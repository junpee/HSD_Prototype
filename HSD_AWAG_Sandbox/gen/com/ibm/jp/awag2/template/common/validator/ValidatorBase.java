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
package com.ibm.jp.awag2.common.validator;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.ConstraintValidatorContext;

import com.ibm.jp.awag2.common.resource.RequestContext;

/**
 * Validatorの基底クラス。
 *
 */
public abstract class ValidatorBase {

    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;
	
    /** エラーメッセージのキーの接尾辞（Parse用） */
	protected static final String PARSEERROR_SUFFIX = ".parse";
	
	/** エラーメッセージのキー */
	protected String messageKey;
	
	/** エラーメッセージ */
	protected String message;
	
	/** エラーメッセージのResourceBundle */
	protected ResourceBundle messageProp;
	
	/** フォーマットチェックロジックのAbstractメソッド */
	protected abstract boolean checkFormat(String value, ConstraintValidatorContext context);
	
	/** パースチェックロジックのAbstractメソッド */
	protected abstract boolean checkParsing(String value, ConstraintValidatorContext context);

	/**
	 * Validationロジック。派生クラスのフォーマットチェックロジック、パースチェックロジックを呼び出す。
	 * @param value Validate対象のデータ
	 * @param context ConstraintValidatorContextオブジェクト
	 * @return Validation結果。成功の場合true、失敗の場合false
	 */
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (requestContext != null && requestContext.getLocale() != null) {
			messageProp = ResourceBundle.getBundle("AWAGCommonMessages", requestContext.getLocale());
		} else {
			messageProp = ResourceBundle.getBundle("AWAGCommonMessages");
		}

		if (value == null) {
			return true;
		}

		boolean isFormatValid = checkFormat(value, context);
		boolean isParsable = true;

		if (isFormatValid) {
			isParsable = checkParsing(value, context);
		}

		if (!isFormatValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		} else if (!isParsable) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(messageProp.getString(messageKey + PARSEERROR_SUFFIX)).addConstraintViolation();
		}

		return (isFormatValid & isParsable);
	}

}
