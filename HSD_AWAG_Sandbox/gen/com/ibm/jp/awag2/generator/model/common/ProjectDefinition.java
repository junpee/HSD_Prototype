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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.ibm.jp.awag.generator.common.util.Required;
import com.ibm.jp.awag2.generator.model.java.APIDefinition;
import com.ibm.jp.awag2.generator.model.web.ArrayListWrapper;
import com.ibm.jp.awag2.generator.model.web.UsecaseDefinition;

/**
 * プロジェクト定義を示すクラス。
 *
 */
public class ProjectDefinition {

	/** アプリ名 */
	private String appName = "";

	/** ユースケース定義のリスト */
	private List<UsecaseDefinition> usecases = new ArrayList<UsecaseDefinition>();

	/** API定義のリスト */
	private List<APIDefinition> apiDefinitions = new ArrayList<APIDefinition>();
	
	/** プロジェクトのデフォルトJavaPackage名 */
	@Required
	private String javaPackage;
	
	/** デザインタイプ */
	private DesignType designType = DesignType.DEFAULT;
	public enum DesignType {
		DEFAULT,
		SIMPLE
	}
	/** コード定義のリスト */
	private Map<String, ArrayListWrapper> displayCodeDefs;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@XmlElementWrapper(name="usecases")
	@XmlElement(name="useCaseDefinition")
	public List<UsecaseDefinition> getUsecases() {
		return usecases;
	}

	public void setUsecases(List<UsecaseDefinition> usecases) {
		this.usecases = usecases;
	}

	public DesignType getDesignType() {
		return designType;
	}

	public void setDesignType(DesignType designType) {
		this.designType = designType;
	}

	public Map<String, ArrayListWrapper> getDisplayCodeDefs() {
		return displayCodeDefs;
	}

	public void setDisplayCodeDefs(Map<String, ArrayListWrapper> displayCodeDefs) {
		this.displayCodeDefs = displayCodeDefs;
	}

	@XmlElementWrapper(name = "apiDefinitions")
	@XmlElement(name = "apiDefinition")
	public List<APIDefinition> getApiDefinitions() {
		return this.apiDefinitions;
	}

	public void setApiDefinitions(List<APIDefinition> apiDefinitions) {
		this.apiDefinitions = apiDefinitions;
	}

	public String getJavaPackage() {
		return this.javaPackage;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}


}
