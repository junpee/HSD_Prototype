package com.ibm.jp.awag.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibm.jp.awag.generator.common.AbstractInputDataLoader;
import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag.generator.model.DefaultGeneratorConfig;
import com.ibm.jp.awag.generator.model.ResourceDefinitionBook;

public class DefaultInputDataLoader extends AbstractInputDataLoader {

	public DefaultInputDataLoader(AWAGConfig awagConfig) {
		super(awagConfig);
	}

	@Override
	public Map<String, Object> load() {
		Map<String, Object> retMap = new HashMap<>();

		try {

			// get input file
			ArrayList<String> files = (ArrayList<String>) awagConfig.getOptionMap().get("files");
			if(files.size() == 0) {
				files.add(((DefaultGeneratorConfig)awagConfig.getGeneratorConfig()).getXlsFilePath());
			}

			// load data
			XLSResourceDefinitionReader reader = new XLSResourceDefinitionReader(((DefaultGeneratorConfig) awagConfig.getGeneratorConfig()).getDefinitionSheetMap());
			for(String file : files) {
				ResourceDefinitionBook resourceBook = reader.read(file);

				if(retMap.get("resourceBook")==null){
					retMap.put("resourceBook", resourceBook);
				} else {
					ResourceDefinitionBook tmp = (ResourceDefinitionBook) retMap.get("resourceBook");
					tmp.getResourceDefs().addAll(resourceBook.getResourceDefs());
					tmp.getDisplayCodeDefs().putAll(resourceBook.getDisplayCodeDefs());
				}
			}

		} catch (GenerateException e) {
			throw new GenerateException("コード生成入力情報の読み込みに失敗。", e);
		}
		return retMap;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag.generator.common.AbstractInputDataLoader#getInputDataGeneratorVersion()
	 */
	@Override
	public String getInputDataGeneratorVersion() {
		return "AWAG1 Excel Generator";
	}
}
