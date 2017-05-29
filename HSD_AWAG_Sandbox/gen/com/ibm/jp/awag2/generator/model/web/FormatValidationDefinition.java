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
package com.ibm.jp.awag2.generator.model.web;

import java.util.ResourceBundle;

import com.ibm.jp.awag2.generator.model.common.FormatType;

/**
 * 画面項目定義の入力チェック仕様を表すクラス。
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

		if(formatType == FormatType.DECIMAL){
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

		if(formatType == FormatType.DECIMAL){
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
