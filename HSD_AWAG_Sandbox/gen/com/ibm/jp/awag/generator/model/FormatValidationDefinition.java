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
package com.ibm.jp.awag.generator.model;

import java.util.ResourceBundle;

/**
 * 入力チェック仕様を示すクラス。
 *
 */
public class FormatValidationDefinition {

	/** AWAGCommonMessagesのResourceBundle */
	private static ResourceBundle messageProp = ResourceBundle.getBundle("AWAGGeneratorMessages");
	
	/** 形式 */
	private FormatType formatType;
	
	/** 最小桁数 */
	private int minLength;
	
	/** 最大桁数 */
	private int maxLength;
	
	/** 一致桁数 */
	private int length;
	
	/** 整数部桁数 */
	private int integer;
	
	/** 小数部桁数 */
	private int fraction;

	/** 形式と正規表現を紐付けるenum */
	public enum FormatType {
		HALF_NUM("StringFormat.Type.HALF_NUM", "[0-9]"),
		HALF_CHAR("StringFormat.Type.HALF_CHAR", "[\\x20-\\x7E]"),
		HALF_NUM_CHAR("StringFormat.Type.HALF_NUM_CHAR", "[0-9a-zA-Z]"),
		FULL_NUM("StringFormat.Type.FULL_NUM", "[\\uFF10-\\uFF19]"),
		FULL_CHAR("StringFormat.Type.FULL_CHAR", "[^\\x20-\\x7E]"),
		FULL_HIRAGANA("StringFormat.Type.FULL_HIRAGANA", "[\\u3041-\\u309F]"),
		FULL_KATAKANA("StringFormat.Type.FULL_KATAKANA", "[\\u30A1-\\u30FE]"),
		SMALLINT("Number.Type.SMALLINT", "[0-9]"),
		INTEGER("Number.Type.INTEGER", "[0-9]"),
		BIGINT("Number.Type.BIGINT", "[0-9]"),
		FLOAT("Number.Type.FLOAT", "[0-9]"),
		DECIMAL("Number.Type.DECIMAL", "\\d{0,INTEGER}(\\.\\d{0,FRACTION})?"),
		DATE("DateTime.Type.DATE", "\\d{4}-\\d{2}-\\d{2}"),
		TIMESTAMP("DateTime.Type.TIMESTAMP", "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{6}"),
		ALL("StringFormat.Type.ALL", ".");

		/** 書式 */
		private String formatType;
		
		/** 正規表現 */
		private String regex;

		private FormatType(String formatType, String regex) {
			this.formatType = formatType;
			this.regex = regex;
		}

		public String getFormatType() {
			return formatType;
		}
		public String getRegex() {
			return this.regex;
		}

	}

	public FormatType getFormatType() {
		return formatType;
	}

	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}

	public int getFraction() {
		return fraction;
	}

	public void setFraction(int fraction) {
		this.fraction = fraction;
	}

	/**
	 * 入力チェック・エラーメッセージを取得する。
	 * @return 入力チェック・エラーメッセージ
	 */
	public String getMessage(){
		String message = messageProp.getString(formatType.getFormatType());
		String lengthMessage = null;

		if(formatType == FormatValidationDefinition.FormatType.DECIMAL){
			message = message.replaceAll("\\{integer\\}", String.valueOf(getInteger()));
			message = message.replaceAll("\\{fraction\\}", String.valueOf(getFraction()));

		} else {
			// 桁数が指定された場合
			if (minLength > 0 || maxLength > 0 || length > 0) {
				if (length > 0) {
					lengthMessage = messageProp.getString("Format.FormatType.common.lengthmsg.match").replaceAll("\\{length\\}", String.valueOf(length));
				} else {
					if (minLength > 0 && maxLength == 0) {
						lengthMessage = messageProp.getString("Format.FormatType.common.lengthmsg.min").replaceAll("\\{min\\}", String.valueOf(minLength));
					}
					if (minLength == 0 && maxLength > 0) {
						lengthMessage = messageProp.getString("Format.FormatType.common.lengthmsg.max").replaceAll("\\{max\\}", String.valueOf(maxLength));
					}
					if (minLength > 0 && maxLength > 0) {
						lengthMessage = messageProp.getString("Format.FormatType.common.lengthmsg.minmax").replaceAll("\\{min\\}", String.valueOf(minLength)).replaceAll("\\{max\\}", String.valueOf(maxLength));
					}
				}

			} else {
				lengthMessage = "";
			}
			message = message.replaceAll("\\{lengthmsg\\}", lengthMessage);
		}

		return message;
	}

	/**
	 * 正規表現を取得する。
	 * @return 正規表現
	 */
	public String getRegex(){
		String regex = null;

		if(formatType == FormatValidationDefinition.FormatType.DECIMAL){
			regex = getFormatType().getRegex();
			regex = regex.replaceAll("INTEGER", String.valueOf(getInteger()));
			regex = regex.replaceAll("FRACTION", String.valueOf(getFraction()));

		} else {
			StringBuilder regexSb = new StringBuilder(getFormatType().getRegex());

			// 桁数が指定された場合
			if (minLength > 0 || maxLength > 0 || length > 0) {
				regexSb.append("{");
				if (length > 0) {
					regexSb.append(length);
				} else {
					if (minLength > 0 && maxLength == 0) {
						regexSb.append(minLength).append(",");
					}
					if (minLength == 0 && maxLength > 0) {
						regexSb.append("0,").append(maxLength);
					}
					if (minLength > 0 && maxLength > 0) {
						regexSb.append(minLength).append(",").append(maxLength);
					}
				}
				regexSb.append("}");
			} else {
				regexSb.append("");
			}
			regex = regexSb.toString();
		}

		return regex;
	}
}
