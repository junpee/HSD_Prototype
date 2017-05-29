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

import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ibm.jp.awag2.generator.model.common.FormatType;

/**
 * API定義の入力項目を表すクラス。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class InputItemDefinition extends ItemDefinition {

	/** 必須 */
	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean isNotNull;
	
	/** 形式 */
	@XmlElement
	private FormatType formatType;
	
	/** 最小桁数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer minLength;
	
	/** 最大桁数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer maxLength;
		
	/** 整数部桁数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer integer;
	
	/** 小数部桁数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer fraction;

	/** JAX-RS PARAM TYPE */
	@XmlTransient
	private jaxrsType paramType;
	
	/** API入力項目定義 */
	@XmlElement(name = "item")
	protected List<InputItemDefinition> itemDefinitions;

	/** JAX-RS PARAM TYPEを表すenum */
	@XmlEnum
	public enum jaxrsType {
		PathParam,
		MatrixParam,
		HeaderParam,
		CookieParam,
		FormParam,
		QueryParam,
		Default
	}
	
	/**
	 * @return isNotNull
	 */
	public boolean isNotNull() {
		return Optional.ofNullable(isNotNull).orElse(false);
	}
	
	public Boolean getIsNotNull() {
		return isNotNull;
	}

	/**
	 * @param isNotNull セットする isNotNull
	 */
	public void setNotNull(Boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	/**
	 * @return formatType
	 */
	public FormatType getFormatType() {
		return formatType;
	}

	/**
	 * @param formatType セットする formatType
	 */
	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}

	/**
	 * @return minLength
	 */
	public Integer getMinLength() {
		return minLength;
	}

	/**
	 * @param minLength セットする minLength
	 */
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	/**
	 * @return maxLength
	 */
	public Integer getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength セットする maxLength
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @return integer
	 */
	public Integer getInteger() {
		return integer;
	}
	
	public void setInteger(Integer integer) {
		this.integer = integer;
	}	

	/**
	 * @return fraction
	 */
	public Integer getFraction() {
		return fraction;
	}
	
	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}	

	public jaxrsType getParamType() {
		return paramType;
	}

	public void setParamType(jaxrsType paramType) {
		this.paramType = paramType;
	}

	@Override
	public List<? extends ItemDefinition> getItemDefinitions() {
		return this.itemDefinitions;
	}

	@Override
	public void setItemDefinitions(List<? extends ItemDefinition> inputItemDefinitions) {
		this.itemDefinitions = (List<InputItemDefinition>) inputItemDefinitions;
	}

}
