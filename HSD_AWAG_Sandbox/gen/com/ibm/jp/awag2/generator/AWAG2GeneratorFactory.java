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

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.loadConfig;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.print;

import com.ibm.jp.awag.generator.common.AbstractGenerator;
import com.ibm.jp.awag.generator.common.AbstractGeneratorFactory;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag2.generator.model.common.AWAG2GeneratorConfig;

/**
 * AWAG2のGenerator Factoryクラス。Generator設定ファイルを読み込み、AWAG2のGeneratorクラスを生成する。
 * 
 */
public class AWAG2GeneratorFactory extends AbstractGeneratorFactory {

	private static final String GENERATOR_DEFAULT_CONFIG_FILE_PATH = "gen/AWAG2GeneratorConfig.xml";

	@Override
	public AbstractGenerator create(AWAGConfig awagConfig) {

		// Generator Config Fileの読み込み
		String generatorConfigFilePath = (String) awagConfig.getOptionMap().get("AWAG2Generator.configFilePath");
		if (generatorConfigFilePath == null || generatorConfigFilePath.isEmpty()) {
			super.generatorConfigFilePath = GENERATOR_DEFAULT_CONFIG_FILE_PATH;
		} else {
			super.generatorConfigFilePath = generatorConfigFilePath;
		}
		
		AWAG2GeneratorConfig generatorConfig = loadConfig(super.generatorConfigFilePath, AWAG2GeneratorConfig.class);
		awagConfig.setGeneratorConfig(generatorConfig);
		print("Generator Config File: " + super.generatorConfigFilePath);
		
		// Generator インスタンスの返却
		return new AWAG2Generator(awagConfig);
	}
}
