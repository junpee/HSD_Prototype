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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.ibm.jp.awag.generator.common.util.Format;
import com.ibm.jp.awag.generator.common.util.Required;

/**
 * ユースケース定義を示すクラス。
 *
 */
public class UsecaseDefinition {

	/** メニュー名（英字） */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	@Required
	private String usecaseName;

	/** メニュー名 */
	@Format(formatType = Format.FormatType.ALL)
	@Required
	private String usecaseDisplayName;
	
	/** 表示優先度 */
	private Integer displayPriority;

	/** 画面定義 */
	private List<ScreenDefinition> screenFlow = new ArrayList<ScreenDefinition>();

	public String getUsecaseName() {
		return usecaseName;
	}

	public void setUsecaseName(String usecaseName) {
		this.usecaseName = usecaseName;
	}

	public String getUsecaseDisplayName() {
		return usecaseDisplayName;
	}

	public void setUsecaseDisplayName(String usecaseDisplayName) {
		this.usecaseDisplayName = usecaseDisplayName;
	}

	/**
	 * displayPriorityを取得する。
	 * @return displayPriority
	 */
	public Integer getDisplayPriority() {
		return this.displayPriority;
	}

	/**
	 * displayPriorityを設定する。
	 * @param displayPriority 設定するdisplayPriority
	 */
	public void setDisplayPriority(Integer displayPriority) {
		this.displayPriority = displayPriority;
	}

	@XmlElementWrapper(name="screenFlow")
	@XmlElement(name="screenDefinition")
	public List<ScreenDefinition> getScreenFlow() {
		return screenFlow;
	}

	public void setScreenFlow(List<ScreenDefinition> screenFlow) {
		this.screenFlow = screenFlow;
	}

}
