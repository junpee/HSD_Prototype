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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXB;

import com.ibm.jp.awag.generator.common.AbstractInputDataLoader;
import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag.generator.model.DefaultGeneratorConfig;
import com.ibm.jp.awag2.generator.model.common.AWAG2GeneratorConfig;
import com.ibm.jp.awag2.generator.model.common.GeneratorDataMapKey;
import com.ibm.jp.awag2.generator.model.common.ProjectDefinition;
import com.ibm.jp.awag2.generator.model.java.TableCategory;

/**
 * AWAG2のコード生成入力情報を読み込むクラス。
 *
 */
public class AWAG2InputDataLoader extends AbstractInputDataLoader {

	Map<String, Object> xlsDefMap;

	/**
	 * 唯一のコンストラクタ。
	 * @param awagConfig
	 */
	public AWAG2InputDataLoader(AWAGConfig awagConfig) {
		super(awagConfig);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag.generator.common.AbstractInputDataLoader#load()
	 */
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
			XLSDefinitionReader reader = new XLSDefinitionReader(((AWAG2GeneratorConfig) awagConfig.getGeneratorConfig()).getDefinitionSheetMap());

			for(String file : files) {
				xlsDefMap = reader.read(file);
				ProjectDefinition projectDef = (ProjectDefinition) xlsDefMap.get(GeneratorDataMapKey.PROJECT.getKey());
				if(projectDef != null){
					ProjectDefinition retProj = (ProjectDefinition)retMap.get(GeneratorDataMapKey.PROJECT.getKey());
					if(retProj != null){
						retProj.getUsecases().addAll(projectDef.getUsecases());
						if(retProj.getAppName()==null && projectDef.getAppName()!=null){
							retProj.setAppName(projectDef.getAppName());
						}
						if(retProj.getDesignType()==null && projectDef.getDesignType()!=null){
							retProj.setDesignType(projectDef.getDesignType());
						}
					} else {
						retMap.put(GeneratorDataMapKey.PROJECT.getKey(), projectDef);
					}
				}

				TableCategory tableCat = (TableCategory) xlsDefMap.get(GeneratorDataMapKey.TABLE_CATEGORY.getKey());
				if(tableCat != null){
					TableCategory retTable = (TableCategory)retMap.get(GeneratorDataMapKey.TABLE_CATEGORY.getKey());
					if(retTable != null){
						retTable.getTableDefinitions().addAll(tableCat.getTableDefinitions());
						if(retTable.getJavaPackage()==null && tableCat.getJavaPackage()!=null){
							retTable.setJavaPackage(tableCat.getJavaPackage());
						}
					} else {
						retMap.put(GeneratorDataMapKey.TABLE_CATEGORY.getKey(), tableCat);
					}
				}
			}

			// dump data
			String arg = null;
			//APL
			arg = (String) awagConfig.getOptionMap().get("AWAG2.DumpAPL");
			if(arg != null && arg.equals("true")){
				ProjectDefinition projectDef = (ProjectDefinition) retMap.get(GeneratorDataMapKey.PROJECT.getKey());
				Path path = Paths.get("AWAG2-APL.xml");
				try(BufferedWriter bw = Files.newBufferedWriter(path)){
					JAXB.marshal(projectDef, bw);
					System.out.println("Dumped in "+path.toAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//JPA
			arg = (String) awagConfig.getOptionMap().get("AWAG2.DumpJPA");
			if(arg != null && arg.equals("true")){
				TableCategory tableCat = (TableCategory) retMap.get(GeneratorDataMapKey.TABLE_CATEGORY.getKey());
				Path path = Paths.get("AWAG2-JPA.xml");
				try(BufferedWriter bw = Files.newBufferedWriter(path)){
					JAXB.marshal(tableCat, bw);
					System.out.println("Dumped in "+path.toAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
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
		return (String) this.xlsDefMap.get(GeneratorDataMapKey.INPUT_DATA_GENERATOR_VERSION.getKey());
	}
}
