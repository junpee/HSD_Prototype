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
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ibm.jp.awag.generator.common.util.GeneratorUtil;
import com.ibm.jp.awag.generator.common.util.Required;

/**
 * API定義の入力・出力項目の親クラス。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ItemDefinition {

	/** データ項目名 */
	@XmlElement
	@Required
	private String name;
	
	/** 表示用データ項目名 */
	@XmlElement
	private String nameLocal;

	/** モデルタイプ */
	@XmlElement
	private ModelType modelType = ModelType.DataItem;
	
	/** アクセス先エンティティ */
	@XmlElement
	private String mappedEntityName;
	
	/** アクセス先エンティティ・フィールド */
	@XmlElement
	private String mappedFieldName;
	
	/** 多重 */
	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean multiple;
	
	/** 従属項目のリスト **/
	@XmlTransient
	protected List<? extends ItemDefinition> itemDefinitions;
	
	/** オプション */
	@XmlElement
	private Map<String, String> options;
	
	/** アクセス先エンティティ定義 */
	@XmlTransient
	protected TableDefinition mappedEntityDefinition;
	
	/** アクセス先エンティティフィールド定義 */
	@XmlTransient
	protected ColumnDefinition mappedEntityFieldDefinition;
	
	/**
	 * 唯一のコンストラクタ。
	 */
	public ItemDefinition() {
		super();
	}

	/**
	 * @return dataItemNameLocal
	 */
	public String getNameLocal() {
		return nameLocal;
	}

	/**
	 * @param dataItemNameLocal セットする dataItemNameLocal
	 */
	public void setNameLocal(String dataItemNameLocal) {
		this.nameLocal = dataItemNameLocal;
	}

	/**
	 * @return dataItemName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param dataItemName セットする dataItemName
	 */
	public void setName(String dataItemName) {
		this.name = dataItemName;
	}

	/**
	 * ModelTypeを表すEnumクラス。
	 */
	@XmlEnum
	public enum ModelType {
		Entity,
		DataItem
	}
	
	/**
	 * modelTypeを取得する。
	 * @return modelType
	 */
	public ModelType getModelType() {
		
		if (this.getItemDefinitions() != null && !this.getItemDefinitions().isEmpty()) {
			return ModelType.Entity;
		} else {
			return ModelType.DataItem;			
		}
	}

	/**
	 * modelTypeを設定する。
	 * @param modelType 設定するmodelType
	 */
	public void setModelType(ModelType modelType) {
		this.modelType = modelType;
	}

	/**
	 * @return accessEntity
	 */
	public String getMappedFieldName() {
		return GeneratorUtil.replaceNullStringwithNull(mappedFieldName);
	}

	/**
	 * @param accessEntity セットする accessEntity
	 */
	public void setMappedFieldName(String accessEntity) {
		this.mappedFieldName = accessEntity;
	}

	public String getMappedEntityName() {
		return GeneratorUtil.replaceNullStringwithNull(mappedEntityName);
	}

	public void setMappedEntityName(String mappedEntity) {
		this.mappedEntityName = mappedEntity;
	}

	/**
	 * multipleを取得する。
	 * @return multiple
	 */
	public boolean isMultiple() {
		return Optional.ofNullable(multiple).orElse(false);
	}

	/**
	 * multipleを設定する。
	 * @param multiple 設定するmultiple
	 */
	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

//	public String getListName() {
//		return listName;
//	}
//
//	public void setListName(String listName) {
//		this.listName = listName;
//	}

	public abstract List<? extends ItemDefinition> getItemDefinitions();

	public abstract void setItemDefinitions(List<? extends ItemDefinition> inputItemDefinitions);

	public TableDefinition getMappedEntityDefinition() {
		return mappedEntityDefinition;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public void setMappedEntityDefinition(TableDefinition mappedEntityDefinition) {
		this.mappedEntityDefinition = mappedEntityDefinition;
	}

	public ColumnDefinition getMappedEntityFieldDefinition() {
		return mappedEntityFieldDefinition;
	}

	public void setMappedEntityFieldDefinition(ColumnDefinition mappedEntityFieldDefinition) {
		this.mappedEntityFieldDefinition = mappedEntityFieldDefinition;
	}

}