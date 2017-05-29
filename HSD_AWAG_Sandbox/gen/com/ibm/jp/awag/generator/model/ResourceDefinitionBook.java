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
package com.ibm.jp.awag.generator.model;

import java.util.List;
import java.util.Map;

/**
 * リソース定義ファイル（XLS）を示すクラス。
 *
 */
public class ResourceDefinitionBook {
	
	/** リソース定義のリスト */
	private List<ResourceDefinition> resourceDefs;
	
	/** コード定義のリスト */
	private Map<String, List<DisplayCode>> displayCodeDefs;
	
	public List<ResourceDefinition> getResourceDefs() {
		return resourceDefs;
	}

	public void setResourceDefs(List<ResourceDefinition> resourceDefs) {
		this.resourceDefs = resourceDefs;
	}

	public Map<String, List<DisplayCode>> getDisplayCodeDefs() {
		return displayCodeDefs;
	}

	public void setDisplayCodeDefs(Map<String, List<DisplayCode>> displayCodeDefs) {
		this.displayCodeDefs = displayCodeDefs;
	}
}
