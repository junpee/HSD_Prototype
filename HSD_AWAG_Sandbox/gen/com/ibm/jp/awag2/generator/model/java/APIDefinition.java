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

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.capitalizeFirstString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;

import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.util.Required;
import com.ibm.jp.awag2.generator.model.common.HTTPMethodType;
import com.ibm.jp.awag2.generator.model.java.InputItemDefinition.jaxrsType;

/**
 * API定義を示すクラス。
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class APIDefinition {
	
	/** API名 */
	@XmlAttribute
	@Required
	private String name;
	
	/** 表示用API名 */
	@XmlElement
	private String nameLocal;
	
	/** PATH */
	@XmlElement
	@Required
	private String path;
	
	/** API種別 */
	@XmlElement
	@Required
	private APIType  apiType;
	
	/** HTTP Method */
	@XmlElement	
	private HTTPMethodType method;
	
	/** 説明 */
	@XmlElement
	private String description;
	
	/** APIオプション */
	@XmlElement
	private Map<String, String> options;	
	
	/** javaパッケージ */
	@XmlElement
	private String javaPackage;
	
	/** アクセス対象エンティティ */
	@XmlElementWrapper(name = "accessEntity")
	@XmlElement(name = "entity")
	private Map<String, AccessEntity> accessEntityList;
		
	/** API入力項目定義 */
	@XmlElementWrapper(name = "inputitemDefinition")
	@XmlElement(name = "item")
	private List<InputItemDefinition> inputItemDefinitions = new ArrayList<>();
	
	/** API出力項目定義 */
	@XmlElementWrapper(name = "outputitemDefinition")
	@XmlElement(name = "item")
	private List<OutputItemDefinition> outputItemDefinitions = new ArrayList<>();

	/** アクセスエンティティ名リスト */
	@XmlTransient
	private List<String> accessEntityNameList = new ArrayList<>();
	
	/** 入力DTOからEntityへの変換マッピング */
	@XmlTransient
	private Map<String, List<FieldMap>> inputDtoMapping;

	/** Entityから出力DTOへの変換マッピング */
	@XmlTransient
	private Map<String, List<FieldMap>> outputDtoMapping;	

	/** API種別を表すEnum */
	@XmlEnum
	public enum APIType {
		SingleRead(LogicType.R),
		MultiRead(LogicType.R),
		SingleCreate(LogicType.CUD),
		MultiCreate(LogicType.CUD),		
		SingleUpdate(LogicType.CUD),
		MultiUpdate(LogicType.CUD),
		SingleDelete(LogicType.CUD),
		MultiDelete(LogicType.CUD);
		
		private LogicType logicType;
		
		private APIType(LogicType logicType) {
			this.logicType = logicType;
		}
		
		public LogicType getLogicType() {
			return this.logicType;
		}
	}
	
	public enum LogicType {
		CUD,
		R,
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String id) {
		this.name = id;
	}

	/**
	 * @return nameLocal
	 */
	public String getNameLocal() {
		return nameLocal;
	}

	/**
	 * @param nameLocal セットする nameLocal
	 */
	public void setNameLocal(String name) {
		this.nameLocal = name;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * pathを設定する。
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return apiType
	 */
	public APIType getApiType() {
		return apiType;
	}

	/**
	 * @param apiType セットする apiType
	 */
	public void setApiType(APIType apiType) {
		this.apiType = apiType;
	}

	/**
	 * API Methodを取得する。
	 * @return method
	 */
	public HTTPMethodType getMethod() {
		
		// デフォルト Methodの返却
		if (this .method == null) {
			switch (this.apiType) {
			case SingleRead :
				return HTTPMethodType.GET;
			case MultiRead :
				return HTTPMethodType.GET;
			case SingleCreate :
				return HTTPMethodType.POST;
			case SingleUpdate :
				return HTTPMethodType.POST;
			case MultiUpdate :
				return HTTPMethodType.POST;
			case SingleDelete :
				return HTTPMethodType.POST;
			default :
				return HTTPMethodType.POST;
			}
		} else {
			return this.method;			
		}
	}

	/**
	 * @param method セットする method
	 */
	public void setMethod(HTTPMethodType method) {
		this.method = method;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description セットする description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}	

	/**
	 * javaPackageを取得する。
	 * @return javaPackage
	 */
	public String getJavaPackage() {
		return this.javaPackage;
	}

	/**
	 * javaPackageを設定する。
	 * @param javaPackage 設定するjavaPackage
	 */
	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	/**
	 * @return accessEntity
	 */
	public Map<String, AccessEntity> getAccessEntityList() {
		
		if (this.accessEntityList == null) {
			setupAccessEntityList();
		}
		return accessEntityList;
	}


	
	/**
	 * @param accessEntity セットする accessEntity
	 */
	public void setAccessEntityList(Map<String, AccessEntity> accessEntity) {
		this.accessEntityList = accessEntity;
	}

	/**
	 * @return inputDefinitions
	 */
	public List<InputItemDefinition> getInputItemDefinitions() {
		this.setupJaxrsTypes(this.inputItemDefinitions);
		return inputItemDefinitions;
	}



	/**
	 * @param inputDefinitions セットする inputDefinitions
	 */
	public void setInputItemDefinitions(List<InputItemDefinition> inputItemDefinitions) {
		this.inputItemDefinitions = inputItemDefinitions;
	}

	/**
	 * @return outputDefinitions
	 */
	public List<OutputItemDefinition> getOutputItemDefinitions() {
		return outputItemDefinitions;
	}

	/**
	 * @param outputDefinitions セットする outputDefinitions
	 */
	public void setOutputItemDefinitions(List<OutputItemDefinition> outputItemDefinitions) {
		this.outputItemDefinitions = outputItemDefinitions;
	}
	
	public List<String> getAccessEntityNameList() {
		List<String> accessEntityNameList = new ArrayList<>();
		buildEntityNameList(this.accessEntityList, accessEntityNameList);
		return accessEntityNameList;
	}
	
	/**
	 * API定義の入出力項目、アクセスエンティティにテーブル定義を紐づける。
	 * @param allTableDefinitionMap
	 */
	public void setupMappedEntityDefinition(Map<String, TableDefinition> allTableDefinitionMap) {
		
		// 入出力定義のテーブル定義マッピングの設定
		buildMappedEntityDefinition(this.getInputItemDefinitions(), allTableDefinitionMap);
		buildMappedEntityDefinition(this.getOutputItemDefinitions(), allTableDefinitionMap);
		
		// アクセスEntityのテーブル定義マッピングの設定
		AccessEntity.setMappedEntityDefinition(this.accessEntityList, allTableDefinitionMap);

	}

	/**
	 * 入力DTOからEntityへの変換マッピングを取得する。
	 * @return
	 */
	public Map<String, List<FieldMap>> getInputDtoMapping() {
		
		if (inputDtoMapping == null) {
			this.setupInputDtoMapping();
		}
		
		return this.inputDtoMapping;
	}

	/**
	 * Entityから出力DTOへの変換マッピングを取得する。
	 * @return
	 */
	public Map<String, List<FieldMap>> getOutputDtoMapping() {
		
		if (outputDtoMapping == null) {
			setupOutputDtoMapping();
		}
		return this.outputDtoMapping;
	}
	
	/**
	 * 入力項目に対しHTTP Methodに応じたParameterTypeを設定する。
	 * @param inputItemDefinitions
	 */
	private void setupJaxrsTypes(List<InputItemDefinition> inputItemDefinitions) {
		
		jaxrsType defaultType = null;
		switch (getMethod()) {
		case GET:
			defaultType = jaxrsType.QueryParam;
			break;
		default:
			defaultType = jaxrsType.Default;
			break;
		}
		
		if (inputItemDefinitions != null) {
			for (InputItemDefinition inputItemDefinition : inputItemDefinitions) {
				if (this.path.contains("{" + inputItemDefinition.getName() + "}" ))  {
					inputItemDefinition.setParamType(jaxrsType.PathParam);				
				} else if (this.path.contains("{_id_}" ) && inputItemDefinition.mappedEntityFieldDefinition != null && inputItemDefinition.mappedEntityFieldDefinition.isPk()) {
					inputItemDefinition.setParamType(jaxrsType.PathParam);
				} else {
					inputItemDefinition.setParamType(defaultType);				
				}
			}		
		}
	}
	
	/**
	 * を行う。
	 */
	private void setupInputDtoMapping() {
		FieldMap fieldMap = new FieldMap();
		
		buildEntityToDTOMapping(this.getInputItemDefinitions(), "", fieldMap, this.getName() + "InputDTO");
		
		this.inputDtoMapping =  fieldMap.getEmbeddedMapping();
	}

	/**
	 * を行う。
	 */
	private void setupOutputDtoMapping() {
		FieldMap fieldMap = new FieldMap();
		
		buildDTOToEntityMapping(this.getOutputItemDefinitions(), "", fieldMap, this.getName() + "OutputDTO");
		
		this.outputDtoMapping =  fieldMap.getEmbeddedMapping();
	}

	/**
	 * を行う。
	 */
	private void setupAccessEntityList() {

		Map<String, AccessEntity> accessEntityList =  new HashMap<>();

		Map<String, List<FieldMap>> dataMapping = null;
		
		// 照会系の場合、OutputputItemDefinitionsのMappedEntity名から作成
		if (this.getApiType().getLogicType().equals(LogicType.R)) {
			List<ItemDefinition> outputItemDefinitions = this.getOutputItemDefinitions().stream().collect(Collectors.toList());
			dataMapping = new HashMap<>();
			buildDataMappingFromOutputItemDefinition(outputItemDefinitions, dataMapping);		
		}
		
		// 更新系の場合、InputDtoMappingのKey(更新先Entity名)から作成
		if (this.getApiType().getLogicType().equals(LogicType.CUD)) {
			dataMapping = this.getInputDtoMapping();
		}
		buildAccessEntityListFromDataMapping(dataMapping, accessEntityList);				
		
		this.accessEntityList = accessEntityList;
	
	}

	private static void buildDataMappingFromOutputItemDefinition(List<ItemDefinition> itemDefinitions, Map<String, List<FieldMap>> dataMapping) {
		
		if (itemDefinitions == null) return;
		for (ItemDefinition itemDefinition : itemDefinitions) {
			String dataMappingKey = null;
			if (itemDefinition.getMappedEntityName() != null && !itemDefinition.getMappedEntityName().isEmpty()) {
				dataMappingKey = itemDefinition.getMappedEntityName();
			} else if (itemDefinition != null && itemDefinition.getMappedFieldName() != null && !itemDefinition.getMappedFieldName().isEmpty()) {
				dataMappingKey = "_none_";
			} else {
				continue;
			}
			
			if (dataMapping.get(dataMappingKey) == null) {
				List<FieldMap> fieldMapList = new ArrayList<>();
				dataMapping.put(dataMappingKey, fieldMapList);
			}

			List<FieldMap> fieldMapList = dataMapping.get(dataMappingKey);

			FieldMap fieldMap = new FieldMap();
			fieldMap.setOutputField(itemDefinition.getMappedFieldName());
			fieldMapList.add(fieldMap);

			Map<String, List<FieldMap>> dataMappingChild = new HashMap<>();
			fieldMap.setEmbeddedMapping(dataMappingChild);
			buildDataMappingFromOutputItemDefinition((List<ItemDefinition>) itemDefinition.getItemDefinitions(), dataMappingChild);

		}
	}

	private static void buildAccessEntityListFromDataMapping(Map<String, List<FieldMap>> dataMapping, Map<String, AccessEntity> accessEntityList) {

		if (dataMapping == null) return;
		for (String entityName : dataMapping.keySet()) {
			
			if (entityName.equals("_none_")) {
				for (FieldMap fieldMap : dataMapping.get(entityName)) {
					buildAccessEntityListFromDataMapping(fieldMap.getEmbeddedMapping(), accessEntityList);
				}
				continue;
			}
				
			AccessEntity accessEntity = new AccessEntity();
			accessEntity.setEntityName(entityName);
			
			if (dataMapping == null || dataMapping.get(entityName) == null) continue;
			
			Map<String, AccessEntity> dotEntitySet = new HashMap<>();
			for (FieldMap fieldMap : dataMapping.get(entityName)) {
				Map<String, AccessEntity> accessEntityListChild = new HashMap<>();
				if (accessEntity.getAccessEntityList() == null) {
					accessEntity.setAccessEntityList(accessEntityListChild);	
				}

				// .ドット記法（例：Product.ProductName）でマップされた項目をJoin対象に追加
				if (fieldMap.getOutputField() != null && fieldMap.getOutputField().contains(".")) {
					String accessEntityName = fieldMap.getOutputField().split("\\.")[0];
					if (dotEntitySet.get(accessEntityName) == null) {
						AccessEntity accessEntitySeparatedByDot = new AccessEntity();
						accessEntitySeparatedByDot.setEntityName(accessEntityName);		
						accessEntityListChild.put(accessEntityName, accessEntitySeparatedByDot);		
					}
				}
				
				buildAccessEntityListFromDataMapping(fieldMap.getEmbeddedMapping(), accessEntityListChild);
				
				if (accessEntity.getAccessEntityList() != null) {
					accessEntity.getAccessEntityList().putAll(accessEntityListChild);
				}
			}
			accessEntityList.put(entityName, accessEntity);
		}
	}
	
	/**
	 * 従属エンティティを含めたアクセスエンティティのリストを取得する。
	 * @param accessEntityList
	 * @param accessEntityNameList
	 */
	private static void buildEntityNameList(Map<String, AccessEntity> accessEntityList, List<String> accessEntityNameList) {
		
		for (AccessEntity accessEntity: accessEntityList.values()) {
			accessEntityNameList.add(accessEntity.getEntityName());
			
			if (accessEntity.getAccessEntityList() != null && !accessEntity.getAccessEntityList().isEmpty()) {
				buildEntityNameList(accessEntity.getAccessEntityList(), accessEntityNameList);
			}
		}
	}	
	
	/**
	 * マッピング先のDB項目のテーブル定義オブジェクトを設定する
	 * @param tableDefinitionMap
	 */
	private static void buildMappedEntityDefinition(List<? extends ItemDefinition> itemDefinitions, Map<String, TableDefinition> allTableDefinitionMap) {
		
		if (itemDefinitions != null) {
			for (ItemDefinition itemDef : itemDefinitions) {
				if (itemDef.getMappedEntityName() != null && !itemDef.getMappedEntityName().isEmpty()) {
					itemDef.setMappedEntityDefinition(allTableDefinitionMap.get(itemDef.getMappedEntityName()));
					if (itemDef.getMappedFieldName() != null && !itemDef.getMappedFieldName().isEmpty()) {
						try {
							itemDef.setMappedEntityFieldDefinition(allTableDefinitionMap.get(itemDef.getMappedEntityName()).getColumnDefinitionMap().get(itemDef.getMappedFieldName()));			
						} catch(NullPointerException e) {
							throw new GenerateException("マッピング先の項目がテーブル定義に見つかりません。（Case Sensitive）[Mapped Entity]" + itemDef.getMappedEntityName() + "[Mapped Field]" + itemDef.getMappedFieldName(), e);
						}
					}
				} else {
					if (itemDef.getMappedFieldName() != null && !itemDef.getMappedFieldName().isEmpty()) {
						itemDef.setMappedEntityDefinition(allTableDefinitionMap.get(itemDef.getMappedFieldName()));		
					}					
				}
				buildMappedEntityDefinition(itemDef.getItemDefinitions(), allTableDefinitionMap);
			}
		}
	}

	private static void buildEntityToDTOMapping(List<? extends ItemDefinition> itemDefinitionList, String parentBeanName, FieldMap parentFIeldMap, String inputTypePrefix) {
		
		if (itemDefinitionList == null) {
			return;
		}
		
		Map<String, List<FieldMap>> beanMap = new HashMap<>();
		
		for (ItemDefinition itemDefinition : itemDefinitionList) {
			
			// DBマッピングが無い場合は無視する
			if (itemDefinition.getMappedFieldName() == null || itemDefinition.getMappedFieldName().isEmpty()) {
				continue;
			}

			FieldMap fieldMap = new FieldMap();
			fieldMap.setInputType(inputTypePrefix + capitalizeFirstString(parentBeanName));
			fieldMap.setInputField(itemDefinition.getName());
			fieldMap.setOutputField(itemDefinition.getMappedFieldName());
			fieldMap.setMultiple(itemDefinition.isMultiple());
			fieldMap.setFieldDefinition(itemDefinition.getMappedEntityFieldDefinition());

			APIDefinition.buildEntityToDTOMapping(itemDefinition.getItemDefinitions(), itemDefinition.getName(), fieldMap, inputTypePrefix);
			
			List<FieldMap> fieldMapList = null;
			if (beanMap.get(Optional.ofNullable(itemDefinition.getMappedEntityName()).orElse("_none_")) == null) {
				fieldMapList = new ArrayList<>();
			} else {
				fieldMapList = beanMap.get(Optional.ofNullable(itemDefinition.getMappedEntityName()).orElse("_none_"));
			}
			
			fieldMapList.add(fieldMap);
			beanMap.put(Optional.ofNullable(itemDefinition.getMappedEntityName()).orElse("_none_"), fieldMapList);

		}
		
		parentFIeldMap.setEmbeddedMapping(beanMap);
	}

	private static void buildDTOToEntityMapping(List<? extends ItemDefinition> itemDefinitionList, String parentBeanName, FieldMap parentFIeldMap, String inputTypePrefix) {
		
		if (itemDefinitionList == null) {
			return;
		}
		
		Map<String, List<FieldMap>> beanMap = new HashMap<>();
		
		for (ItemDefinition itemDefinition : itemDefinitionList) {
			
			// DBマッピングが無い場合は無視する
			if (itemDefinition.getMappedFieldName() == null || itemDefinition.getMappedFieldName().isEmpty()) {
				continue;
			}

			FieldMap fieldMap = new FieldMap();
			fieldMap.setInputType(Optional.ofNullable(itemDefinition.getMappedEntityName()).orElse("_none_"));
			fieldMap.setInputField(itemDefinition.getMappedFieldName());
			fieldMap.setOutputField(itemDefinition.getName());
			fieldMap.setMultiple(itemDefinition.isMultiple());
			fieldMap.setFieldDefinition(itemDefinition.getMappedEntityFieldDefinition());

			APIDefinition.buildDTOToEntityMapping(itemDefinition.getItemDefinitions(), itemDefinition.getName(), fieldMap, inputTypePrefix);
			
			List<FieldMap> fieldMapList = null;
			if (beanMap.get(inputTypePrefix + capitalizeFirstString(parentBeanName)) == null) {
				fieldMapList = new ArrayList<>();
			} else {
				fieldMapList = beanMap.get(inputTypePrefix + capitalizeFirstString(parentBeanName));
			}
			
			fieldMapList.add(fieldMap);
			beanMap.put(inputTypePrefix + capitalizeFirstString(parentBeanName), fieldMapList);

		}
		
		parentFIeldMap.setEmbeddedMapping(beanMap);
	}

	/**
	 * を行う。
	 * @param allTableDefinitionMap
	 */
	public void setupDerivationField(Map<String, TableDefinition> allTableDefinitionMap) {
		this.setupInputDtoMapping();
		this.setupOutputDtoMapping();
		this.setupAccessEntityList();
		this.setupMappedEntityDefinition(allTableDefinitionMap);
	}

	public enum Option {
		Dummy("dummy");
		
		private String parameterStr;
		
		private Option(String parameterStr) {
			this.parameterStr = parameterStr;
		}
		
		public String getParameterStr() {
			return this.parameterStr;
		}
	}
}
