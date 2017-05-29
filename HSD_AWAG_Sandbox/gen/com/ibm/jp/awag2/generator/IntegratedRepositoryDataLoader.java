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
/**
 * 
 */
package com.ibm.jp.awag2.generator;

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.mapXmlToObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DataBindingException;

import com.ibm.jp.awag.generator.common.AbstractInputDataLoader;
import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag2.generator.model.common.AWAG2GeneratorConfig;
import com.ibm.jp.awag2.generator.model.common.GeneratorDataMapKey;
import com.ibm.jp.awag2.generator.model.common.IntegratedRepositoryInterfaceData;

/**
 * Integrated Repositoryより連携されたコード生成入力情報を読み込むクラス。
 * 
 */
public class IntegratedRepositoryDataLoader extends AbstractInputDataLoader  {

	private IntegratedRepositoryInterfaceData irInterfaceData;
	
	/**
	 * 唯一のコンストラクタ。
	 * @param awagConfig
	 */
	public IntegratedRepositoryDataLoader(AWAGConfig awagConfig) {
		super(awagConfig);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag.generator.common.AbstractInputDataLoader#load()
	 */
	@Override
	public Map<String, Object> load() {
		Map<String, Object> retMap = new HashMap<>();

		String xmlFilePath = null;
		// get input file
		ArrayList<String> files = (ArrayList<String>) awagConfig.getOptionMap().get("files");
		if (files != null && !files.isEmpty()) {
			xmlFilePath = files.get(0);
		} else {
			xmlFilePath = ((AWAG2GeneratorConfig)awagConfig.getGeneratorConfig()).getDataFilePath();
		}

		try {
			this.irInterfaceData = mapXmlToObject(xmlFilePath, IntegratedRepositoryInterfaceData.class);
			retMap.put(GeneratorDataMapKey.PROJECT.getKey(), irInterfaceData.getProjectDefinition());
			retMap.put(GeneratorDataMapKey.TABLE_CATEGORY.getKey(), irInterfaceData.getTableCategory());
			
		} catch (DataBindingException | IOException e) {
			throw new GenerateException("コード生成入力情報の読み込みに失敗。", e);
		}

		return retMap;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag.generator.common.AbstractInputDataLoader#getInputDataGeneratorVersion()
	 */
	@Override
	public String getInputDataGeneratorVersion() {
		return "AWAG Input Data Generator " + this.irInterfaceData.getAwagInputDataGeneratorConfig().getVersion();
	}

}
