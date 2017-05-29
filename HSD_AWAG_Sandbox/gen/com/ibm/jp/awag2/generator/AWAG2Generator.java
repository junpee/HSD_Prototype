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

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.capitalizeFirstString;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.getExtensionString;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.getSimpleFileNameString;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.getStackErrorMessage;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.print;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.printError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.jp.awag.generator.common.AbstractGenerator;
import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag.generator.common.model.GeneratorConfig.TemplateConfig;
import com.ibm.jp.awag.generator.common.model.GeneratorConfig.TemplateSet;
import com.ibm.jp.awag2.generator.model.common.GeneratorDataMapKey;
import com.ibm.jp.awag2.generator.model.common.ProjectDefinition;
import com.ibm.jp.awag2.generator.model.java.APIDefinition;
import com.ibm.jp.awag2.generator.model.java.InputItemDefinition;
import com.ibm.jp.awag2.generator.model.java.ItemDefinition;
import com.ibm.jp.awag2.generator.model.java.ItemDefinition.ModelType;
import com.ibm.jp.awag2.generator.model.java.OutputItemDefinition;
import com.ibm.jp.awag2.generator.model.java.TableCategory;
import com.ibm.jp.awag2.generator.model.java.TableDefinition;
import com.ibm.jp.awag2.generator.model.web.EventDefinition;
import com.ibm.jp.awag2.generator.model.web.FieldDefinitionComparator;
import com.ibm.jp.awag2.generator.model.web.FieldGroup;
import com.ibm.jp.awag2.generator.model.web.ScreenDefinition;
import com.ibm.jp.awag2.generator.model.web.UsecaseDefinition;
import com.ibm.jp.awag2.generator.model.web.UsecaseDefinitionComparator;

/**
 * AWAG2のコード生成クラス。
 * 
 */
public class AWAG2Generator extends AbstractGenerator {

	/**
	 * AWAG Configを指定してオブジェクトを初期化する。
	 *
	 * @param awagConfig
	 */
	public AWAG2Generator(AWAGConfig awagConfig) {
		super(awagConfig);

		if (super.getLoader() == null) {
			super.setLoader(new AWAG2InputDataLoader(awagConfig));
		}
	}

	@Override
	protected void generateCodeFromTemplateSet(Map<String, Object> dataModel, TemplateSet templateSet) {

		Map<String, Object> dataModelForOutput = null;

		success = 0;
		fail = 0;

		// commonコードを生成する
		if (templateSet.isCommonfile()) {

			// テンプレート毎にコードを生成する
			for (TemplateConfig template : templateSet.getTemplateList()) {
				if (template.isActive()) {

					String codeDirectoryPath = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir());
					String filePath = codeDirectoryPath + "/" + template.getFileName();

					try {
						generateEachCode(dataModel, template, codeDirectoryPath, filePath);
						success++;
					} catch (GenerateException e) {
						fail++;
						printError("コード生成に失敗。" + getStackErrorMessage(e));
					}
				}
			}
		}
		// common以外のコードを生成する
		else {
			for (TemplateConfig template : templateSet.getTemplateList()) {

				if (template.isActive()) {
					// Java_Resourceテンプレート向けロジック
					if (templateSet.getName().equals("java_Resource")) {

						ProjectDefinition project = (ProjectDefinition)dataModel.get(GeneratorDataMapKey.PROJECT.getKey());
						List<APIDefinition> apiDefList = project.getApiDefinitions();

						// API毎にコードを生成
						if (apiDefList != null && !apiDefList.isEmpty()) {
							for (APIDefinition apiDef : apiDefList) {

								// モデルの導出項目の設定
								TableCategory tc = (TableCategory) dataModel.get(GeneratorDataMapKey.TABLE_CATEGORY.getKey());
								apiDef.setupDerivationField(tc.getTableDefinitionMap());
								
								// 埋込データの初期化
								dataModelForOutput = new HashMap<>();
								dataModelForOutput.putAll(dataModel);
								dataModelForOutput.put(GeneratorDataMapKey.API.getKey(), apiDef);

								// 出力先ファイル名の構築
								String appPackagePath = apiDef.getJavaPackage().replaceAll("\\.", "/");
								String codeDirectoryPath = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir().replaceAll("\\{appPackage\\}", appPackagePath));
								String filePath = codeDirectoryPath + "/" + template.getFileName().replaceAll("\\{id\\}", apiDef.getName());

								// Freemerker 埋込データの設定
								dataModelForOutput.put(GeneratorDataMapKey.API.getKey(), apiDef);

								// コード生成
								// DTO生成
								if (template.getName().equals("InputDTO")) {
									dataModelForOutput.put(GeneratorDataMapKey.API_ITEMS.getKey(), apiDef.getInputItemDefinitions());
									try {
										generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
										success++;
									} catch (GenerateException e) {
										fail++;
										printError("コード生成に失敗。" + getStackErrorMessage(e));
									}

									// 従属DTOを生成する
									generateChildItemCod(InputItemDefinition.class, apiDef.getInputItemDefinitions(), dataModelForOutput, template, codeDirectoryPath, filePath);
								} else if (template.getName().equals("OutputDTO")) {
									dataModelForOutput.put(GeneratorDataMapKey.API_ITEMS.getKey(), apiDef.getOutputItemDefinitions());
									try {
										generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
										success++;
									} catch (GenerateException e) {
										fail++;
										printError("コード生成に失敗。" + getStackErrorMessage(e));
									}

									// 従属DTOを生成する
									generateChildItemCod(OutputItemDefinition.class, apiDef.getOutputItemDefinitions(), dataModelForOutput, template, codeDirectoryPath, filePath);
								}
								// DTO以外のコード生成
								else {
									try {
										generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
										success++;
									} catch (GenerateException e) {
										fail++;
										printError("コード生成に失敗。" + getStackErrorMessage(e));
									}
								}
							}
						}
					}
					// Java_Entityテンプレート向けロジック
					else if (templateSet.getName().equals("java_Entity")) {
						TableCategory tableCategory = (TableCategory) dataModel.get(GeneratorDataMapKey.TABLE_CATEGORY.getKey());

						// モデルの導出項目の設定
						tableCategory.setupDerivationField(tableCategory.getTableDefinitionMap());

						// テーブル定義毎にコードを生成
						for (TableDefinition tableDef : tableCategory.getTableDefinitions()) {

							// 埋込データの初期化
							dataModelForOutput = new HashMap<>();
							dataModelForOutput.putAll(dataModel);

							// 出力先ファイル名の構築
							String appPackagePath = tableCategory.getJavaPackage().replaceAll("\\.", "/");
							String codeDirectoryPath = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir().replaceAll("\\{appPackage\\}", appPackagePath));
							String filePath = codeDirectoryPath + "/" + template.getFileName().replaceAll("\\{tableName\\}", capitalizeFirstString(tableDef.getName()));

							// Freemerker 埋込データの設定
							dataModelForOutput.put(GeneratorDataMapKey.TABLE.getKey(), tableDef);

							// コード生成
							try {
								generateEachCode(dataModelForOutput, template, codeDirectoryPath, filePath);
								success++;
							} catch (GenerateException e) {
								fail++;
								printError("コード生成に失敗。" + getStackErrorMessage(e));
							}
						}
					} else if (templateSet.getName().equals("screen")) {

						dataModelForOutput = new HashMap<>();
						dataModelForOutput.putAll(dataModel);

						ProjectDefinition project = (ProjectDefinition)dataModelForOutput.get(GeneratorDataMapKey.PROJECT.getKey());

						String codeDirectory = null;
						String fileName = null;

						if(template.getPatternList().contains("1T")){
							// コード出力先ディレクトリの構築
							codeDirectory = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir());

							// コードファイル名の構築
							fileName = codeDirectory + "/" + template.getFileName();

							// コード生成
							try {
								generateEachCode(dataModelForOutput, template, codeDirectory, fileName);
								success++;
							} catch (GenerateException e) {
								fail++;
								printError("コード生成に失敗。" + getStackErrorMessage(e));
							}
						}

						// ユースケース表示優先度でソート
						if (project.getUsecases() != null) {
							project.getUsecases().sort(new UsecaseDefinitionComparator());
						}
						
						for (UsecaseDefinition usecase : project.getUsecases()) {

							dataModelForOutput.put(GeneratorDataMapKey.USECASE.getKey(), usecase);

							//UC単位の生成物
							if(template.getPatternList().contains("UC")){

								// ディレクトリ生成
								codeDirectory = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir());
								codeDirectory = codeDirectory.replaceAll("\\{usecasename\\}", usecase.getUsecaseName().toLowerCase());

								// コードファイル名の構築
								fileName = codeDirectory + "/" + template.getFileName();
								fileName = fileName.replaceAll("\\{usecasename\\}", usecase.getUsecaseName().toLowerCase());

								// コード生成
								try {
									generateEachCode(dataModelForOutput, template, codeDirectory, fileName);
									success++;
								} catch (GenerateException e) {
									fail++;
									printError("コード生成に失敗。" + getStackErrorMessage(e));
								}
							}

							for (ScreenDefinition screen : usecase.getScreenFlow()){
								
								// フィールド定義表示優先度でソート
								if (screen.getFieldList() != null) {
									screen.getFieldList().sort(new FieldDefinitionComparator());
								}
								
								//画面単位の生成物
								dataModelForOutput.put(GeneratorDataMapKey.SCREEN.getKey(), screen);

								if(template.getPatternList().contains("SC")){
									// ディレクトリ生成
									codeDirectory = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir());
									codeDirectory = codeDirectory.replaceAll("\\{usecasename\\}", usecase.getUsecaseName().toLowerCase());

									fileName = codeDirectory + "/" + template.getFileName();
									fileName = fileName.replaceAll("\\{usecasename\\}", usecase.getUsecaseName().toLowerCase());
									fileName = fileName.replaceAll("\\{screenName\\}", screen.getScreenName().toLowerCase());

									// コード生成
									try {
										generateEachCode(dataModelForOutput, template, codeDirectory, fileName);
										success++;
									} catch (GenerateException e) {
										fail++;
										printError("コード生成に失敗。" + getStackErrorMessage(e));
									}
								}

								for (FieldGroup fieldGroup : screen.getFieldGroupList()) {
									for (EventDefinition event : fieldGroup.getEventList()) {
										//イベント単位の生成物
										dataModelForOutput.put(GeneratorDataMapKey.EVENT.getKey(), event);

										if(template.getPatternList().contains("SV")){
											// ディレクトリ生成
											codeDirectory = template.getDir().replaceAll("\\{outputdir\\}", templateSet.getOutputdir());
											codeDirectory = codeDirectory.replaceAll("\\{usecasename\\}", usecase.getUsecaseName().toLowerCase());

											fileName = codeDirectory + "/" + template.getFileName();
											fileName = fileName.replaceAll("\\{screenName\\}", screen.getScreenName().toLowerCase());
											fileName = fileName.replaceAll("\\{eventName\\}", event.getEventName().substring(0, 1).toUpperCase().concat(event.getEventName().substring(1, event.getEventName().length())));

											// コード生成
											try {
												generateEachCode(dataModelForOutput, template, codeDirectory, fileName);
												success++;
											} catch (GenerateException e) {
												fail++;
												printError("コード生成に失敗。" + getStackErrorMessage(e));
											}
										}
									}
								}
							}
						}
					}
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

	/**
	 * ネストされたデータ項目内のリストに対して再帰的にコード生成を行う。
	 *
	 * @param clazz
	 * @param items
	 * @param dataModel
	 * @param template
	 * @param codeDirectoryPath
	 * @param filePathPrefix
	 * @param success
	 * @param fail
	 */
	private <T extends ItemDefinition> void generateChildItemCod(Class<T> clazz, List<T> items, Map<String, Object> dataModel, TemplateConfig template, String codeDirectoryPath, String filePathPrefix) {

		dataModel.remove("itemName");
		dataModel.remove(GeneratorDataMapKey.API_ITEMS.getKey());

		if (!items.isEmpty()) {
			for (T item : items) {
				if (item.getModelType() == ModelType.Entity) {

					dataModel.put(GeneratorDataMapKey.API_ITEMNAME.getKey(), item.getName());
					dataModel.put(GeneratorDataMapKey.API_ITEMS.getKey(), item.getItemDefinitions());
					try {
						generateEachCode(dataModel, template, codeDirectoryPath, getSimpleFileNameString(filePathPrefix) + capitalizeFirstString(item.getName()) + "." + getExtensionString(filePathPrefix));
						success++;
					} catch (GenerateException e) {
						fail++;
						printError("コード生成に失敗。" + getStackErrorMessage(e));
					}
					generateChildItemCod(clazz, (List<T>) item.getItemDefinitions(), dataModel, template, codeDirectoryPath, filePathPrefix);
				}
			}
		}
	}
}
