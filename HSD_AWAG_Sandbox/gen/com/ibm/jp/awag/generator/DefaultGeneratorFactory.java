package com.ibm.jp.awag.generator;

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.loadConfig;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.print;

import com.ibm.jp.awag.generator.common.AbstractGenerator;
import com.ibm.jp.awag.generator.common.AbstractGeneratorFactory;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag.generator.model.DefaultGeneratorConfig;

public class DefaultGeneratorFactory extends AbstractGeneratorFactory {

	private static final String GENERATOR_DEFAULT_CONFIG_FILE_PATH = "gen/DefaultGeneratorConfig.xml";
	
	@Override
	public AbstractGenerator create(AWAGConfig awagConfig) {
		
		// Generator Config Fileの読み込み
		String generatorConfigFilePath = (String) awagConfig.getOptionMap().get("DefaultGenerator.configFilePath");
		if (generatorConfigFilePath == null || generatorConfigFilePath.isEmpty()) {
			super.generatorConfigFilePath = GENERATOR_DEFAULT_CONFIG_FILE_PATH;
		} else {
			super.generatorConfigFilePath = generatorConfigFilePath;
		}
		
		DefaultGeneratorConfig generatorConfig = loadConfig(super.generatorConfigFilePath, DefaultGeneratorConfig.class);
		awagConfig.setGeneratorConfig(generatorConfig);
		print("Generator Config File: " + super.generatorConfigFilePath);
		
		// Generator インスタンスの返却
		return new DefaultGenerator(awagConfig);
	}
}
