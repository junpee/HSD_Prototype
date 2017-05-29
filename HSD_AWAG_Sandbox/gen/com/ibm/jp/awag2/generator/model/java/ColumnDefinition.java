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
package com.ibm.jp.awag2.generator.model.java;

import java.util.Map;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ibm.jp.awag.generator.common.util.Required;
import com.ibm.jp.awag2.generator.model.common.DataType;

/**
 * テーブル定義のデータ項目を表すクラス。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnDefinition {

	/** カラム名（Excel:カラム名（英字）） */
	@XmlElement
	@Required
	private String name; //IR: aliasName
	
	/** 表示用カラム名 */
	@XmlElement
	@Required
	private String nameLocal; //IR: name
	
	/** PK */
	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean isPk;

	/** NotNull */
	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean isNotNull;
	
	/** データ型 */
	@XmlElement
	@Required
	private DataType dataType;

	/** 全体桁数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer length;
	
	/** 小数桁数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer fraction;
	
	/** ロック用バージョン */
	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean isVersion;

	/** カラム・オプション */
	private Map<String, String> options;
	
	/**
	 * @return nameLocal
	 */
	public String getNameLocal() {
		if (this.nameLocal == null || this.nameLocal.isEmpty()) {
			return this.getName();
		} else {
			return nameLocal;			
		}
	}

	/**
	 * @param nameLocal セットする nameLocal
	 */
	public void setNameLocal(String columnNameLocal) {
		this.nameLocal = columnNameLocal;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String columnName) {
		this.name = columnName;
	}

	/**
	 * @return isPk
	 */
	public boolean isPk() {
		return Optional.ofNullable(isPk).orElse(false);
	}
	
	public Boolean getIsPk() {
		return isPk;
	}

	/**
	 * @param isPk セットする isPk
	 */
	public void setPk(Boolean isPk) {
		this.isPk = isPk;
	}

	public boolean isNotNull() {
		return Optional.ofNullable(isNotNull).orElse(false);
	}
	
	public Boolean getIsNotNull() {
		return isNotNull;
	}

	public void setNotNull(Boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	/**
	 * @return dataType
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType セットする dataType
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getFraction() {
		return fraction;
	}

	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}

	/**
	 * @return isVersion
	 */
	public boolean isVersion() {
		return Optional.ofNullable(isVersion).orElse(false);
	}
	
	public Boolean getIsVersion() {
		return isVersion;
	}

	/**
	 * @param isVersion セットする isVersion
	 */
	public void setVersion(Boolean isVersion) {
		this.isVersion = isVersion;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

}
