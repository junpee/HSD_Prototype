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
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.ibm.jp.awag.generator.common.util.Format;
import com.ibm.jp.awag.generator.common.util.Required;

/**
 * 画面定義を示すクラス。
 *
 */
public class ScreenDefinition {

	/** 画面名（英字） */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	@Required
	private String screenName;

	/** 画面名 */
	@Format(formatType = Format.FormatType.ALL)
	@Required
	private String screenDisplayName;

	/** イベント定義 */
	private List<EventDefinition> eventList = new ArrayList<EventDefinition>();

	/** 画面項目定義 */
	private List<FieldDefinition> fieldList = new ArrayList<FieldDefinition>();

	/** 標準列数 */
	private int col = 4;

	/** 画面タイプ */
	@Required
	private ScreenType screenType = ScreenType.STANDARD;

	/** 画面タイプのenum */
	public enum ScreenType {
		STANDARD,
		SEARCH_TABLE,
		HEADER_DETAIL,
		GRID_SELECT_TABLE,
		GRID_TABLE
	}

	/** グループ定義 */
	private List<FieldGroup> fieldGroupList = new ArrayList<FieldGroup>();

	/** メニュー内トップ */
	private boolean isMenuTop;;

	/** オプション */
	private Map<String, String> options;

	/**
	 * @return screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName セットする screenName
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return screenDisplayName
	 */
	public String getScreenDisplayName() {
		return screenDisplayName;
	}

	/**
	 * @param screenDisplayName セットする screenDisplayName
	 */
	public void setScreenDisplayName(String screenDisplayName) {
		this.screenDisplayName = screenDisplayName;
	}

	/**
	 * @return eventList
	 */
	@XmlElementWrapper(name="eventList")
	@XmlElement(name="eventDefinition")
	public List<EventDefinition> getEventList() {
		return eventList;
	}

	/**
	 * @param eventList セットする eventList
	 */
	public void setEventList(List<EventDefinition> eventList) {
		this.eventList = eventList;
	}

	/**
	 * @return fieldList
	 */
	@XmlElementWrapper(name="fieldList")
	@XmlElement(name="fieldDefinition")
	public List<FieldDefinition> getFieldList() {
		return fieldList;
	}

	/**
	 * @param fieldList セットする fieldList
	 */
	public void setFieldList(List<FieldDefinition> fieldList) {
		this.fieldList = fieldList;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getPCColWidth() {
		switch (col) {
		case 1:
			return 12;
		case 2:
			return 6;
		case 3:
			return 4;
		default:
			return 3;
		}
	}

	public int getTabletColWidth() {
		switch (col) {
		case 1:
			return 8;
		default:
			return 4;
		}
	}

	public int getPhoneColWidth() {
		return 4;
	}

	public ScreenType getScreenType() {
		return screenType;
	}
	public void setScreenType(ScreenType screenType) {
		this.screenType = screenType;
	}

	@XmlElementWrapper(name="fieldGroupList")
	@XmlElement(name="fieldGroup")
	public List<FieldGroup> getFieldGroupList() {
		return fieldGroupList;
	}

	public void setFieldGroupList(List<FieldGroup> fieldGroupList) {
		this.fieldGroupList = fieldGroupList;
	}

	public boolean isMenuTop() {
		return isMenuTop;
	}

	public void setMenuTop(boolean isMenuTop) {
		this.isMenuTop = isMenuTop;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

}
