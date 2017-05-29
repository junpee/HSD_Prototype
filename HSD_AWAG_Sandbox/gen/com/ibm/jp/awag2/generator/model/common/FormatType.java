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
package com.ibm.jp.awag2.generator.model.common;

import javax.xml.bind.annotation.XmlEnum;

/** 
 * 形式と正規表現を紐付けるenumクラス。
 * 
 */
@XmlEnum
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
	TIMESTAMP("DateTime.Type.TIMESTAMP", "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{6}"),
	ALL("StringFormat.Type.ALL", ".");

	/** 書式 */
	private String formatType;
	
	/** 正規表現 */
	private String regex;

	/**
	 * 唯一のコンストラクタ。
	 * @param 書式
	 * @param 正規表現
	 */
	private FormatType(String formatType, String regex) {
		this.formatType = formatType;
		this.regex = regex;
	}

	/**
	 * 書式を取得する。
	 * @return 書式
	 */
	public String getFormatType() {
		return formatType;
	}
	
	/**
	 * 正規表現を取得する。
	 * @return 正規表現
	 */
	public String getRegex() {
		return this.regex;
	}
}