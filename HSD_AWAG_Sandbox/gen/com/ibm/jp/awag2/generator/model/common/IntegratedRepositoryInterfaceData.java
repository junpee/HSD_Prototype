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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.ibm.jp.awag2.generator.model.java.TableCategory;

/**
 * Integrated Repositoryから受け取るインターフェースデータを表すクラス。
 *
 */
@XmlType(propOrder = {"awagInputDataGeneratorConfig", "errorList", "projectDefinition", "tableCategory"})
@XmlAccessorType(XmlAccessType.FIELD)
public class IntegratedRepositoryInterfaceData {
	
	/** AWAGInputDataGeneratorの設定*/
	@XmlElement
	private AWAGInputDataGeneratorConfig awagInputDataGeneratorConfig;
	
	/** プロジェクト定義（画面・API定義） */
	@XmlElement
	private ProjectDefinition projectDefinition;

//	/** ビジネスカテゴリー（API定義） */
//	@XmlElementWrapper(name = "businessCategories")
//	@XmlElement(name = "businessCategory")
//	private List<BusinessCategory> businessCategories;
	
	/** テーブル定義 */
	@XmlElement
	private TableCategory tableCategory;

	/** エラー情報 */
	@XmlElementWrapper(name = "errors")
	@XmlElement(name = "error")
	private List<AWAGInputDataGeneratorError> errorList;
	
	/**
	 * awagInputDataGeneratorConfigを取得する。
	 * @return awagInputDataGeneratorConfig
	 */
	public AWAGInputDataGeneratorConfig getAwagInputDataGeneratorConfig() {
		return this.awagInputDataGeneratorConfig;
	}

	/**
	 * awagInputDataGeneratorConfigを設定する。
	 * @param awagInputDataGeneratorConfig 設定するawagInputDataGeneratorConfig
	 */
	public void setAwagInputDataGeneratorConfig(AWAGInputDataGeneratorConfig awagInputDataGeneratorConfig) {
		this.awagInputDataGeneratorConfig = awagInputDataGeneratorConfig;
	}

	/**
	 * プロジェクト定義（画面・API定義）を取得する。
	 * @return プロジェクト定義（画面・API定義）
	 */
	public ProjectDefinition getProjectDefinition() {
		return this.projectDefinition;
	}

	/**
	 * プロジェクト定義（画面・API定義）を設定する。
	 * @param projectDefinition 設定するプロジェクト定義（画面・API定義）
	 */
	public void setProjectDefinition(ProjectDefinition projectDefinition) {
		this.projectDefinition = projectDefinition;
	}
/*
	*//**
	 * API定義を取得する。
	 * @return API定義
	 *//*
	public List<BusinessCategory> getBusinessCategories() {
		return this.businessCategories;
	}

	*//**
	 * API定義を設定する。
	 * @param businessCategories 設定するAPI定義
	 *//*
	public void setBusinessCategories(List<BusinessCategory> businessCategories) {
		this.businessCategories = businessCategories;
	}
*/
	/**
	 * テーブル定義を取得する。
	 * @return テーブル定義
	 */
	public TableCategory getTableCategory() {
		return this.tableCategory;
	}

	/**
	 * テーブル定義を設定する。
	 * @param tableCategory 設定するテーブル定義
	 */
	public void setTableCategory(TableCategory tableCategory) {
		this.tableCategory = tableCategory;
	}

	/**
	 * errorListを取得する。
	 * @return errorList
	 */
	public List<AWAGInputDataGeneratorError> getErrorList() {
		return this.errorList;
	}

	/**
	 * errorListを設定する。
	 * @param errorList 設定するerrorList
	 */
	public void setErrorList(List<AWAGInputDataGeneratorError> errorList) {
		this.errorList = errorList;
	}
}
