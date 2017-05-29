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
package com.ibm.jp.awag.generator;

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.*;
import static com.ibm.jp.awag.generator.common.util.POIUtil.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ibm.jp.awag.generator.common.GenerateException;
import com.ibm.jp.awag.generator.common.util.FormatValidator;
import com.ibm.jp.awag.generator.model.ColumnDefinition;
import com.ibm.jp.awag.generator.model.ColumnDefinitionComparator;
import com.ibm.jp.awag.generator.model.DisplayCode;
import com.ibm.jp.awag.generator.model.FormatValidationDefinition;
import com.ibm.jp.awag.generator.model.RelationDefinition;
import com.ibm.jp.awag.generator.model.ResourceDefinition;
import com.ibm.jp.awag.generator.model.ResourceDefinitionBook;
import com.ibm.jp.awag.generator.model.ColumnDefinition.ConditionOperator;
import com.ibm.jp.awag.generator.model.ColumnDefinition.DataType;
import com.ibm.jp.awag.generator.model.ColumnDefinition.HtmlType;
import com.ibm.jp.awag.generator.model.FormatValidationDefinition.FormatType;
import com.ibm.jp.awag.generator.model.RelationDefinition.JoinKey;
import com.ibm.jp.awag.generator.model.RelationDefinition.RelationPattern;
import com.ibm.jp.awag.generator.model.ResourceDefinition.VersionType;

/**
 * リソース定義ファイル（XLS）を読み込むリーダークラス。
 *
 */
public class XLSResourceDefinitionReader {

	/** リソース定義ファイルのパス */
	String xlsPath;
	
	/** リソース定義ファイルの各定義とセルアドレスを紐付けるMapオブジェクト */
	private Map<String, String> definitionSheetMappings;
	
	/**
	 * 唯一のコンストラクタ
	 * リソース定義ファイルを指定してインスタンスを初期化する。
	 * @param xlsPath リソース定義ファイル（XLS）のパス
	 * @param definitionSheetMappings
	 */
	public XLSResourceDefinitionReader(Map<String, String> definitionSheetMappings) {
		this.definitionSheetMappings = definitionSheetMappings;
	}

	/**
	 * リソース定義ファイルを読み込み、ResourceDefinitionBookオブジェクトにマップする。
	 * @return リソース定義を格納したResourceDefinitionBookオブジェクト
	 */
	public ResourceDefinitionBook read(String xlsPath) {

		this.xlsPath = xlsPath;

		ResourceDefinitionBook resourceDefBook = new ResourceDefinitionBook();

		// 指定されたパスのリソース定義ファイルを読み込み、コード定義、リソース定義を構築する
		try (InputStream is = Files.newInputStream(new File(this.xlsPath).toPath()); Workbook workbook = WorkbookFactory.create(is)) {

			resourceDefBook.setDisplayCodeDefs(buildCodeDefinition(workbook));
			resourceDefBook.setResourceDefs(buildResourceDefinitions(workbook, resourceDefBook.getDisplayCodeDefs()));

		} catch (IOException | EncryptedDocumentException | InvalidFormatException | NullPointerException e ) {
			throw new GenerateException("リソース定義ファイルのOpenに失敗。[Path]" + xlsPath, e);
		}

		return resourceDefBook;
	}

	/**
	 * 指定したWorkbookに含まれるリソース定義シートを読み込み、ResourceDefinitionオブジェクトにマップする。
	 * @param workbook 対象のWorkbookオブジェクト
	 * @return リソース定義を格納したリスト
	 */
	private List<ResourceDefinition> buildResourceDefinitions(Workbook workbook, Map<String, List<DisplayCode>> codeDefs) {
		List<ResourceDefinition> resourceDefs = new ArrayList<ResourceDefinition>();

		// シート毎にリソース定義を構築する
		workbook.forEach(sheet -> {
			try {
				Cell topCell = getCell(sheet, this.definitionSheetMappings.get("sheetType"));
				if (topCell.getStringCellValue().equals("リソース定義")) {
					resourceDefs.add(buildResourceDefinition(sheet, codeDefs));
				}
			} catch (RuntimeException e) {
				throw new GenerateException("リソース定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);
			}
		});
		
		// 関連リソース定義紐付け用にリソース定義のMapを構築する
		Map<String, ResourceDefinition> resourceDefMap = null;
		try {
			resourceDefMap = resourceDefs.stream().collect(Collectors.toMap(ResourceDefinition::getResourceName, s -> s));
		} catch (Exception e) {
			throw new GenerateException("リソース名の指定が不正です。", e);
		}
		
		// 関連リソース定義を設定する
		for (ResourceDefinition resourceDef : resourceDefs) {

			Map<String, ColumnDefinition> resourcecolumnDefMap = null;
			try {
				resourcecolumnDefMap = resourceDef.getColumnDefinitions().stream().collect(Collectors.toMap(ColumnDefinition::getColumnName, s -> s));
			} catch (Exception e) {
					throw new GenerateException("カラム名（英字）の指定が不正です。[Resource]" + resourceDef.getResourceName(), e);
			}

			for (RelationDefinition relationDef : resourceDef.getRelationDefinitions()) {
				ResourceDefinition resourceDefEmbed = resourceDefMap.get(relationDef.getDependentResourceName());
				if (resourceDefEmbed != null) {
					
					Map<String, ColumnDefinition> embeddedResourcecolumnDefMap = null;
					try {
						embeddedResourcecolumnDefMap = resourceDefEmbed.getColumnDefinitions().stream().collect(Collectors.toMap(ColumnDefinition::getColumnName, s -> s));
					} catch (Exception e) {
						throw new GenerateException("関連定義の結合キー（子）の指定が不正です。[Embedded Resource]" + relationDef.getDependentResourceName(), e);
					}
					
					// 関連リソース定義の結合キー存在チェック
					for (JoinKey joinKey : relationDef.getJoinKeyList()) {
						if (resourcecolumnDefMap.get(joinKey.getKeyParent()) == null) {
							throw new GenerateException("関連リソースに定義された結合キー（親）が存在しません。[Resource]" + resourceDef.getResourceDisplayName() + "[Embedded Resource]" + resourceDefEmbed.getResourceName() + "[Join Key]" + joinKey.getKeyParent(), new RuntimeException());					
						}
						
						if (embeddedResourcecolumnDefMap.get(joinKey.getKeyDependence()) == null) {
							throw new GenerateException("関連リソースに定義された結合キー（子）が存在しません。[Resource]" + resourceDef.getResourceDisplayName() + "[Embedded Resource]" + resourceDefEmbed.getResourceName() + "[Join Key]" + joinKey.getKeyDependence(), new RuntimeException());												
						}
					}
					relationDef.setResourceDefinition(resourceDefEmbed);
				} else {
					// 関連リソース定義のリソース存在チェック
					throw new GenerateException("関連リソースに定義されたリソースが存在しません。[Resource]" + resourceDef.getResourceDisplayName() + "[Embedded Resource]" + relationDef.getDependentResourceName(), new RuntimeException());					
				}
			}
		}
		
		return resourceDefs;
	}

	/**
	 * 指定したWorkbookに含まれるコード定義シートを読み込み、Mapオブジェクトにマップする。
	 * @param workbook 対象のWorkbookオブジェクト
	 * @return コード定義を格納したマップ
	 */
	private Map<String, List<DisplayCode>> buildCodeDefinition(Workbook workbook) {
		Map<String, List<DisplayCode>> displayCodeDefs = null;

		for (Sheet sheet : workbook) {
			try {
				Cell topCell = getCell(sheet, this.definitionSheetMappings.get("sheetType"));
				if (topCell.getStringCellValue().equals("コード定義")) {
					displayCodeDefs = buildCodeDefinition(sheet);
				}
			} catch (RuntimeException e) {
				throw new GenerateException("コード定義シートの読み込みに失敗。[Book]" + sheet.getSheetName(), e);
			}
		}

		return displayCodeDefs;
	}

	/**
	 * 指定したリソース定義シートを読み込み、ResourceDefinitionオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return リソース定義を格納したResourceDefinitionオブジェクト
	 */
	private ResourceDefinition buildResourceDefinition(Sheet sheet, Map<String, List<DisplayCode>> codeDefs) {

		ResourceDefinition resourceDef = new ResourceDefinition();
		resourceDef.setVersionType(VersionType.NONE);

		// リソース名
		resourceDef.setResourceDisplayName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("resourceDisplayName"))));
		
		// リソース名（英字）
		resourceDef.setResourceName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("resourceName"))));
		
		// パッケージ名
		resourceDef.setPackageName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("packageName"))));
		
		// 最大取得件数
		Double numberValue = getNumberValue(getCell(sheet, this.definitionSheetMappings.get("maxResult")));
		if (numberValue != null) {
			resourceDef.setMaxResult(Integer.valueOf(numberValue.intValue()));
		}
		
		// 画面コード出力
		String isStr = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("webCodeGen")));
		boolean is = true;
		if (isStr != null && isStr.equals("N")) {
			is = false;
		}
		resourceDef.setWebGenEnabled(is);
		
		// スキーマ名
		resourceDef.setSchemaName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("schemaName"))));
		
		// テーブル名（英字）
		resourceDef.setTableName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("tableName"))));
		
		// リソース・オプション
		try {
			resourceDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("resource_options")))));
		} catch(Exception e) {
			throw new GenerateException("リソース定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] リソース・オプションの書式が不正です。", e);
		}
		
		// データ項目定義の構築
		List<ColumnDefinition> columnDefs = new ArrayList<ColumnDefinition>();
		String firstColumnCellAddr = this.definitionSheetMappings.get("column_name");
		Cell firstColumnCell = getCell(sheet, firstColumnCellAddr);
		int numOfColumn = countColumnNum(sheet, firstColumnCellAddr);
		int offset = firstColumnCell.getRowIndex();
		for (int i = 0; i < numOfColumn; i++) {

			ColumnDefinition columnDef = new ColumnDefinition();
			int targetRow = offset + i;

			// PK
			Cell cell = getCell(sheet, this.definitionSheetMappings.get("column_isPk"));
			isStr = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			is = false;
			if (isStr != null && isStr.equals("Y")) {
				is = true;
			}
			columnDef.setPk(is);

			// カラム名（英字）
			cell = getCell(sheet, this.definitionSheetMappings.get("column_columnName"));
			String value = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			columnDef.setColumnName(Optional.ofNullable(value).map(s -> s.toUpperCase()).orElse(null));

			// Null許可
			cell = getCell(sheet, this.definitionSheetMappings.get("column_isAllowedNull"));
			isStr = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			is = false;
			if (isStr != null && isStr.equals("Y")) {
				is = true;
			}
			columnDef.setAllowedNull(is);

			// データ型
			cell = getCell(sheet, this.definitionSheetMappings.get("column_dataType"));
			String dataType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (dataType != null && !dataType.isEmpty()) {
				columnDef.setDataType(DataType.valueOf(dataType));				
			}
			
			// 全体桁数
			cell = getCell(sheet, this.definitionSheetMappings.get("column_length"));
			numberValue = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (numberValue != null) {
				columnDef.setLength(numberValue.intValue());
			}

			// 小数桁数
			cell = getCell(sheet, this.definitionSheetMappings.get("column_fraction"));
			numberValue = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (numberValue != null) {
				columnDef.setFraction(numberValue.intValue());
			}
			
			// 画面表示項目名
			cell = getCell(sheet, this.definitionSheetMappings.get("column_displayName"));
			columnDef.setDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex())));

			// パラメータ名
			cell = getCell(sheet, this.definitionSheetMappings.get("column_fieldName"));
			columnDef.setFieldName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex())));

			// HTML要素-タグ種別
			cell = getCell(sheet, this.definitionSheetMappings.get("column_htmlType"));
			String htmlType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (htmlType != null && !htmlType.isEmpty()) {
				columnDef.setHtmlType(HtmlType.valueOf(htmlType));
			} else {
				columnDef.setHtmlType(HtmlType.NONE);
			}
			
			// HTML要素-コードリスト
			cell = getCell(sheet, this.definitionSheetMappings.get("column_codelistId"));	
			String codeListId = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (codeListId != null && !codeListId.isEmpty()) {
				columnDef.setCodeListId(codeListId);
			}
			columnDefs.add(columnDef);
			
			// 入力チェック
			cell = getCell(sheet, this.definitionSheetMappings.get("column_formatValidationRule"));
			String formatTypeString = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (!(formatTypeString == null) && !formatTypeString.isEmpty()) {
				FormatValidationDefinition validationDefinition = new FormatValidationDefinition();

				// 入力チェック-形式
				validationDefinition.setFormatType(FormatType.valueOf(formatTypeString));

				// 入力チェック-最大桁数
				numberValue = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex() + 1));
				if (numberValue != null) {
					validationDefinition.setMaxLength(numberValue.intValue());
				}

				// 入力チェック-最小桁数
				numberValue = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex() + 2));
				if (numberValue != null) {
					validationDefinition.setMinLength(numberValue.intValue());
				}

				// 入力チェック-一致桁数
				numberValue = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex() + 3));
				if (numberValue != null) {
					validationDefinition.setLength(numberValue.intValue());
				}

				// 入力チェック-DECIMAL用 整数、小数桁数
				if (FormatType.valueOf(formatTypeString).equals(FormatType.DECIMAL)) {
					validationDefinition.setInteger(replaceNullwithZero(getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex() + 1))).intValue());
					validationDefinition.setFraction(replaceNullwithZero(getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex() + 2))).intValue());
				}

				columnDef.setFormatValidationRule(validationDefinition);
			}

			// 検索-パラメータ
			cell = getCell(sheet, this.definitionSheetMappings.get("column_isSerchParam"));
			isStr = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			is = false;
			if (isStr != null && isStr.equals("Y")) {
				is = true;
			}
			columnDef.setSearchParam(is);

			// 検索-一致条件
			if (is) {
				cell = getCell(sheet, this.definitionSheetMappings.get("column_conditionOperator"));
				String str = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				if (str.equals("完全")) {
					columnDef.setConditionOperator(ConditionOperator.EXACT);
				} else if (str.equals("前方")) {
					columnDef.setConditionOperator(ConditionOperator.PREFIX);
				} else if (str.equals("部分")) {
					columnDef.setConditionOperator(ConditionOperator.PARTIAL);
				}
			}
			
			// ロック用バージョン
			cell = getCell(sheet, this.definitionSheetMappings.get("column_isVersion"));
			isStr = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			is = false;
			
			if (isStr != null && isStr.equals("Y")) {
				is = true;
				
				if (columnDef.getDataType().equals(DataType.TIMESTAMP)) {
					resourceDef.setVersionType(VersionType.TIMESTAMP);
				} else if(columnDef.getDataType().equals(DataType.FLOAT)) {
					resourceDef.setVersionType(VersionType.FLOAT);
				}
			}
			columnDef.setVersion(is);
			
			// 表示順序
			cell = getCell(sheet, this.definitionSheetMappings.get("column_displayOrder"));
			numberValue = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if (numberValue != null) {
				columnDef.setDisplayOrder(numberValue.intValue());
			}
			
			// オプション
			cell = getCell(sheet, this.definitionSheetMappings.get("column_options"));
			try {
				columnDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()))));
			} catch(Exception e) {
				throw new GenerateException("リソース定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Column]" + columnDef.getColumnName() + "[Error] オプションの書式が不正です。", e);
			}
		}
		
		// 関連定義の構築
		Map<String, RelationDefinition> relationDefMap = new HashMap<>();
		
		firstColumnCellAddr = this.definitionSheetMappings.get("relation_dependenteResource");
		firstColumnCell = getCell(sheet, firstColumnCellAddr);
		numOfColumn = countColumnNum(sheet, firstColumnCellAddr);
		offset = firstColumnCell.getRowIndex();
		
		for (int i = 0; i < numOfColumn; i++) {
			
			int targetRow = offset + i;
			
			// 子リソース
			Cell cell = getCell(sheet, this.definitionSheetMappings.get("relation_dependenteResource"));
			String dependentResource = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			
			if (dependentResource == null || dependentResource.isEmpty()) {
				continue;
			}
			
			RelationDefinition relationDef = Optional.ofNullable(relationDefMap.get(dependentResource)).orElse(new RelationDefinition());
			relationDef.setDependentResourceName(dependentResource);
			
			// 関連パターン
			cell = getCell(sheet, this.definitionSheetMappings.get("relation_pattern"));		
			relationDef.setRelationPattern(RelationPattern.patternOf(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()))));

			// 結合キー
			JoinKey joinKey = new JoinKey();
			
			// 結合キー親
			cell = getCell(sheet, this.definitionSheetMappings.get("relation_joinKeyParent"));
			String value = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			joinKey.setKeyParent(Optional.ofNullable(value).map(s -> s.toUpperCase()).orElse(null));

			// 結合キー子
			cell = getCell(sheet, this.definitionSheetMappings.get("relation_joinKeyDependence"));
			value = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			joinKey.setKeyDependence(Optional.ofNullable(value).map(s -> s.toUpperCase()).orElse(null));

			relationDef.addJoinKey(joinKey);
			
			relationDefMap.put(dependentResource, relationDef);
		}

		resourceDef.setColumnDefinitions(columnDefs);
		resourceDef.setRelationDefinitions(new ArrayList<RelationDefinition>(relationDefMap.values()));
		resourceDef.setPkFields();
		resourceDef.setParamFields();

		// リソース定義シートの入力チェックを行う
		List<String> errorList = validate(resourceDef, codeDefs);
		if (!errorList.isEmpty()) {
			throw new GenerateException("リソース定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error]" + errorList, null);
		}
		
		// 項目定義を表示順序でソートする
		resourceDef.getColumnDefinitions().sort(new ColumnDefinitionComparator());
       
		return resourceDef;
	}
	
	/**
	 * リソース定義シートの入力チェックを行う。
	 * @param resourceDef リソース定義シートの値を読み込んだResourceDefinitionオブジェクト
	 * @return エラーリスト
	 */
	private List<String> validate(ResourceDefinition resourceDef, Map<String, List<DisplayCode>> codeDefs) {

		List<String> errorList = new ArrayList<String>();

		// 単項目チェック
		FormatValidator validator = new FormatValidator();
		errorList.addAll(validator.isValid(resourceDef));

		int versionCnt = 0;
		
		for (ColumnDefinition columnDef : resourceDef.getColumnDefinitions()) {
			List<String> cloumnDefErrorList = validator.isValid(columnDef);
			
			if(!cloumnDefErrorList.isEmpty()) {
				errorList.add("カラム定義入力チェックエラー。[Column]" + columnDef.getColumnName() + cloumnDefErrorList);
			} else {
				
				// 相関チェック
				if (columnDef.getDataType().equals(DataType.DECIMAL) && columnDef.getLength() == 0 && columnDef.getFraction() == 0) {
					errorList.add("全体桁数または小数桁数が未入力。[Column]" + columnDef.getColumnName());
				}

				if ((columnDef.getHtmlType().equals(HtmlType.RADIO) || columnDef.getHtmlType().equals(HtmlType.SELECT)) && (columnDef.getCodeListId() == null || columnDef.getCodeListId().isEmpty())) {
					errorList.add("コードリストが未入力。[Column]" + columnDef.getColumnName());
				}
				
				if (columnDef.getCodeListId() != null && !(columnDef.getCodeListId().isEmpty()) && !codeDefs.containsKey(columnDef.getCodeListId())) {
					errorList.add("対応するコードリストがコード定義シートにありません。[Column]" + columnDef.getColumnName());					
				}

				if (columnDef.getFormatValidationRule() != null && !(columnDef.getFormatValidationRule().getFormatType().equals(FormatType.DATE) || columnDef.getFormatValidationRule().getFormatType().equals(FormatType.TIMESTAMP)) && (columnDef.getFormatValidationRule().getMaxLength() == 0 && columnDef.getFormatValidationRule().getLength() == 0)) {
					errorList.add("入力チェックの最大桁数または一致桁数が未入力。[Column]" + columnDef.getColumnName());
				}
				
				if (columnDef.isSearchParam() && columnDef.getConditionOperator() == null) {
					errorList.add("一致条件が未入力。[Column]" + columnDef.getColumnName());
				}

				if (columnDef.isVersion()) {
					versionCnt++;	
					if (versionCnt > 1) {
						errorList.add("ロック用バージョンは複数指定できません。[Column]" + columnDef.getColumnName());					
					}
					
					if (!columnDef.getDataType().equals(DataType.TIMESTAMP) && !columnDef.getDataType().equals(DataType.FLOAT)) {
						errorList.add("ロック用バージョンはTIMESTAMP, FLOAT以外のカラムには指定できません。[Column]" + columnDef.getColumnName());
					}
				}
			}
		}
		
		if (resourceDef.getPkFields().isEmpty()) {
			errorList.add("PK列が未指定。");
		}
		
		return errorList;
	}

	/**
	 * 指定したコード定義シートを読み込み、Mapオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return コード定義を格納したマップ
	 */
	private Map<String, List<DisplayCode>> buildCodeDefinition(Sheet sheet) {
		
		Map<String, List<DisplayCode>> newCodeDefMap = new HashMap<String, List<DisplayCode>>();
		
		String firstCodeCellAddr = this.definitionSheetMappings.get("codelist_id");
		Cell firstColumnCell = getCell(sheet, firstCodeCellAddr);
		int numOfCode = countColumnNum(sheet, firstCodeCellAddr);
		int offset = firstColumnCell.getRowIndex();
		for (int i = 0; i < numOfCode; i++) {
		
			DisplayCode codeDef = new DisplayCode();
			int targetRow = offset + i;
			
			String codeId = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(0));
			codeDef.setLabel(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(1)));
			codeDef.setValue(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(2)));
			
			List<DisplayCode> codeDefs = Optional.ofNullable(newCodeDefMap.get(codeId)).orElse(new ArrayList<DisplayCode>());
			codeDefs.add(codeDef);
			newCodeDefMap.put(codeId, codeDefs);
		}
		
		return newCodeDefMap;
	}

}
