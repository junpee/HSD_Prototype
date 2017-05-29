/*
 * Automated web application generator
 *
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 * IPSC : 6949-63S
 * (C) Copyright IBM Japan, Ltd. 2016 All Rights Reserved.
 * (C) Copyright IBM Corp. 2016 All Rights Reserved.
 * US Government Users Restricted Rights -
 * Use, duplication or disclosure restricted
 * by GSA ADP Schedule Contract with IBM Corp.
 *
 */
package com.ibm.jp.awag2.common.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Context;
import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.jp.awag2.common.entity.DisplayName;
import com.ibm.jp.awag2.common.entity.EntityBase;
import com.ibm.jp.awag2.common.entity.PKBase;
import com.ibm.jp.awag2.common.logic.ErrorContainer;
import com.ibm.jp.awag2.common.logic.ErrorInfo;
import com.ibm.jp.awag2.common.logic.ValidationErrorInfo;
import com.ibm.jp.awag2.common.util.Logger;
import com.ibm.jp.awag2.common.util.LoggerFactory;
import com.ibm.jp.awag2.common.validator.ValidationException;

/**
 * Resourceクラスの基底クラス。
 *
 */
public abstract class ResourceManagerBase {

	private static final int ERRORTYPE_SINGLEITEM = 1;
	private static final int ERRORTYPE_GROUP = 2;

    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;

	/** HttpRequestオブジェクト */
	@Context
	HttpServletRequest request;

	/**
	 * RequestContextオブジェクト を取得する。
	 * @return RequestContextオブジェクト
	 */
	public RequestContext getRequestContext() {
		return requestContext;
	}

	/**
	 * HttpServletRequestを取得する。
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Loggerを取得する。
	 * @return Logger
	 */
	protected Logger getLogger() {
		return LoggerFactory.getInstance().getLogger(this);
	}


	/**
	 * 複数のEntityオブジェクトのBeanValidationを実行する。
	 * @param beanList チェック対象のEntityオブジェクトのリスト
	 * @throws ValidationException チェックエラーが発生した場合
	 */
/** メソッド廃止
	protected <T extends EntityBase<?, ?>> void validateBean(List<T> beanList) throws ValidationException {

		if (beanList != null) {
			for (T bean : beanList) {

				if (bean.get_Action() == null) {
					continue;
				}
				switch (bean.get_Action()) {
				case "a" :
					validateBean(bean, null);
					break;
				case "u" :
					validateBean(bean, null);
					break;
				case "d" :
					validateBean(bean, DeleteGroup.class);
					break;
				default :
					break;
				}
			}
		}
	}
**/

	/**
	 * EntityオブジェクトのBeanValidationを実行する。
	 * @param bean チェック対象のEntityオブジェクト
	 * @throws ValidationException チェックエラーが発生した場合
	 */
	protected <T> void validateBean(T bean) throws ValidationException {
		if (bean != null) {
		validateBean(bean, null);
		}
	}

	/**
	 * チェック対象Groupを指定してEntityオブジェクトのBeanValidationを実行する。
	 * @param bean チェック対象のEntityオブジェクト
	 * @throws ValidationException チェックエラーが発生した場合
	 */
	protected <T> void validateBean(T bean, Class<?> group) throws ValidationException {

		// Validatorの呼び出し
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> validateResult = null;
        try {
        	if (group != null) {
                validateResult = validator.validate(bean, group);
        	} else {
        		validateResult = validator.validate(bean);
        	}

        } catch (Exception e) {
        	throw new RuntimeException(e);
        }

        List<ErrorInfo> errorList = null;
        ErrorContainer ec = null;

        // Invalidの場合
		if (!validateResult.isEmpty()) {
			errorList = new ArrayList<ErrorInfo>();

			int errorType = 0;

			for (ConstraintViolation<T> cv : validateResult) {

				// エラー項目のフィールド名を取得
				Object invalidBean = cv.getLeafBean();
				Class<T> validatedClass = (Class<T>)invalidBean.getClass();

				Iterator<Path.Node> itr = cv .getPropertyPath().iterator();

				String fieldName = null;
				while(itr.hasNext()) {
					fieldName = itr.next().toString();
				}
				
				String index = null;
				try {
					Matcher matcher = Pattern.compile("\\[(\\d)\\]\\.").matcher(fieldName);
					if (matcher.find()) {
						index = matcher.group(1);					
					}
				} catch (RuntimeException e) {
					index = null;
				}

				fieldName = Pattern.compile("\\[[0-9]*\\]\\.").matcher(fieldName).replaceFirst("");


				// 単項目チェックエラーの場合、エラーメッセージを構築する
				if (fieldName != null && !fieldName.isEmpty()) {

					// Beanのキー値の取得
					String key = null;
					String displayKey = null;
					if (invalidBean instanceof EntityBase) {
						key = ((EntityBase<?, ?>)invalidBean).getPk().toString();
						displayKey = ((EntityBase<?, ?>)invalidBean).getPk().toDisplayString();
						
					} else if (invalidBean instanceof PKBase) {
						key = ((PKBase)invalidBean).toString();
						displayKey = ((PKBase)invalidBean).toDisplayString();
					}

					// Beanのフィールド名の文字列表現を取得
					Field validatedField = null;
					try {
						validatedField = validatedClass.getDeclaredField(fieldName);
						validatedField.setAccessible(true);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					}

					// Beanのリソース名の取得
					String resourceName = Optional.ofNullable(validatedClass.getAnnotation(XmlRootElement.class)).map(s -> s.name()).orElse(null);

					// Beanのフィールド名の取得
					String displayFieldName = Optional.ofNullable(validatedField.getAnnotation(DisplayName.class)).map(s -> s.name()).orElse(fieldName);

					// エラーコードの構築
					String code = null;
					Class<? extends Annotation> annotationClazz = cv.getConstraintDescriptor().getAnnotation().annotationType();
					if(annotationClazz.equals(com.ibm.jp.awag2.common.validator.StringFormat.class)) {
						code = "_EVDS0001";
					} else if (annotationClazz.equals(com.ibm.jp.awag2.common.validator.Number.class)) {
						code = "_EVDN0001";
					} else if (annotationClazz.equals(com.ibm.jp.awag2.common.validator.DateTime.class)) {
						code = "_EVDD0001";
					}

					// エラーメッセージの構築
					String displayKeyString = "";
					if (displayKey != null && !displayKey.isEmpty()) {
						displayKeyString = "[" + displayKey + "]";
					} else if (index != null && !index.isEmpty()) {
						displayKeyString = "[" + index +  "]";						
					}
					String message =  displayKeyString + displayFieldName +  " " + cv.getMessage();

					errorList.add(new ValidationErrorInfo(code, message, resourceName, validatedField.getName(), key));
					errorType = ERRORTYPE_SINGLEITEM;
				}
				// 相関チェックエラーの場合
				else {
					errorList.add(new ErrorInfo("_EVD00002", cv.getMessage()));
					errorType = ERRORTYPE_GROUP;
				}
			}

			String errorCode = null;
			switch (errorType) {
			case ERRORTYPE_SINGLEITEM:
				errorCode = "_EVD00001";
				break;
			case ERRORTYPE_GROUP:
				errorCode = "_EVD00002";
				break;
			default: break;
			}

			if (errorCode != null && !errorCode.isEmpty()) {
				ec = new ErrorContainer(errorCode, requestContext.getLocale());
				ec.addErrors(errorList);
			}
			throw new ValidationException(ec);
		}
	}

}
