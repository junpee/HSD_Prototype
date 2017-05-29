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
package com.ibm.jp.awag.generator.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.ibm.jp.awag.generator.common.util.Format.FormatType;

/**
 * Beanに定義された入力チェック・アノテーションに従ってValidationを実行する。
 *
 */
public class FormatValidator {

	/**
	 * 指定されたBeanのValidationを行う。
	 * @param bean Validation対象のBean
	 * @return エラー・メッセージのリスト
	 */
	public <T> List<String> isValid(T bean) {

		@SuppressWarnings("unchecked")
		Class<T> validatedClass = (Class<T>) bean.getClass();
		Class<T> validatedClassParent = (Class<T>) validatedClass.getSuperclass();	
		
		List<String> errorList = new ArrayList<>();
		List<Field> validateFieldList = new ArrayList<>();
		validateFieldList.addAll(Arrays.asList(validatedClass.getDeclaredFields()));
		validateFieldList.addAll(Arrays.asList(validatedClassParent.getDeclaredFields()));

		for (Field field : validateFieldList) {

			field.setAccessible(true);
		
			Object fieldObj = null;
			try {
				fieldObj = field.get(bean);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("入力チェックでリソース定義シートObjectへのアクセスに失敗。", e);
			}
			
			if (fieldObj instanceof String) {
				String errorMsg = validate(field, (String)fieldObj);
				if (errorMsg != null) {
					errorList.add(errorMsg);
				}
			} else {
				String errorMsg = validate(field, fieldObj);
				if (errorMsg != null) {
					errorList.add(errorMsg);
				}
			}
		}
		return errorList;
	}

	/**
	 * 指定されたフィールドの値のValidationを実行する。
	 * @param field Validation対象のフィールド
	 * @param fieldObj Validation対象のオブジェクト
	 * @return エラー・メッセージ
	 */
	private String validate(Field field, Object fieldObj) {
		
		String errorMsg = null;
		
		if(field.getAnnotation(Required.class) != null && fieldObj == null) {
			errorMsg = "形式チェックエラー。[Field]" + field.getName() + "[Expected]必須項目 [Value]" + fieldObj;
		}
		
		return errorMsg;
	}

	/**
	 * 指定されたString型フィールドの値のValidationを実行する。
	 * @param field Validation対象のフィールド
	 * @param fieldObj Validation対象のオブジェクト
	 * @return エラー・メッセージ
	 */
	private String validate(Field field, String fieldObj) {
		String errorMsg = null;
		
		boolean isAllowNull = true;	
		if(field.getAnnotation(Required.class) != null) {
			isAllowNull = false;
		}
		
		if (!isAllowNull && (fieldObj == null || fieldObj.equals(""))) {
			errorMsg = "形式チェックエラー。[Field]" + field.getName() + "[Expected]必須入力[Value]" + fieldObj;
		}
		
		Format formatAnotation = field.getAnnotation(Format.class);
		if (formatAnotation != null) {
			FormatType formatType = formatAnotation.formatType();
			
			try {
				String regex = null;
				if (isAllowNull) {
					regex = formatType.getRegex() + "{0,}";				
				} else {
					regex = formatType.getRegex() + "{1,}";							
				}

				if (!Pattern.matches(regex, fieldObj)) {
					String allowNullStr = "";
					if (!isAllowNull) {
						allowNullStr = "必須入力";
					}
					
					errorMsg = "形式チェックエラー。[Field]" + field.getName() + "[Expected]" + allowNullStr + ", " + formatType.toString() + "[Value]" + fieldObj;
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("入力チェックでリソース定義シートObjectへのアクセスに失敗。", e);
			}
		}
		return errorMsg;
	}
}
