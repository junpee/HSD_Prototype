package com.ibm.jp.awag.generator;

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.getStackErrorMessage;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.print;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.printError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ibm.jp.awag.generator.common.AbstractGenerator;
import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag.generator.common.model.GeneratorConfig.TemplateConfig;
import com.ibm.jp.awag.generator.common.model.GeneratorConfig.TemplateSet;
import com.ibm.jp.awag.generator.model.RelationDefinition;
import com.ibm.jp.awag.generator.model.ResourceDefinition;
import com.ibm.jp.awag.generator.model.ResourceDefinitionBook;

public class DefaultGenerator extends AbstractGenerator {
		
	public DefaultGenerator(AWAGConfig awagConfig) {
		super(awagConfig);
		
		if (super.getLoader() == null) {
			super.setLoader(new DefaultInputDataLoader(awagConfig));
		}
	}

	@Override
	protected void generateCodeFromTemplateSet(Map<String, Object> dataModel, TemplateSet templateSet) {

		Map<String, Object> dataModelForOutput  = null;
		
		ResourceDefinitionBook resourceDefBook = (ResourceDefinitionBook) dataModel.get("resourceBook");
		
		int success = 0;
		int fail = 0;
		
		// commonコードを生成する
		if(templateSet.isCommonfile()) {
			
			// テンプレート毎にコードを生成する
			for (TemplateConfig template : templateSet.getTemplateList()) {
				dataModelForOutput = new HashMap<>();
				dataModelForOutput.putAll(dataModel);
				
				try {
					if (template.isActive()) {
						dataModelForOutput.put("resourceDefs", resourceDefBook.getResourceDefs());
						dataModelForOutput.put("displayCodeDefs", resourceDefBook.getDisplayCodeDefs());
						
						String codeDirectoryPath = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir());
						String filePath = codeDirectoryPath + "/" + template.getFileName();														
						
						generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
						success++;							
					}
				} catch (GenerateException e) {
					fail++;
					printError("コード生成に失敗。" + getStackErrorMessage(e));
				}
			}
		}
		// common以外のコードを生成する			
		else {
			for (TemplateConfig template : templateSet.getTemplateList()) {
				dataModelForOutput = new HashMap<>();
				dataModelForOutput.putAll(dataModel);
				
				try {
					if (template.isActive()) {
						for (ResourceDefinition resourceDef : resourceDefBook.getResourceDefs()) {
							
							// 画面コード出力が"しない"の場合、Webコード生成をスキップ
							if (!resourceDef.isWebGenEnabled() && templateSet.isWebCode()) {
								print("SKIP    : " + "[Resource]" + Optional.ofNullable(resourceDef).map(v-> v.getResourceName()).orElse("common") + "\t[TemplateSet]" + templateSet.getName() + "\t\tGenerate web code is \"OFF\".");
								continue;
							}								

							try {
								List<RelationDefinition> relationDefinitions = resourceDef.getRelationDefinitions();
								boolean isIncludePattern = relationDefinitions.stream().anyMatch(s -> ((template.getPatternList()!=null) ? template.getPatternList().contains(s.getRelationPattern().getPatternString()) : false));

									// リソースシートに関連定義が入力されていない場合、または入力されているが出力対象のパターンに一致している場合、コードを生成する
									if (template.getPatternList() == null || template.getPatternList().isEmpty() || isIncludePattern) {
										
										// コード生成
										if (!template.isGenerateToEachEmbedded()) {
											// リソース定義、関連リソース定義リスト、親リソース定義をFreeMarkerの埋め込みデータに設定			
											dataModelForOutput.put("resourceDefs", resourceDefBook.getResourceDefs());
											dataModelForOutput.put("displayCodeDefs", resourceDefBook.getDisplayCodeDefs());
											dataModelForOutput.put("resource", resourceDef);
											dataModelForOutput.put("embeddedResourceDefinitions", resourceDef.getEmbeddedResourceDefinitions());
											
											String appPackagePath = resourceDef.getPackageName().replaceAll("\\.", "/");
											String codeDirectoryPath = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir().replaceAll("\\{appPackage\\}", appPackagePath)).replaceAll("\\{resourceNameLowerCase\\}", resourceDef.getResourceName().toLowerCase());		;
											if (resourceDef.getResourceDefinitionParent() != null) {
												codeDirectoryPath = codeDirectoryPath.replaceAll("\\{resourceNameParentLowerCase\\}", resourceDef.getResourceDefinitionParent().getResourceName().toLowerCase());
											}
											String filePath = codeDirectoryPath + "/" + template.getFileName().replaceAll("\\{resourceName\\}", resourceDef.getResourceName()).replaceAll("\\{resourceNameLowerCase\\}", resourceDef.getResourceName().toLowerCase());;									
											
											generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
											success++;
										} 
										// 関連リソース毎に生成するコード生成
										else {
											List<ResourceDefinition> embeddedResourceDefinitions = resourceDef.getEmbeddedResourceDefinitions();
											
											for (ResourceDefinition embeddedResourceDefinition : embeddedResourceDefinitions) {
												embeddedResourceDefinition.setResourceDefinitionParent(resourceDef);
												// 関連リソース定義をFreeMarkerの埋め込みデータに設定
												dataModelForOutput.put("AWAGversion", this.awagConfig.getVersion());				
												dataModelForOutput.put("resourceDefs", resourceDefBook.getResourceDefs());
												dataModelForOutput.put("displayCodeDefs", resourceDefBook.getDisplayCodeDefs());
												dataModelForOutput.put("resourceParent", resourceDef);
												dataModelForOutput.put("resource", embeddedResourceDefinition);

												String appPackagePath = embeddedResourceDefinition.getPackageName().replaceAll("\\.", "/");
												String codeDirectoryPath = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir().replaceAll("\\{appPackage\\}", appPackagePath)).replaceAll("\\{resourceNameLowerCase\\}", embeddedResourceDefinition.getResourceName().toLowerCase());		;
												if (embeddedResourceDefinition.getResourceDefinitionParent() != null) {
													codeDirectoryPath = codeDirectoryPath.replaceAll("\\{resourceNameParentLowerCase\\}", embeddedResourceDefinition.getResourceDefinitionParent().getResourceName().toLowerCase());
												}
												String filePath = codeDirectoryPath + "/" + template.getFileName().replaceAll("\\{resourceName\\}", embeddedResourceDefinition.getResourceName()).replaceAll("\\{resourceNameLowerCase\\}", embeddedResourceDefinition.getResourceName().toLowerCase());						
												
												generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
												success++;
											}
										}

									} else {
										print("SKIP    : " + "[Resource]" + Optional.ofNullable(resourceDef).map(v-> v.getResourceName()).orElse("common") + "\t[Template]" + template.getName() + "\t\t Unmatched Generate pattern");
									}

							} catch (GenerateException e) {
								fail++;
								printError("コード生成に失敗。" + getStackErrorMessage(e));
							}
						}
						success++;
					}
				} catch (GenerateException e) {
					fail++;
					printError("コード生成に失敗。" + getStackErrorMessage(e));
				}
			}
		}
		// TemplateSet単位に成功・失敗件数を出力
		print(".................................");
		print("SUCCESS:\t" + String.valueOf(success));
		print("FAIL:\t\t" + String.valueOf(fail));
		print("=================================");
		
		if (fail > 0) {
			throw new GenerateException("コード生成エラーあり。", null);
		}
	}
}
