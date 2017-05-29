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

/**
 * BeanValidation用アノテーション。
 * 指定した文字列フィールドのフォーマットをチェックする。
 *
 */
@Constraint(validatedBy=StringFormatValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface StringFormat {

	/** 文字列フォーマットを示すenum */
	public enum Type {
		/** 半角数字 */
		HALF_NUM("[0-9]", "Validator.Format.half_num"),
		/** 半角文字 */
		HALF_CHAR("[\\x20-\\x7E]",  "Validator.Format.half_char"),
		/** 半角英数字 */
		HALF_NUM_CHAR("[0-9a-zA-Z]", "Validator.Format.half_num_char"),
		/** 全角数字 */
		FULL_NUM("[\\uFF10-\\uFF19]", "Validator.Format.full_num"),
		/** 全角文字 */
		FULL_CHAR("[^\\x20-\\x7E]", "Validator.Format.full_char"),
		/** 全角ひらがな */
		FULL_HIRAGANA("[\\u3041-\\u309F]", "Validator.Format.full_hiragana"),
		/** 全角カタカナ */
		FULL_KATAKANA("[\\u30A1-\\u30FE]", "Validator.Format.full_katakana"),
		/** すべての文字 */
		ALL(".", "Validator.Format.all");

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
	/** 最小桁数 */
	int minLength() default 0;
	/** 最大桁数 */
	int maxLength() default 0;
	/** 一致桁数 */
	int length() default 0;
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
