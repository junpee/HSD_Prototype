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
 * Excel定義シートのデータ型とJavaのデータ型を紐付けるenumクラス。
 * 
 */
@XmlEnum
public enum DataType {
	SMALLINT("String"),
	INTEGER("String"),
	BIGINT("String"),
	FLOAT("String"),
	DECIMAL("String"),
	CHAR("String"),
	VARCHAR("String"),
	DATE("String"),
	TIMESTAMP("String");

	/** Javaのデータ型 */
	private String javaType;

	/**
	 * 唯一のコンストラクタ。
	 * @param Javaのデータ型
	 */
	private DataType(String javaType) {
		this.javaType = javaType;
	}

	/**
	 * Javaのデータ型を取得する。
	 * @return Javaのデータ型
	 */
	public String getJavaType() {
		return this.javaType;
	}
}