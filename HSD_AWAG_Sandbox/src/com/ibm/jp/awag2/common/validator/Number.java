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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * BeanValidation用アノテーション。
 * 指定した数値フィールドのフォーマットをチェックする。
 *
 */
@Constraint(validatedBy=NumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Number {
	
	/** 数値フォーマットを示すenum */
	public enum Type {
		/** SMALLINT */
		SMALLINT("Validator.Format.smallint"),
		
		/** INTEGER */
		INTEGER("Validator.Format.integer"),
		
		/** BIGINT */
		BIGINT("Validator.Format.bigint"),		
		
		/** FLOAT */
		FLOAT("Validator.Format.float"),
		
		/** DECIMAL */
		DECIMAL("Validator.Format.decimal"),	
		
		ALL("{Validator.Format.default}");
		
		/** エラーメッセージのキー */
		private String messageKey;
		
		/**
		 * 正規表現とエラーメッセージのキーを指定してインスタンスを初期化する。
		 * @param regex　正規表現
		 * @param messageKey　エラーメッセージのキー
		 */
		private Type(String messageKey) {
			this.messageKey = messageKey;
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
	
	/** 整数部桁数 */
	int integer() default 0;
	
	/** 小数部桁数 */
	int fraction() default 0;
	
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
