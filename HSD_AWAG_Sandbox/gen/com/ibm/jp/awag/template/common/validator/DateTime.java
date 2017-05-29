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
package com.ibm.jp.awag.rest.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=DateTimeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DateTime {	
	public enum Type {
		/** 日付 */
		DATE("\\d{4}-\\d{2}-\\d{2}", "Validator.Format.date"),
		/** 日時 */
		TIMESTAMP("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{0,6}", "Validator.Format.timestamp"),		
		ALL(".*", "{Validator.Format.default}");
		
		/** 正規表現 */
		private String regex;
		/** エラーメッセージのキー */
		private String messageKey;
		
		/**
		 * 正規表現とエラーメッセージのキーを指定してインスタンスを初期化する。
		 * @param regex　正規表現
		 * @param messageKey　エラーメッセージのキー
		 */
		private Type(String regex, String messageKey) {
			this.regex = regex;
			this.messageKey = messageKey;
		}
		
		/**
		 * 正規表現を取得する。
		 * @return　正規表現
		 */
		public String getRegex() {
			return this.regex;
		}
		
		/**
		 * エラーメッセージのキーを取得する。
		 * @return エラーメッセージのキー
		 */
		public String getMessageKey() {
			return this.messageKey;
		}
	}
	
	/** 文字列フォーマット */
	Type type() default Type.ALL;
	/** エラーメッセージ */
    String message() default "{Validator.Format.default}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};	

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    public @interface List {
    	StringFormat[] value();
    }
}
