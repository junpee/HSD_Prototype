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

/**
 * 画面項目定義の画面グループ仕様を表すクラス。
 *
 */
public class FieldGroup {
	
	/** デフォルトリスト名 */
	private final String DEFAULT_LIST_NAME = "list";

	/** グループ名 */
	@Format(formatType = Format.FormatType.ALL)
	private String fieldGroupName;

	/** リスト判定フラグ */
	private boolean isList;

	/** リストの項目名 */
	private String listName = DEFAULT_LIST_NAME;

	/** リストのパラメータ名 */
	private String listDisplayName;

	/** グループ定義 */
	private List<FieldGroup> fieldGroupList = new ArrayList<FieldGroup>();

	/** イベント定義 */
	private List<EventDefinition> eventList = new ArrayList<EventDefinition>();

	/** 画面項目定義 */
	private List<FieldDefinition> fieldList = new ArrayList<FieldDefinition>();

	/** 標準列数 */
	private int col = 4;
	
	public String getFieldGroupName() {
		return fieldGroupName;
	}

	public void setFieldGroupName(String fieldGroupName) {
		this.fieldGroupName = fieldGroupName;
	}

	public boolean isList() {
		return isList;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListDisplayName() {
		return listDisplayName;
	}

	public void setListDisplayName(String listDisplayName) {
		this.listDisplayName = listDisplayName;
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
	
	@XmlElementWrapper(name="fieldGroupList")
	@XmlElement(name="fieldGroup")
	public List<FieldGroup> getFieldGroupList() {
		return fieldGroupList;
	}

	public void setFieldGroupList(List<FieldGroup> fieldGroupList) {
		this.fieldGroupList = fieldGroupList;
	}
}
