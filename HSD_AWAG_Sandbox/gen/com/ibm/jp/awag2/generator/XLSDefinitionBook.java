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
package com.ibm.jp.awag2.generator;

import java.util.List;
import java.util.Map;

import com.ibm.jp.awag2.generator.model.common.ProjectDefinition;
import com.ibm.jp.awag2.generator.model.java.APIDefinition;
import com.ibm.jp.awag2.generator.model.java.TableCategory;
import com.ibm.jp.awag2.generator.model.java.TableDefinition;
import com.ibm.jp.awag2.generator.model.web.ArrayListWrapper;
import com.ibm.jp.awag2.generator.model.web.DisplayCode;
import com.ibm.jp.awag2.generator.model.web.EventDefinition;
import com.ibm.jp.awag2.generator.model.web.FieldDefinition;
import com.ibm.jp.awag.generator.model.FormatValidationDefinition;
import com.ibm.jp.awag2.generator.model.web.ScreenDefinition;
import com.ibm.jp.awag2.generator.model.web.UsecaseDefinition;

/**
 * Excel定義ファイルを示すクラス。
 *
 */
public class XLSDefinitionBook {

	/** プロジェクト定義のリスト */
	private List<ProjectDefinition> projectDefs;
	
	/** コード定義のリスト */
	private Map<String, ArrayListWrapper> displayCodeDefs;

	/** テーブルカテゴリ */
	private TableCategory tableCategory;	
	
	public List<ProjectDefinition> getProjectDefs() {
		return projectDefs;
	}

	public void setProjectDefs(List<ProjectDefinition> projectDefs) {
		this.projectDefs = projectDefs;
	}	

	public Map<String, ArrayListWrapper> getDisplayCodeDefs() {
		return displayCodeDefs;
	}

	public void setDisplayCodeDefs(Map<String, ArrayListWrapper> displayCodeDefs) {
		this.displayCodeDefs = displayCodeDefs;
	}
	
	public TableCategory getTableCategory() {
		return tableCategory;
	}

	public void setTableCategory(TableCategory tableCategory) {
		this.tableCategory = tableCategory;
	}
	
}
