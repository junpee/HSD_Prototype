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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * API定義のアクセス先テーブル項目を表すクラス。
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessEntity {
	
	/** エンティティ名 */
	@XmlAttribute(name="name")
	private String entityName;
	
	/** アクセス対象エンティティ */
	@XmlElement(name = "entity")
	private Map<String, AccessEntity> accessEntityList;
	
	/** アクセス先エンティティ定義 */
	@XmlTransient
	protected TableDefinition mappedEntityDefinition;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Map<String, AccessEntity> getAccessEntityList() {
		return accessEntityList;
	}

	public void setAccessEntityList(Map<String, AccessEntity> accessEntityList) {
		this.accessEntityList = accessEntityList;
	}

	public TableDefinition getMappedEntityDefinition() {
		return mappedEntityDefinition;
	}

	public void setMappedEntityDefinition(TableDefinition mappedEntityDefinition) {
		this.mappedEntityDefinition = mappedEntityDefinition;
	}
	
	/**
	 * AccessEntityの参照先テーブル定義を設定する。
	 * @param accessEntityList
	 * @param allTableDefinitionMap
	 */
	protected static void setMappedEntityDefinition(Map <String, ? extends AccessEntity> accessEntityMap, Map<String , TableDefinition> allTableDefinitionMap) {
		
		if (accessEntityMap != null) {
			for (AccessEntity accessEntity : accessEntityMap.values()) {
				if (accessEntity.getEntityName() != null) {
					accessEntity.setMappedEntityDefinition(allTableDefinitionMap.get(accessEntity.getEntityName()));					
				}
				setMappedEntityDefinition(accessEntity.getAccessEntityList(), allTableDefinitionMap);
			}
		}
	}
}
