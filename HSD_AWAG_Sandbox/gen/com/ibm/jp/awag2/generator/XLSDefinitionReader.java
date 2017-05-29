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

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.*;
import static com.ibm.jp.awag.generator.common.util.POIUtil.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ibm.jp.awag2.generator.model.common.DataType;
import com.ibm.jp.awag2.generator.model.common.FormatType;
import com.ibm.jp.awag2.generator.model.common.GeneratorDataMapKey;
import com.ibm.jp.awag2.generator.model.common.HTTPMethodType;
import com.ibm.jp.awag2.generator.model.common.ProjectDefinition;
import com.ibm.jp.awag2.generator.model.common.ProjectDefinition.DesignType;
import com.ibm.jp.awag2.generator.model.java.APIDefinition;
import com.ibm.jp.awag2.generator.model.java.APIDefinition.APIType;
import com.ibm.jp.awag2.generator.model.java.ColumnDefinition;
import com.ibm.jp.awag2.generator.model.java.InputItemDefinition;
import com.ibm.jp.awag2.generator.model.java.ItemDefinition;
import com.ibm.jp.awag2.generator.model.java.ItemDefinition.ModelType;
import com.ibm.jp.awag2.generator.model.java.OutputItemDefinition;
import com.ibm.jp.awag2.generator.model.java.RelationDefinition;
import com.ibm.jp.awag2.generator.model.java.RelationDefinition.JoinKey;
import com.ibm.jp.awag2.generator.model.java.RelationDefinition.RelationPattern;
import com.ibm.jp.awag2.generator.model.java.TableCategory;
import com.ibm.jp.awag2.generator.model.java.TableDefinition;
import com.ibm.jp.awag2.generator.model.java.TableDefinition.VersionType;
import com.ibm.jp.awag2.generator.model.web.ArrayListWrapper;
import com.ibm.jp.awag2.generator.model.web.DisplayCode;
import com.ibm.jp.awag2.generator.model.web.EventDefinition;
import com.ibm.jp.awag2.generator.model.web.EventDefinition.EventFireType;
import com.ibm.jp.awag2.generator.model.web.FieldDefinition;
import com.ibm.jp.awag2.generator.model.web.FieldDefinition.HtmlType;
import com.ibm.jp.awag2.generator.model.web.FieldGroup;
import com.ibm.jp.awag2.generator.model.web.FormatValidationDefinition;
import com.ibm.jp.awag2.generator.model.web.ScreenDefinition;
import com.ibm.jp.awag2.generator.model.web.ScreenDefinition.ScreenType;
import com.ibm.jp.awag2.generator.model.web.UsecaseDefinition;

/**
 * Excel定義ファイルを読み込むリーダークラス。
 *
 */
public class XLSDefinitionReader {

	/** Excel定義ファイルのパス */
	String xlsPath;

	/** Excel定義ファイルの各定義とセルアドレスを紐付けるMapオブジェクト */
	private Map<String, String> definitionSheetMappings;	

	/**
	 * 唯一のコンストラクタ
	 * Excel定義ファイルを指定してインスタンスを初期化する。
	 * @param xlsPath Excel定義ファイルのパス
	 * @param definitionSheetMappings
	 */
	public XLSDefinitionReader(Map<String, String> definitionSheetMappings) {
		this.definitionSheetMappings = definitionSheetMappings;
	}

	/**
	 * Excel定義ファイルを読み込み、Mapオブジェクトにマップする。
	 *
	 * @param xlsPath 読み込み対象のExcelパス
	 * @return Excel定義を格納したマップ
	 */
	public Map<String, Object> read(String xlsPath) {

		this.xlsPath = xlsPath;

		Map<String, Object> xlsDefMap = new HashMap<String, Object>();

		XLSDefinitionBook xlsDefBook = new XLSDefinitionBook();

		// Excel定義ファイルを読み込み、コード定義、画面項目定義、テーブル定義、API定義を構築する
		try (InputStream is = Files.newInputStream(new File(this.xlsPath).toPath()); Workbook workbook = WorkbookFactory.create(is)) {

			String systemVersion = null;
			try {
				Sheet systemSheet = workbook.getSheet("System");
				systemVersion = getEvaluatedCellValue(getCell(systemSheet, this.definitionSheetMappings.get("system_version")));
			} catch (RuntimeException e) {
				systemVersion = "N/A";
			}
			xlsDefMap.put(GeneratorDataMapKey.INPUT_DATA_GENERATOR_VERSION.getKey(), systemVersion);
			
			//Excelより取得したコード定義をセット
			xlsDefBook.setDisplayCodeDefs(buildCodeDefinition(workbook));
			//Excelより取得した画面項目定義をセット
			xlsDefBook.setProjectDefs(buildProjectDefinitions(workbook, xlsDefBook.getDisplayCodeDefs()));
			print("画面項目定義シート数：" + xlsDefBook.getProjectDefs().size());

			//SPA・API定義をマップに格納
			ProjectDefinition pd = organizeforScreen(xlsDefBook);
			List<APIDefinition> apiDefinitionList = buildAPIDefinition(workbook);
			pd.setApiDefinitions(apiDefinitionList);
			xlsDefMap.put(GeneratorDataMapKey.PROJECT.getKey(), pd);
			print("API定義シート数：" + pd.getApiDefinitions().size());

			//Excelより取得したテーブル定義をセット
			xlsDefBook.setTableCategory(buildTableCategory(workbook));
			print("テーブル定義シート数：" + xlsDefBook.getTableCategory().getTableDefinitions().size());

			//JPA定義をマップに格納
			TableCategory tc = organizeforTable(xlsDefBook);
			xlsDefMap.put(GeneratorDataMapKey.TABLE_CATEGORY.getKey(), tc);

		} catch (IOException | EncryptedDocumentException | InvalidFormatException | NullPointerException e ) {
			throw new GenerateException("Excel定義ファイルのOpenに失敗。[Path]" + xlsPath, e);
		}

		return xlsDefMap;

	}

	// ---------------------------------------------------------------------------
	// Excelブックより取得した定義情報を整理する
	// ---------------------------------------------------------------------------

	/**
	 * 画面項目定義シートから読み込んだSPA定義情報を、ProjectDefinitionオブジェクトにセットする。
	 * @param xlsDefBook 対象のXLSDefBookオブジェクト
	 * @return SPA定義を格納したProjectDefinitionオブジェクト
	 */
	private ProjectDefinition organizeforScreen(XLSDefinitionBook xlsDefBook){

		ProjectDefinition projectDef = new ProjectDefinition();

		List<UsecaseDefinition> usecaseDefs = projectDef.getUsecases();

		int number = 1;
		String ucDisplayName = null;

		List<ProjectDefinition> pdList = xlsDefBook.getProjectDefs();

		UsecaseDefinition usecase = null;
		if(pdList.size()>0){
			usecase = new UsecaseDefinition();
			usecaseDefs.add(usecase);
		}

		for(ProjectDefinition pd :pdList){

			//アプリ名
			projectDef.setAppName(pd.getAppName());

			//デザインタイプ
			projectDef.setDesignType(pd.getDesignType());

			//コード定義
			projectDef.setDisplayCodeDefs(pd.getDisplayCodeDefs());

			//ユースケース定義
			List<UsecaseDefinition> ucList = pd.getUsecases();

			for(UsecaseDefinition uc : ucList){

				//初回以外
				if(number!=1){
					//メニュー名が1シート前の値と異なる場合
					if(!ucDisplayName.equals(uc.getUsecaseDisplayName())){

						//ユースケースを初期化
						usecase = new UsecaseDefinition();

						//ユースケースリストに先に追加
						usecaseDefs.add(usecase);
//						print("「" + uc.getUsecaseDisplayName() + "」用にユースケースを初期化");

					}
				}

				//メニュー名　※上書き
				usecase.setUsecaseDisplayName(uc.getUsecaseDisplayName());

				//2回目以降の判定用にメニュー名を変数にセット
				ucDisplayName = uc.getUsecaseDisplayName();

				//メニュー名（英字）　※上書き
				usecase.setUsecaseName(uc.getUsecaseName());

				//ユースケース定義
				List<ScreenDefinition> scList = uc.getScreenFlow();

				for(ScreenDefinition sc : scList){

					ScreenDefinition screenDef = new ScreenDefinition();

					//画面名
					screenDef.setScreenDisplayName(sc.getScreenDisplayName());

					//画面名（英字）
					screenDef.setScreenName(sc.getScreenName());

					//画面タイプ
					screenDef.setScreenType(sc.getScreenType());

					//標準列数
					screenDef.setCol(sc.getCol());

					//メニュー内トップ
					screenDef.setMenuTop(sc.isMenuTop());

					//画面オプション
					screenDef.setOptions(sc.getOptions());

					//グループ名
					screenDef.setFieldGroupList(sc.getFieldGroupList());

					//イベント定義
					screenDef.setEventList(sc.getEventList());

					//画面項目仕様
					screenDef.setFieldList(sc.getFieldList());

					usecase.getScreenFlow().add(sc);

				}

				number++;

			}

		}

		projectDef.setUsecases(usecaseDefs);

		return projectDef;
	}

	/**
	 * テーブル定義シートから読み込んだJPA定義情報を、TableCategoryオブジェクトにセットする。
	 * @param xlsDefBook 対象のXLSDefBookオブジェクト
	 * @return JPA定義を格納したTableCategoryオブジェクト
	 */
	private TableCategory organizeforTable(XLSDefinitionBook xlsDefBook){

		TableCategory TableCategory = new TableCategory();

		// パッケージ名 ※後勝ち
		TableCategory.setJavaPackage(xlsDefBook.getTableCategory().getJavaPackage());

		List<TableDefinition> TableDefs = new ArrayList<TableDefinition>();

		List<TableDefinition> tdList = xlsDefBook.getTableCategory().getTableDefinitions();

		for(TableDefinition td : tdList){

			TableDefinition tableDef = new TableDefinition();

			List<ColumnDefinition> ColumnDefs = new ArrayList<ColumnDefinition>();
			List<RelationDefinition> RelationDefs = new ArrayList<RelationDefinition>();

			//最大取得件数
			tableDef.setMaxResult(td.getMaxResult());

			//テーブル・オプション
			tableDef.setOptions(td.getOptions());

			//スキーマ名
			tableDef.setSchemaName(td.getSchemaName());

			//テーブル名
			tableDef.setNameLocal(td.getNameLocal());

			//テーブル名（英字）
			tableDef.setName(td.getName());

			// ---------------------------------------------------------------------------
			// 各列のデータ一覧情報をモデルにセット
			// ---------------------------------------------------------------------------

			List<ColumnDefinition> cdList = td.getColumnDefinitions();

			for(ColumnDefinition cd :cdList){

				ColumnDefinition columnDef = new ColumnDefinition();

				//PK
				columnDef.setPk(cd.isPk());

				//カラム名
				columnDef.setNameLocal(cd.getNameLocal());

				//カラム名（英字）
				columnDef.setName(cd.getName());

				//Null許可
				columnDef.setNotNull(cd.isNotNull());

				//データ型
				columnDef.setDataType(cd.getDataType());

				//全体桁数
				columnDef.setLength(cd.getLength());

				//小数桁数
				columnDef.setFraction(cd.getFraction());

				//ロック用バージョン
				columnDef.setVersion(cd.isVersion());

				//オプション
				columnDef.setOptions(cd.getOptions());

				//ColumnDefinitionのリスト追加
				ColumnDefs.add(columnDef);

			}

			//TableDefinitionモデルにColumnDefinitionのリストをセット
			tableDef.setColumnDefinitions(ColumnDefs);

			// ---------------------------------------------------------------------------
			// 各列の関連リソース定義情報をモデルにセット
			// ---------------------------------------------------------------------------
			List<RelationDefinition> rdList = td.getRelationDefinitions();

			for(RelationDefinition rd : rdList){

				RelationDefinition relationDef = new RelationDefinition();
				//子リソース
				relationDef.setName(rd.getName());

				//関連パターン
				relationDef.setRelationPattern(rd.getRelationPattern());

				//結合キー（親）と結合キー（子）
				relationDef.setJoinKeyList(rd.getJoinKeyList());

				//RelationDefinitionのリスト追加
				RelationDefs.add(relationDef);

			}

			//TableDefinitionモデルにRelationDefinitionのリストをセット
			tableDef.setRelationDefinitions(RelationDefs);

			//TableDefinitionのリスト追加
			TableDefs.add(tableDef);

		}

		//TableCategoryモデルにTableDefinitionのリストをセット
		TableCategory.setTableDefinitions(TableDefs);

		return TableCategory;

	}

	// ---------------------------------------------------------------------------
	// ExcelブックのSPA定義情報を取得する
	// ---------------------------------------------------------------------------

	/**
	 * 指定したWorkbookに含まれる画面定義シートを読み込み、ProjectDefinitionオブジェクトにマップする。
	 * @param workbook 対象のWorkbookオブジェクト
	 * @return SPA定義を格納したリスト
	 */
	private List<ProjectDefinition> buildProjectDefinitions(Workbook workbook, Map<String, ArrayListWrapper> codeDefs) {

		List<ProjectDefinition> projectDefs = new ArrayList<ProjectDefinition>();

		// シート毎に画面定義を構築する
		workbook.forEach(sheet -> {
			
			//AWAG1シート判定フラグ
			Boolean isAWAG1sheet = false;
			
			try {
				Cell topCell = getCell(sheet, this.definitionSheetMappings.get("sheetType"));
				if(topCell != null){
					if (topCell.getStringCellValue().equals("画面項目定義")) {
						projectDefs.add(buildProjectDefinition(sheet, codeDefs, isAWAG1sheet));
					}else if (topCell.getStringCellValue().equals("リソース定義")) {
						isAWAG1sheet = true;
						projectDefs.add(buildProjectDefinition(sheet, codeDefs, isAWAG1sheet));
						print("リソース定義シート数(画面定義)：" + projectDefs.size());
					}
				}
			} catch (RuntimeException e) {
				if(!isAWAG1sheet){
					throw new GenerateException("画面項目定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);
				}else{
					throw new GenerateException("リソース定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);					
				}					
			}
		});

		return projectDefs;

	}

	/**
	 * 指定したWorkbookに含まれるコード定義シートを読み込み、Mapオブジェクトにマップする。
	 * @param workbook 対象のWorkbookオブジェクト
	 * @return コード定義を格納したマップ
	 */
	private Map<String, ArrayListWrapper> buildCodeDefinition(Workbook workbook) {

		Map<String, ArrayListWrapper> displayCodeDefs = null;

		for (Sheet sheet : workbook) {
			try {
				Cell topCell = getCell(sheet, this.definitionSheetMappings.get("sheetType"));
				if(topCell != null){
					if (topCell.getStringCellValue().equals("コード定義")) {
						displayCodeDefs = buildCodeDefinition(sheet);
					}
				}
			} catch (RuntimeException e) {
				throw new GenerateException("コード定義シートの読み込みに失敗。[Book]" + sheet.getSheetName(), e);
			}
		}

		return displayCodeDefs;
	}

	/**
	 * 指定した画面項目定義シートを読み込み、ProjectDefinitionオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @param codeDefs コード定義を格納したマップ
	 * @param isAWAG1sheet AWAG1シート判定フラグ
	 * @return SPA定義を格納したProjectDefinitionオブジェクト
	 */
	private ProjectDefinition buildProjectDefinition(Sheet sheet, Map<String, ArrayListWrapper> codeDefs, Boolean isAWAG1sheet) {
		ProjectDefinition projectDef = new ProjectDefinition();

		String appName = null;
		String designType = null;
		
		if(isAWAG1sheet){
			// AWAG2用にAWAG1アプリ名を設定
			appName = "Automated web app generator";
			
		}else{
			// AWAG2の画面項目定義シートより値を取得
			appName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("appName")));
			designType = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("designType")));			
		}
				
		// アプリ名
		if (appName != null && !appName.isEmpty()) {
			projectDef.setAppName(appName);
		}

		// デザインタイプ
		if (designType != null && !designType.isEmpty()) {
			projectDef.setDesignType(DesignType.valueOf(designType));
		}

		// コード定義　
		projectDef.setDisplayCodeDefs(codeDefs);

		//ユースケース一覧　※シートから取得するユースケース数は1
		List<UsecaseDefinition> usecases = projectDef.getUsecases();
		UsecaseDefinition usecase = buildUsecaseDefinition(sheet, codeDefs, isAWAG1sheet);
		usecases.add(usecase);
		projectDef.setUsecases(usecases);

		// 画面項目定義シートの入力チェックを行う
		List<String> errorList = validate(projectDef, codeDefs);
		if (!errorList.isEmpty()) {
			throw new GenerateException("画面項目定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error]" + errorList, null);
		}

		return projectDef;

	}

	/**
	 * 指定した画面項目定義シートを読み込み、UsecaseDefinitionオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @param codeDefs コード定義を格納したマップ
	 * @param isAWAG1sheet AWAG1シート判定フラグ
	 * @return ユースケース定義を格納したUsecaseDefinitionオブジェクト
	 */
	private UsecaseDefinition buildUsecaseDefinition(Sheet sheet, Map<String, ArrayListWrapper> codeDefs, Boolean isAWAG1sheet) {
		UsecaseDefinition usecaseDef = new UsecaseDefinition();

		String usecaseDisplayName = null;
		String usecaseName = null;
		ScreenDefinition screenDef = null;

		if(isAWAG1sheet){
			// AWAG1のリソース定義シートより値を取得
			usecaseDisplayName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_screenNameLocal")));
			usecaseName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_screenName"))).toLowerCase();			
		}else{
			// AWAG2の画面項目定義シートより値を取得
			usecaseDisplayName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("usecaseDisplayName")));
			usecaseName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("usecaseName")));			
		}		
				
		// メニュー名
		usecaseDef.setUsecaseDisplayName(usecaseDisplayName);

		// メニュー名（英字）
		usecaseDef.setUsecaseName(usecaseName);

		//画面リストをセット（グループ名リスト、イベントリスト、フィールドリストを含む）※シートから取得する画面数は1
		List<ScreenDefinition> screenFlow = usecaseDef.getScreenFlow();
		
		if(isAWAG1sheet){
			
			//画面リスト（7画面）
			screenFlow = buildScreenDefinitionforAWAG1(sheet);
			
		}else{

			screenDef = buildScreenDefinition(sheet);
			screenFlow.add(screenDef);
		}
		
		usecaseDef.setScreenFlow(screenFlow);

		return usecaseDef;

	}

	/**
	 * 指定したコード定義シートを読み込み、Mapオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return コード定義を格納したマップ
	 */
	private Map<String, ArrayListWrapper> buildCodeDefinition(Sheet sheet) {

		Map<String, ArrayListWrapper> newCodeDefMap = new HashMap<String, ArrayListWrapper>();

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

			ArrayListWrapper codeDefs = Optional.ofNullable(newCodeDefMap.get(codeId)).orElse(new ArrayListWrapper());
			ArrayList<DisplayCode> list = codeDefs.getList();
			list.add(codeDef);

			newCodeDefMap.put(codeId, codeDefs);
		}

		return newCodeDefMap;
	}

	/**
	 * 指定したAWAG1のリソース定義シートを読み込み、AWAG2用にScreenDefinitionオブジェクトのリストにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return 画面定義を格納したScreenDefinitionオブジェクトのリスト
	 */
	private List<ScreenDefinition> buildScreenDefinitionforAWAG1(Sheet sheet) {

		List<ScreenDefinition> screenFrow = new ArrayList<ScreenDefinition>();
		
		//画面名
		String screenNameLocal = null;
		
		//画面名（英字）
		String screenName = null;
		
		ScreenDefinition screenDef = new ScreenDefinition();
		List<FieldGroup> fieldGroupList = new ArrayList<FieldGroup>();

		//TODO 要確認	
		String[][] screen = new String[7][2];
		
		screen[0][0] = AWAG1Constant.SEARCH_EVENT_NAME;
		screen[0][1] = AWAG1Constant.SEARCH_EVENT;
		screen[1][0] = AWAG1Constant.INPUT_EVENT_NAME;
		screen[1][1] = AWAG1Constant.INPUT_EVENT;
		screen[2][0] = AWAG1Constant.CONFIRM_EVENT_NAME;
		screen[2][1] = AWAG1Constant.CONFIRM_EVENT;
		screen[3][0] = AWAG1Constant.UPDATE_EVENT_NAME;
		screen[3][1] = AWAG1Constant.UPDATE_EVENT;		
		screen[4][0] = AWAG1Constant.UPDATECONFIRM_EVENT_NAME;
		screen[4][1] = AWAG1Constant.UPDATECONFIRM_EVENT;
		screen[5][0] = AWAG1Constant.DELETECONFIRM_EVENT_NAME;
		screen[5][1] = AWAG1Constant.DELETECONFIRM_EVENT;
		screen[6][0] = AWAG1Constant.COMPLETE_EVENT_NAME;
		screen[6][1] = AWAG1Constant.COMPLETE_EVENT;		
		
		for(int i = 0; i< screen.length; i++){
			
			screenNameLocal = screen[i][0];
			screenName = screen[i][1];
			fieldGroupList = applyAWAG2FieldGroupDefinition(screenName, sheet);
			screenDef = applyAWAG2ScreenDefinition(screenNameLocal,screenName,fieldGroupList);
			screenFrow.add(screenDef);
			
		}
						
		return screenFrow;
	}
	
	/**
	 * AWAG1シートに基づいてAWAG2用に画面定義を適用し、ScreenDefinitionオブジェクトにマップする。
	 * @param  screenNameLocal 指定した表示用画面名
	 * @param  screenName 指定した画面名
	 * @param  fieldGroupList 指定したグループ定義のリスト
	 * @return ScreenDefinition 適用したScreenDefinitionのオブジェクト
	 */
	private ScreenDefinition applyAWAG2ScreenDefinition(String screenNameLocal, String screenName, List<FieldGroup> fieldGroupList) {
		
		ScreenDefinition screenDef = new ScreenDefinition();

		/** デフォルト標準列数 */
		Integer DEFAULT_COL = 4;
		
		// 画面名
		screenDef.setScreenDisplayName(screenNameLocal);
		
		// 画面名（英字）
		screenDef.setScreenName(screenName);
		
		// 画面タイプ
		if(screenName.equals("search")){			
			screenDef.setScreenType(ScreenType.SEARCH_TABLE);
			
			// メニュー内トップ
			screenDef.setMenuTop(true);
			
		}else{			
			screenDef.setScreenType(ScreenType.STANDARD);
		}
				
		// 標準列数
		screenDef.setCol(DEFAULT_COL);

		// グループリスト（画面項目定義一覧、イベント一覧）		
		screenDef.setFieldGroupList(fieldGroupList);
		
		return screenDef;
	}	
	
	/**
	 * 指定したリソース定義シートよりグループ定義を読み込み、指定した画面に対するグループ定義のリストにマップする。
	 * @param screenName 指定した画面名（英字）
	 * @param sheet 対象のSheetオブジェクト
	 * @return グループ定義のリスト
	 */
	private List<FieldGroup> applyAWAG2FieldGroupDefinition(String screenName, Sheet sheet) {

		List<FieldGroup> fieldGroupList = new ArrayList<FieldGroup>();
		
		//グループ名
		FieldGroup fieldGroup = new FieldGroup();
		fieldGroupList.add(fieldGroup);
		
		String apiPath = "/" + getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_screenName"))).toLowerCase();
		List<String> apiParam = new ArrayList<String>();
		apiParam = new ArrayList<String>();
		
		if(screenName.equals("thanks")){			
			
			// ---------------------------------------------------------------------------
			// 完了画面
			// ---------------------------------------------------------------------------
			
			FieldDefinition fieldDef = new FieldDefinition();

			// 項目名
			fieldDef.setDisplayName("メッセージ1");
			// パラメータ名
			fieldDef.setFieldName("msg1");
			// HTML要素 - タグ種別
			fieldDef.setHtmlType(HtmlType.STATIC_TEXT);			
			// HTMLタグ
			fieldDef.setValue("★☆処理が完了しました。ありがとうございました。☆★");
			
			fieldGroup.getFieldList().add(fieldDef);
			
		}else{
			
			String firstGroupCellAddr = this.definitionSheetMappings.get("AWAG1_column_columnName");
			Cell firstColumnCell = getCell(sheet, firstGroupCellAddr);
			int numOfCode = countColumnNum(sheet, firstGroupCellAddr);
			int offset = firstColumnCell.getRowIndex();
			for (int i = 0; i < numOfCode; i++) {
	
				int targetRow = offset + i;
	
				// ---------------------------------------------------------------------------
				// 画面項目一覧を取得
				// ---------------------------------------------------------------------------
	
				if(screenName.equals("search")){
					// グループ名
					fieldGroup.setFieldGroupName("パラメーター");
				}
				
				// パラメータ名
				String fieldName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_fieldName")).getColumnIndex()));
				
				// 検索 - パラメータ
				String isSerchParam = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isSerchParam")).getColumnIndex()));
				
				// 検索画面の場合は、検索 - パラメータが"Y"の場合のみ画面項目定義をセットする
				if(!screenName.equals("search") || (screenName.equals("search") && isSerchParam != null && isSerchParam.equals("Y"))){
				
					// 画面項目定義
					FieldDefinition fieldDef = new FieldDefinition();
	
					// 項目名
					fieldDef.setDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_displayName")).getColumnIndex())));
	
					// パラメータ名					
					fieldDef.setFieldName(fieldName);
		
					// HTML要素 - タグ種別
					String htmlType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_htmlType")).getColumnIndex()));
									
					// HTML要素 - コードリスト
					fieldDef.setCodeListId(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_codeListId")).getColumnIndex())));
	
					// 入力チェック - 形式
					String formatType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_formatValidationRule")).getColumnIndex()));
					
					if(screenName.equals("search") || screenName.equals("input") || screenName.equals("update")){
	
						// ---------------------------------------------------------------------------
						// 検索画面 or 新規登録画面 or 更新画面
						// ---------------------------------------------------------------------------
						
						// HTML要素 - タグ種別
						if (htmlType != null && !htmlType.isEmpty()) {
							fieldDef.setHtmlType(HtmlType.valueOf(htmlType));
						} else {
							fieldDef.setHtmlType(HtmlType.NONE);
						}
											
						// 入力チェック - 必須
						Boolean isRequired = true;													
						String isAllowedNull = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isAllowedNull")).getColumnIndex()));
						
						if(screenName.equals("search")) {
							//検索画面は必須項目なし
							isRequired = false;
							
						}else{
							if (isAllowedNull != null && isAllowedNull.equals("Y")) {
								isRequired = false;
							}
						}
									
						fieldDef.setRequired(isRequired);
						
						FormatValidationDefinition fmtValidationDef = new FormatValidationDefinition();
		
						// 入力チェック - 形式
						if (formatType != null && !formatType.isEmpty()) {
							fmtValidationDef.setFormatType(FormatType.valueOf(formatType));
						}
		
						// 入力チェック - 最大桁数
						Double maxLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_maxLength")).getColumnIndex()));
						// 入力チェック - 最小桁数
						Double minLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_minLength")).getColumnIndex()));
						// 入力チェック - 一致桁数
						Double length = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_length")).getColumnIndex()));				
						
						if (maxLength != null) {
							if(fmtValidationDef.getFormatType() == FormatType.DECIMAL || fmtValidationDef.getFormatType() == FormatType.FLOAT){
		
								if(minLength != null){
		
									// 整数部桁数
									fmtValidationDef.setInteger(Integer.valueOf(maxLength.intValue() - minLength.intValue()));
		
									// 小数部桁数
									fmtValidationDef.setFraction(Integer.valueOf(minLength.intValue()));
		
								}else{
		
									// 整数部桁数
									fmtValidationDef.setInteger(Integer.valueOf(maxLength.intValue()));
		
								}
		
							}else{
								// 最大桁数
								fmtValidationDef.setMaxLength(Integer.valueOf(maxLength.intValue()));
		
								if(minLength != null){
									// 最小桁数
									fmtValidationDef.setMinLength(Integer.valueOf(minLength.intValue()));
									// 一致桁数
									if(maxLength.equals(minLength)){
										fmtValidationDef.setLength(Integer.valueOf(maxLength.intValue()));
									}
								}
							}
							
						}else{
							
							if(minLength == null && length != null){
								// 最大桁数
								fmtValidationDef.setMaxLength(Integer.valueOf(length.intValue()));
								// 最小桁数
								fmtValidationDef.setMinLength(Integer.valueOf(length.intValue()));
								// 一致桁数
								fmtValidationDef.setLength(Integer.valueOf(length.intValue()));
							}
							
						}				
		
						fieldDef.setFormatValidationRule(fmtValidationDef);
					
					}else{
						
						// ---------------------------------------------------------------------------
						// 登録確認画面 or 更新確認画面 or 削除確認画面
						// ---------------------------------------------------------------------------
										
						if (htmlType != null && !htmlType.isEmpty()) {
							
							// RADIOとSESLECTとCHECKBOX以外は「HTML要素 - タグ種別」にOUTPUTを設定
							if(HtmlType.valueOf(htmlType) == HtmlType.CHECKBOX || HtmlType.valueOf(htmlType) == HtmlType.SELECT || HtmlType.valueOf(htmlType) == HtmlType.RADIO){
								
								// HTML要素 - タグ種別
								fieldDef.setHtmlType(HtmlType.valueOf(htmlType));
								
								// HTML要素 - readonly
								fieldDef.setReadonly(true);
								
							}else{					
								fieldDef.setHtmlType(HtmlType.OUTPUT);
							}
												
						} else {
				
							fieldDef.setHtmlType(HtmlType.NONE);
							
						}
						
					}	
					
					fieldGroup.getFieldList().add(fieldDef);
				}	
				
				// APIのパラメータ判定
				String isPK = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isPk")).getColumnIndex()));
				
				if (isPK != null && isPK.equals("Y")) {
					apiParam.add(fieldName);
				}
				
			}
			
			if(screenName.equals("search")){
				
				// ---------------------------------------------------------------------------
				// 検索画面
				// ---------------------------------------------------------------------------
				
				// ---------------------------------------------------------------------------
				// 検索ボタン
				// ---------------------------------------------------------------------------

				FieldDefinition searchField = new FieldDefinition();
				EventDefinition searchEvent = new EventDefinition();
				
				searchField.setDisplayName("検索");
				searchField.setFieldName("awagSearch");
				searchField.setHtmlType(HtmlType.BUTTON);
				searchEvent.setEventDisplayName("検索");
				searchEvent.setEventName("awagSearch");
				searchEvent.setEventFireType(EventFireType.FORMVALID);
				searchEvent.setApiType(HTTPMethodType.GET);						
				searchEvent.setApiPath(apiPath + "/" + screenName);
				
				fieldGroup.getFieldList().add(searchField);
				fieldGroup.getEventList().add(searchEvent);
				
				fieldGroup = new FieldGroup();
				fieldGroupList.add(fieldGroup);				
				
				for (int i = 0; i < numOfCode; i++) {
					
					int targetRow = offset + i;
					
					// 検索 - パラメータ
					String isSerchParam = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isSerchParam")).getColumnIndex()));
					
					// 検索 - パラメータが"Y"の場合のみ画面項目定義をセットする
					if((screenName.equals("search") && isSerchParam != null && isSerchParam.equals("Y"))){
				
						// グループ名
						fieldGroup.setFieldGroupName("表");
			
						// 画面項目定義
						FieldDefinition fieldDef = new FieldDefinition();
						
						Boolean isList = true;
	
						// 第２階層フラグ
						fieldGroup.setList(isList);
	
						// 項目名
						fieldGroup.setListDisplayName("検索結果");
	
						// パラメータ名
						fieldGroup.setListName("list");					
	
						// 繰り返し項目 - 項目名
						fieldDef.setDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_displayName")).getColumnIndex())));
	
						// 繰り返し項目 - パラメータ名
						fieldDef.setFieldName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_fieldName")).getColumnIndex())));
						
						// HTML要素 - タグ種別
						String htmlType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_htmlType")).getColumnIndex()));
						if(htmlType != null){
							fieldDef.setHtmlType(HtmlType.OUTPUT);
						}else{
							fieldDef.setHtmlType(HtmlType.NONE);
						}					
						
						fieldGroup.getFieldList().add(fieldDef);
					
					}
					
				}
				
				// ---------------------------------------------------------------------------
				// 更新ボタン
				// ---------------------------------------------------------------------------
				
				FieldDefinition updateField = new FieldDefinition();
				EventDefinition updateEvent = new EventDefinition();				
				
				updateField.setDisplayName("更新");
				updateField.setFieldName("update");
				updateField.setHtmlType(HtmlType.BUTTON);
				updateEvent.setEventDisplayName("更新");
				updateEvent.setEventName("update");
				updateEvent.setNextDisplayScreen("更新");
				updateEvent.setNextScreen("update");			
				updateEvent.setEventFireType(EventFireType.TABLE_SELECTED);
				
				fieldGroup.getFieldList().add(updateField);
				fieldGroup.getEventList().add(updateEvent);				
				
				// ---------------------------------------------------------------------------
				// 削除ボタン
				// ---------------------------------------------------------------------------

				FieldDefinition deleteField = new FieldDefinition();
				EventDefinition deleteEvent = new EventDefinition();
				
				deleteField.setDisplayName("削除");
				deleteField.setFieldName("delete");
				deleteField.setHtmlType(HtmlType.BUTTON);
				deleteEvent.setEventDisplayName("削除");
				deleteEvent.setEventName("delete");				
				deleteEvent.setNextDisplayScreen("削除確認");
				deleteEvent.setNextScreen("deleteconfirm");				
				deleteEvent.setEventFireType(EventFireType.TABLE_SELECTED);
				
				fieldGroup.getFieldList().add(deleteField);
				fieldGroup.getEventList().add(deleteEvent);					
				
				// ---------------------------------------------------------------------------
				// 追加ボタン
				// ---------------------------------------------------------------------------

				FieldDefinition createField = new FieldDefinition();
				EventDefinition createEvent = new EventDefinition();
				
				createField.setDisplayName("追加");
				createField.setFieldName("awagGoblank");
				createField.setHtmlType(HtmlType.BUTTON);
				createEvent.setEventDisplayName("追加");
				createEvent.setEventName("awagGoblank");
				createEvent.setNextDisplayScreen("登録");
				createEvent.setNextScreen("input");				
				createEvent.setEventFireType(EventFireType.ANYTIME);
				
				fieldGroup.getFieldList().add(createField);
				fieldGroup.getEventList().add(createEvent);
				
				// ---------------------------------------------------------------------------
				// 1行選択
				// ---------------------------------------------------------------------------

				FieldDefinition selectField = new FieldDefinition();
				EventDefinition selectEvent = new EventDefinition();
				
				selectField.setDisplayName("選択");
				selectField.setFieldName("awagSelect");
				selectField.setHtmlType(HtmlType.NONE);
				selectEvent.setEventDisplayName("選択");
				selectEvent.setEventName("awagSelect");
				selectEvent.setApiType(HTTPMethodType.GET);

				String apiPathParam = null;
				
				//主キーが複数存在する場合はパラメータを.で繋ぐ
				for(int i= 0; i< apiParam.size(); i++){
					if(i == 0){
						apiPathParam = "{" + apiParam.get(i) +  "}";
					}else{
						apiPathParam += ".{" + apiParam.get(i) + "}";
					}
					
				}
				
				selectEvent.setApiPath(apiPath + "/" + apiPathParam);
				
				fieldGroup.getFieldList().add(selectField);
				fieldGroup.getEventList().add(selectEvent);				
					
			}else{	
				// 検索画面以外
				
				// ---------------------------------------------------------------------------
				// Submitボタン
				// ---------------------------------------------------------------------------
				
				FieldDefinition submitField = new FieldDefinition();
				EventDefinition submitEvent = new EventDefinition();
								
				if(screenName.equals("input")){
					
					// ---------------------------------------------------------------------------
					// 新規登録画面
					// ---------------------------------------------------------------------------				
					submitField.setDisplayName("確認へ");
					submitField.setFieldName("confirm");
					submitEvent.setEventDisplayName("確認へ");
					submitEvent.setEventName("confirm");
					submitEvent.setNextDisplayScreen("登録確認");
					submitEvent.setNextScreen("confirm");				
					submitEvent.setEventFireType(EventFireType.FORMVALID);
					
				}else if(screenName.equals("confirm")){
					
					// ---------------------------------------------------------------------------
					// 登録確認画面
					// ---------------------------------------------------------------------------				
					submitField.setDisplayName("登録");
					submitField.setFieldName("confirm");
					submitEvent.setEventDisplayName("登録");
					submitEvent.setEventName("confirm");
					submitEvent.setNextDisplayScreen("完了");
					submitEvent.setNextScreen("thanks");
					submitEvent.setEventFireType(EventFireType.FORMVALID);
					submitEvent.setApiType(HTTPMethodType.POST);
					submitEvent.setApiPath(apiPath);
					
				}else if(screenName.equals("update")){
					
					// ---------------------------------------------------------------------------
					// 更新画面
					// ---------------------------------------------------------------------------
					submitField.setDisplayName("確認へ");
					submitField.setFieldName("confirm");
					submitEvent.setEventDisplayName("確認へ");
					submitEvent.setEventName("confirm");
					submitEvent.setNextDisplayScreen("更新確認");
					submitEvent.setNextScreen("updateconfirm");				
					submitEvent.setEventFireType(EventFireType.FORMVALID);
					
				}else if(screenName.equals("updateconfirm")){
					
					// ---------------------------------------------------------------------------
					// 更新確認画面
					// ---------------------------------------------------------------------------
					submitField.setDisplayName("更新");
					submitField.setFieldName("update");	
					submitEvent.setEventDisplayName("更新");
					submitEvent.setEventName("update");	
					submitEvent.setNextDisplayScreen("完了");
					submitEvent.setNextScreen("thanks");
					submitEvent.setEventFireType(EventFireType.FORMVALID);
					submitEvent.setApiType(HTTPMethodType.PUT);
					submitEvent.setApiPath(apiPath);
					
				}else if(screenName.equals("deleteconfirm")){
					
					// ---------------------------------------------------------------------------
					// 削除確認画面
					// ---------------------------------------------------------------------------				
					submitField.setDisplayName("削除");
					submitField.setFieldName("delete");	
					submitEvent.setEventDisplayName("削除");
					submitEvent.setEventName("delete");
					submitEvent.setNextDisplayScreen("完了");
					submitEvent.setNextScreen("thanks");
					submitEvent.setApiType(HTTPMethodType.DELETE);
					submitEvent.setApiPath(apiPath);
				}
				
				submitField.setHtmlType(HtmlType.BUTTON);			
				
				fieldGroup.getFieldList().add(submitField);
				fieldGroup.getEventList().add(submitEvent);
	
				// ---------------------------------------------------------------------------
				// Backボタン
				// ---------------------------------------------------------------------------
				
				FieldDefinition backField = new FieldDefinition();
				EventDefinition backEvent = new EventDefinition();			
				
				if(screenName.equals("input") || screenName.equals("update") || screenName.equals("deleteconfirm")){
					
					// ---------------------------------------------------------------------------
					// 新規登録画面 or 更新画面 or 削除確認画面
					// ---------------------------------------------------------------------------				
					backField.setDisplayName("検索へ戻る");
					backEvent.setEventDisplayName("検索へ戻る");
					backEvent.setNextDisplayScreen("検索");
					backEvent.setNextScreen("search");
					
				}else{
					
					backField.setDisplayName("入力へ戻る");
					backEvent.setEventDisplayName("入力へ戻る");
					
					 if(screenName.equals("confirm")){				
						// ---------------------------------------------------------------------------
						// 登録確認画面
						// ---------------------------------------------------------------------------
						backEvent.setNextDisplayScreen("登録");
						backEvent.setNextScreen("input");
						
					 }else if (screenName.equals("updateconfirm")){
						// ---------------------------------------------------------------------------
						// 更新確認画面
						// ---------------------------------------------------------------------------
						backEvent.setNextDisplayScreen("更新");
						backEvent.setNextScreen("update");
						 
					 }
					 
				}		
							
				backField.setFieldName("awagGoback");
				backField.setHtmlType(HtmlType.BUTTON);			
				backEvent.setEventName("awagGoback");
				backEvent.setEventFireType(EventFireType.ANYTIME);
							
				fieldGroup.getFieldList().add(backField);
				fieldGroup.getEventList().add(backEvent);			
				
			}
			
		}	

		return fieldGroupList;

	}	
	
	/**
	 * 指定した画面項目定義シートを読み込み、ScreenDefinitionオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return SPA定義を格納したScreenDefinitionのオブジェクト
	 */
	private ScreenDefinition buildScreenDefinition(Sheet sheet) {

		ScreenDefinition screenDef = new ScreenDefinition();

		// 画面名
		screenDef.setScreenDisplayName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("screenNameLocal"))));

		// 画面名（英字）
		screenDef.setScreenName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("screenName"))));

		// 画面タイプ
		String screenType = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("screenType")));
		if (screenType != null && !screenType.isEmpty()) {
			screenDef.setScreenType(ScreenType.valueOf(screenType));
		}

		// 標準列数
		Double col = getNumberValue(getCell(sheet, this.definitionSheetMappings.get("col")));
		if (col != null) {
			screenDef.setCol(Integer.valueOf(col.intValue()));
		}

		// メニュー内トップ
		String isMenuTop = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("isMenuTop")));
		if (isMenuTop != null && isMenuTop.equals("Y")) {
			screenDef.setMenuTop(true);
		}

		// 画面オプション
		try {
			screenDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("screen_options")))));
		} catch(Exception e) {
			throw new GenerateException("画面項目定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] 画面オプションの書式が不正です。", e);
		}

		// グループリスト（画面項目定義一覧、イベント一覧）
		List<FieldGroup> fieldGroupList = buildFieldGroupDefinition(sheet);
		screenDef.setFieldGroupList(fieldGroupList);

		return screenDef;
	}

	/**
	 * 指定した画面項目定義シートよりグループ定義を読み込み、Mapオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return グループ定義のリスト
	 */
	private List<FieldGroup> buildFieldGroupDefinition(Sheet sheet) {

		List<FieldGroup> fieldGroupList = new ArrayList<FieldGroup>();

		//グループ名
		FieldGroup fieldGroup = new FieldGroup();
		fieldGroupList.add(fieldGroup);

		String firstGroupCellAddr = this.definitionSheetMappings.get("column_DisplayName");
		Cell firstColumnCell = getCell(sheet, firstGroupCellAddr);
		int numOfCode = countColumnNum(sheet, firstGroupCellAddr);
		int offset = firstColumnCell.getRowIndex();
		for (int i = 0; i < numOfCode; i++) {

			int targetRow = offset + i;

			// ---------------------------------------------------------------------------
			// 画面項目一覧を取得
			// ---------------------------------------------------------------------------

			Cell cell = getCell(sheet, this.definitionSheetMappings.get("column_groupName"));
			//取得対象のグループ名
			String fgCell = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
			if(fgCell == null){
				fgCell = "";
			}
			//1つ前のグループ名
			String fgCellbefore = getEvaluatedCellValue(sheet.getRow(targetRow-1).getCell(cell.getColumnIndex()));
			if(fgCellbefore == null){
				fgCellbefore = "";
			}

			if(!fgCell.equals(fgCellbefore) && i!=0 ){//初回のみ追加済みなのでスキップ
				// 1つ前のグループ名が違う場合、FieldGroupのインスタンスを新規作成
				fieldGroup = new FieldGroup();
				fieldGroupList.add(fieldGroup);
//				System.out.println(targetRow+1 + "行目でfieldGroupを初期化" );

			}

			// グループ名
			fieldGroup.setFieldGroupName(fgCell);

			// 標準列数
			Double col = getNumberValue(getCell(sheet, this.definitionSheetMappings.get("col")));
			if (col != null) {
				fieldGroup.setCol(Integer.valueOf(col.intValue()));
			}

			FieldDefinition fieldDef = new FieldDefinition();
			Boolean isList = false;

			String isSecond = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_eventDisplayName")).getColumnIndex()));

			if (isSecond != null && !isSecond.isEmpty()) {

				isList = true;

				// 第２階層フラグ
				fieldGroup.setList(isList);

				// 項目名
				fieldGroup.setListDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_DisplayName")).getColumnIndex())));

				// パラメータ名
				fieldGroup.setListName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName")).getColumnIndex())));

				// 繰り返し項目 - 項目名
				fieldDef.setDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_eventDisplayName")).getColumnIndex())));

				// 繰り返し項目 - パラメータ名
				fieldDef.setFieldName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_eventName")).getColumnIndex())));

			}else{

				// 第２階層フラグ
				fieldGroup.setList(isList);

				// 項目名
				fieldDef.setDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_DisplayName")).getColumnIndex())));

				// パラメータ名
				fieldDef.setFieldName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName")).getColumnIndex())));

			}

			// HTML要素 - タグ種別
			String htmlType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_htmlType")).getColumnIndex()));
			if (htmlType != null && !htmlType.isEmpty()) {
				fieldDef.setHtmlType(HtmlType.valueOf(htmlType));
			} else {
				fieldDef.setHtmlType(HtmlType.NONE);
			}

			// HTML要素 - readonly
			String readOnly = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_isReadonly")).getColumnIndex()));
			Boolean isReadonly = false;
			if (readOnly != null && readOnly.equals("Y")) {
				isReadonly = true;
			}
			fieldDef.setReadonly(isReadonly);

			// HTML要素 - コードリスト
			fieldDef.setCodeListId(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_codeListId")).getColumnIndex())));

			// ---------------------------------------------------------------------------
			// 入力チェック仕様を取得
			// ---------------------------------------------------------------------------

			FormatValidationDefinition fmtValidationDef = new FormatValidationDefinition();

			// 入力チェック - 必須
			String required = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_isRequired")).getColumnIndex()));
			Boolean isRequired = false;
			if (required != null && required.equals("Y")) {
				isRequired = true;
			}
			fieldDef.setRequired(isRequired);

			// 入力チェック - 形式
			String formatType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_formatType")).getColumnIndex()));
			if (formatType != null && !formatType.isEmpty()) {
				fmtValidationDef.setFormatType(FormatType.valueOf(formatType));
			}

			// 入力チェック - 最大桁数
			Double maxLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_maxLength")).getColumnIndex()));
			// 入力チェック - 最小桁数
			Double minLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_minLength")).getColumnIndex()));

			if (maxLength != null) {
				if(fmtValidationDef.getFormatType() == FormatType.DECIMAL || fmtValidationDef.getFormatType() == FormatType.FLOAT){

					if(minLength != null){

						// 整数部桁数
						fmtValidationDef.setInteger(Integer.valueOf(maxLength.intValue() - minLength.intValue()));

						// 小数部桁数
						fmtValidationDef.setFraction(Integer.valueOf(minLength.intValue()));

					}else{

						// 整数部桁数
						fmtValidationDef.setInteger(Integer.valueOf(maxLength.intValue()));
						
						// 小数部桁数
						fmtValidationDef.setFraction(0);

					}

				}else{
					// 最大桁数
					fmtValidationDef.setMaxLength(Integer.valueOf(maxLength.intValue()));

					if(minLength != null){
						// 最小桁数
						fmtValidationDef.setMinLength(Integer.valueOf(minLength.intValue()));
						// 一致桁数
						if(maxLength.equals(minLength)){
							fmtValidationDef.setLength(Integer.valueOf(maxLength.intValue()));
						}
					}
				}
			}

			fieldDef.setFormatValidationRule(fmtValidationDef);

			// HTMLタグ
			fieldDef.setValue(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_value")).getColumnIndex())));

			//オプション
			try {
				fieldDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_screen_options")).getColumnIndex()))));
			} catch(Exception e) {
				throw new GenerateException("画面項目定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
			}

			fieldGroup.getFieldList().add(fieldDef);

			// ---------------------------------------------------------------------------
			// イベント一覧を取得
			// ---------------------------------------------------------------------------

			Cell cell3 = sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_isEvent")).getColumnIndex());
			String isEvent = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell3.getColumnIndex()));
			if (isEvent != null && isEvent.equals("Y")) {

				EventDefinition eventDef = new EventDefinition();

				// 第２階層の有無で分岐
				if(!isList){

					// 項目名
					eventDef.setEventDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_DisplayName")).getColumnIndex())));

					// パラメータ名
					eventDef.setEventName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName")).getColumnIndex())));

				}else{

					// 繰り返し項目 - 項目名
					eventDef.setEventDisplayName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_eventDisplayName")).getColumnIndex())));

					// 繰り返し項目 - パラメータ名
					eventDef.setEventName(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_eventName")).getColumnIndex())));

				}

				// 遷移先画面名
				eventDef.setNextDisplayScreen(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_nextScreenDisplayName")).getColumnIndex())));

				// 遷移先画面物理名
				eventDef.setNextScreen(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_nextScreen")).getColumnIndex())));

				// イベント発火条件
				String eventFireType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_eventFireType")).getColumnIndex()));
				if (eventFireType != null && !eventFireType.isEmpty()) {
					eventDef.setEventFireType(EventFireType.valueOf(eventFireType));
				}

				// API種別
				String apiType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_apiType")).getColumnIndex()));
				if (apiType != null && !apiType.isEmpty()) {
					eventDef.setApiType(HTTPMethodType.valueOf(apiType));
				}
				// API Path
				eventDef.setApiPath(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_apiPath")).getColumnIndex())));

				fieldGroup.getEventList().add(eventDef);

			}

		}

		return fieldGroupList;

	}

	/**
	 * 画面項目定義シートの入力チェックを行う。
	 * @param projectDef 画面項目定義シートの値を読み込んだProjectDefinitionオブジェクト
	 * @param codeDefs コード定義シートの値を読み込んだマップオブジェクト
	 * @return エラーリスト
	 */
	private List<String> validate(ProjectDefinition projectDef, Map<String, ArrayListWrapper> codeDefs) {

		List<String> errorList = new ArrayList<String>();
		FormatValidator validator = new FormatValidator();

		for(UsecaseDefinition usecaseDef: projectDef.getUsecases()){

			// ユースケース定義 - 単項目チェック
			List<String> usecaseErrorList = validator.isValid(usecaseDef);

			if(!usecaseErrorList.isEmpty()) {
				errorList.add("ユースケース定義入力チェックエラー。[Usecase]" + usecaseDef.getUsecaseName() + usecaseErrorList);
			} else {

				for(ScreenDefinition screenDef: usecaseDef.getScreenFlow()){

					// 画面定義 - 単項目チェック
					List<String> screenErrorList = validator.isValid(screenDef);

					if(!screenErrorList.isEmpty()) {
						errorList.add("画面定義入力チェックエラー。[Screen]" + screenDef.getScreenName() + screenErrorList);
					} else {

						for (FieldGroup fieldGroup : screenDef.getFieldGroupList()) {

							// 画面グループ - 単項目チェック
							List<String> fieldGroupErrorList = validator.isValid(fieldGroup);

							if(!fieldGroupErrorList.isEmpty()) {
								errorList.add("画面グループ入力チェックエラー。[Group]" + fieldGroup.getFieldGroupName() + fieldGroupErrorList);
							} else {

								for (FieldDefinition fieldDef : fieldGroup.getFieldList()) {

									// 画面項目定義（入力チェック以外） - 単項目チェック
									List<String> fieldDefErrorList = validator.isValid(fieldDef);

									if(!fieldDefErrorList.isEmpty()) {
										errorList.add("画面項目定義入力チェックエラー。[Field]" + fieldDef.getFieldName() + fieldDefErrorList);
									} else {

										// 画面項目定義 - 相関チェック
										if ((fieldDef.getHtmlType().equals(HtmlType.RADIO) || fieldDef.getHtmlType().equals(HtmlType.SELECT)) && (fieldDef.getCodeListId() == null || fieldDef.getCodeListId().isEmpty())) {
											errorList.add("コードリストが未入力。[Field]" + fieldDef.getFieldName());
										}

										if (fieldDef.getCodeListId() != null && !(fieldDef.getCodeListId().isEmpty()) && !codeDefs.containsKey(fieldDef.getCodeListId())) {
											errorList.add("対応するコードリストがコード定義シートにありません。[Field]" + fieldDef.getFieldName());
										}

									}

									// 画面項目定義（入力チェック） - 単項目チェック
									if(fieldDef.getFormatValidationRule() != null){
										List<String> formatDefErrorList = validator.isValid(fieldDef.getFormatValidationRule());

										if(!formatDefErrorList.isEmpty()) {
											errorList.add("画面項目定義入力チェックエラー。[Field]" + fieldDef.getFieldName() + fieldDefErrorList);
										}
									
									}

								}

								for (EventDefinition eventDef : fieldGroup.getEventList()) {

									// イベント定義 - 単項目チェック
									List<String> eventDefErrorList = validator.isValid(eventDef);

									if(!eventDefErrorList.isEmpty()) {
										errorList.add("イベント定義入力チェックエラー。[Event]" + eventDef.getEventName() + eventDefErrorList);
									}

								}

							}

						}

					}

				}

			}

		}

		return errorList;
	}

	// ---------------------------------------------------------------------------
	// ExcelブックのAPI定義情報を取得する
	// ---------------------------------------------------------------------------

	/**
	 * 指定したWorkbookに含まれるAPI定義シートを読み込み、BusinessCategoryオブジェクトにマップする。
	 * @param workbook 対象のWorkbookオブジェクト
	 * @return API定義を格納したBusinessCategoryオブジェクト
	 */

	private List<APIDefinition> buildAPIDefinition(Workbook workbook) {

		List<APIDefinition> apiDefs = new ArrayList<APIDefinition>();

		// シート毎にAPI定義を構築する
		workbook.forEach(sheet -> {
			
			//AWAG1シート判定フラグ
			Boolean isAWAG1sheet = false;
			
			try {
				Cell topCell = getCell(sheet, this.definitionSheetMappings.get("sheetType"));
				if(topCell != null){
					if (topCell.getStringCellValue().equals("API定義")) {
						apiDefs.add(buildAPIDefinition(sheet));
					}else if (topCell.getStringCellValue().equals("リソース定義")) {
						isAWAG1sheet = true;
						apiDefs.addAll(buildAPIDefinitionforAWAG1(sheet));
						print("リソース定義シート数(API定義)：" + apiDefs.size());
					}
				}
			} catch (RuntimeException e) {
				
				if(!isAWAG1sheet){
					throw new GenerateException("API定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);
				}else{
					throw new GenerateException("リソース定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);					
				}
				
			}
		});

		return apiDefs;
	}
	
	/**
	 * 指定したAWAG1のリソース定義シートを読み込み、AWAG2用にAPIDefinitionオブジェクトのリストにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return 画面定義を格納したAPIDefinitionオブジェクトのリスト
	 */
	private List<APIDefinition> buildAPIDefinitionforAWAG1(Sheet sheet) {

		List<APIDefinition> apiList = new ArrayList<>();
		
		// API名（表示用）の一部
		String partOfApiNameLocal = null;
		
		// API名（英字）の一部
		String partOfApiName = null;

		//TODO 要確認	
		String[][] api = new String[5][2];
		
		api[0][0] = AWAG1Constant.SEARCH_EVENT_NAME;
		api[0][1] = AWAG1Constant.SEARCH_API;
		api[1][0] = AWAG1Constant.GET_EVENT_NAME;
		api[1][1] = AWAG1Constant.GET_API;
		api[2][0] = AWAG1Constant.INPUT_EVENT_NAME;
		api[2][1] = AWAG1Constant.CREATE_API;
		api[3][0] = AWAG1Constant.UPDATE_EVENT_NAME;
		api[3][1] = AWAG1Constant.UPDATE_API;		
		api[4][0] = AWAG1Constant.DELETE_EVENT_NAME;
		api[4][1] = AWAG1Constant.DELETE_API;		
		
		for(int i = 0; i< api.length; i++){
			
			partOfApiNameLocal = api[i][0];
			partOfApiName = api[i][1];
			APIDefinition apiDef = applyAWAG2APIDefinition(partOfApiNameLocal, partOfApiName, sheet);	
			apiList.add(apiDef);
		
		}
						
		return apiList;
	}

	/**
	 * AWAG1シートに基づいてAWAG2用にAPI定義を適用し、APIDefinitionオブジェクトにマップする。
	 * @param  partOfApiNameLocal 指定した表示用API名の一部
	 * @param  partOfApiName 指定したAPI名の一部
	 * @return APIDefinition 適用したAPIDefinitionのオブジェクト
	 */
	private APIDefinition applyAWAG2APIDefinition(String partOfApiNameLocal, String partOfApiName, Sheet sheet) {
		
		APIDefinition apiDef = new APIDefinition();
		
		String apiName = null;
		
		String apiNameLocal = null;
		
		// パッケージ名
		String javaPackage = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_packageName")));
		
		// PATH		
		String apiPath = null;
		
		// METHOD
		HTTPMethodType apiMethod = null;
		
		// API種別
		APIType apiType = null;
		
		// 説明
		String description = null;	
		
//		// APIオプション
//		String apiOptions = null;
		
		List<InputItemDefinition> inputItemDefinitions = null;
		List<OutputItemDefinition> outputItemDefinitions = null;
				
		apiName = partOfApiName + getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_screenName")));
		apiNameLocal = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_screenNameLocal"))) + partOfApiNameLocal;
		apiPath = "/" + getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_screenName"))).toLowerCase() ;		

		List<String> apiParam = new ArrayList<String>();
		apiParam = new ArrayList<String>();		
		String firstGroupCellAddr = this.definitionSheetMappings.get("AWAG1_column_columnName");
		Cell firstColumnCell = getCell(sheet, firstGroupCellAddr);
		int numOfCode = countColumnNum(sheet, firstGroupCellAddr);
		int offset = firstColumnCell.getRowIndex();
		for (int i = 0; i < numOfCode; i++) {

			int targetRow = offset + i;
			
			// APIのパラメータ判定
			String isPK = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isPk")).getColumnIndex()));
			String fieldName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_fieldName")).getColumnIndex()));
			
			if (isPK != null && isPK.equals("Y")) {
				apiParam.add(fieldName);
			}
			
		}		
		
		if(partOfApiNameLocal.equals(AWAG1Constant.SEARCH_EVENT_NAME)){
			
			//検索API
			apiPath = apiPath + "/" + AWAG1Constant.SEARCH_EVENT;
			apiMethod = HTTPMethodType.GET;
			apiType = APIType.MultiRead;
			inputItemDefinitions = applyAWAG2InputItemDefinition(AWAG1Constant.SEARCH_EVENT_NAME, sheet);
			outputItemDefinitions = applyAWAG2OutputItemDefinition(AWAG1Constant.SEARCH_EVENT_NAME, sheet);			
			
		}else if(partOfApiNameLocal.equals(AWAG1Constant.GET_EVENT_NAME)){

			// 取得API			
			String apiPathParam = null;
			
			// 主キーが複数存在する場合はパラメータを.で繋ぐ
			for(int i= 0; i< apiParam.size(); i++){
				if(i == 0){
					apiPathParam = "{" + apiParam.get(i) +  "}";
				}else{
					apiPathParam += ".{" + apiParam.get(i) + "}";
				}				
			}
			
			apiPath = apiPath + "/" + apiPathParam;
			apiMethod = HTTPMethodType.GET;
			apiType = APIType.SingleRead;
			inputItemDefinitions = applyAWAG2InputItemDefinition(AWAG1Constant.GET_EVENT_NAME, sheet);
			outputItemDefinitions = applyAWAG2OutputItemDefinition(AWAG1Constant.GET_EVENT_NAME, sheet);
			
		}else if(partOfApiNameLocal.equals(AWAG1Constant.INPUT_EVENT_NAME)){
			
			//登録API
			apiMethod = HTTPMethodType.POST;
			apiType = APIType.SingleCreate;
			inputItemDefinitions = applyAWAG2InputItemDefinition(AWAG1Constant.INPUT_EVENT_NAME, sheet);
			outputItemDefinitions = applyAWAG2OutputItemDefinition(AWAG1Constant.INPUT_EVENT_NAME, sheet);			
			
		}else if(partOfApiNameLocal.equals(AWAG1Constant.UPDATE_EVENT_NAME)){
			
			//更新API
			apiMethod = HTTPMethodType.PUT;
			apiType = APIType.SingleUpdate;
			inputItemDefinitions = applyAWAG2InputItemDefinition(AWAG1Constant.UPDATE_EVENT_NAME, sheet);
			outputItemDefinitions = applyAWAG2OutputItemDefinition(AWAG1Constant.UPDATE_EVENT_NAME, sheet);
			
		}else{
			//削除API
			apiMethod = HTTPMethodType.DELETE;
			apiType = APIType.SingleDelete;
			inputItemDefinitions = applyAWAG2InputItemDefinition(AWAG1Constant.DELETE_EVENT_NAME, sheet);
			outputItemDefinitions = applyAWAG2OutputItemDefinition(AWAG1Constant.DELETE_EVENT_NAME, sheet);
			
		}	
		
		description = apiNameLocal;
		
		apiDef.setName(apiName);
		apiDef.setNameLocal(apiNameLocal);
		apiDef.setJavaPackage(javaPackage);
		apiDef.setPath(apiPath);
		apiDef.setMethod(apiMethod);
		apiDef.setApiType(apiType);
		apiDef.setDescription(description);		
		apiDef.setInputItemDefinitions(inputItemDefinitions);
		apiDef.setOutputItemDefinitions(outputItemDefinitions);
		
		return apiDef;
	}	
	
	/**
	 * 指定したリソース定義シートよりAPI項目定義を読み込み、指定したAPIに対するAPI入力項目のリストにマップする。
	 * @param apiNameLocal 指定したAPI名
	 * @param sheet 対象のSheetオブジェクト
	 * @return API入力項目定義のリスト
	 */
	private List<InputItemDefinition> applyAWAG2InputItemDefinition(String apiNameLocal, Sheet sheet) {

		List<InputItemDefinition> inputItemList = new ArrayList<InputItemDefinition>();
		
		String entityName1 = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_tableName")));
			
		String firstGroupCellAddr = this.definitionSheetMappings.get("AWAG1_column_columnName");
		Cell firstColumnCell = getCell(sheet, firstGroupCellAddr);
		int numOfCode = countColumnNum(sheet, firstGroupCellAddr);
		int offset = firstColumnCell.getRowIndex();
		for (int i = 0; i < numOfCode; i++) {

			int targetRow = offset + i;
			
			// API項目定義
			InputItemDefinition inputItemDef = new InputItemDefinition();

			// PK
			String isPK = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isPk")).getColumnIndex()));

			// 検索 - パラメータ
			String isSerchParam = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isSerchParam")).getColumnIndex()));

			// ロック用バージョン
			String isVersion = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isVersion")).getColumnIndex()));
			
			// API入力項目設定フラグ
			Boolean isSet = false;
			
			if(apiNameLocal.equals(AWAG1Constant.SEARCH_EVENT_NAME)){
				// 検索APIで検索-パラメータがYの場合
				if (isSerchParam != null && isSerchParam.equals("Y")) {
					isSet = true;
				}
				
			}else if (apiNameLocal.equals(AWAG1Constant.GET_EVENT_NAME)){
				//取得APIで主キー値の場合
				if(isPK != null && isPK.equals("Y")){
					isSet = true;
				}
				
			}else{
				// 登録API or 更新API or 削除APIの場合
				isSet = true;
			}
						
			if(isSet){
				
				// 階層1-データ項目名（表示用）
				String itemNameLocal = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_displayName")).getColumnIndex()));
				inputItemDef.setNameLocal(itemNameLocal);
	
				// 階層1-データ項目名（英字）
				String itemName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_fieldName")).getColumnIndex()));
				inputItemDef.setName(itemName);
	
				// 第1階層 - テーブル名
				if(entityName1 != null){
					inputItemDef.setMappedEntityName(entityName1);
				}
	
				// 第1階層 - カラム名 or 第2階層 - テーブル名
				String field1orEntity2 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_columnName")).getColumnIndex()));
				if(field1orEntity2 != null){
					inputItemDef.setMappedFieldName(field1orEntity2);
				}
	
				// 入力チェック - 必須
				Boolean isRequired = true;													
				String isAllowedNull = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isAllowedNull")).getColumnIndex()));
				
				if(apiNameLocal.equals(AWAG1Constant.SEARCH_EVENT_NAME)) {
					// 検索API
					isRequired = false;					
				}else if (apiNameLocal.equals(AWAG1Constant.GET_EVENT_NAME) ){
					// 取得API
					if(isPK == null){
						isRequired = false;
					}
				}else if (apiNameLocal.equals(AWAG1Constant.INPUT_EVENT_NAME)){
					// 登録API
					if (isPK == null) {
						isRequired = false;
					}
					
					if (isAllowedNull != null && isAllowedNull.equals("Y")) {
						isRequired = false;
					}					
				}else if (apiNameLocal.equals(AWAG1Constant.UPDATE_EVENT_NAME)){
					// 更新API					
					if(isPK == null){						
						isRequired = false;
						
						// ロックバージョンが"Y"の場合のみ必須に設定
						if(isVersion != null && isVersion.equals("Y")){
							isRequired = true;
						}
					}
					
					if(isAllowedNull != null && isAllowedNull.equals("Y")){						
						isRequired = false;
						
						// ロックバージョンが"Y"の場合のみ必須に設定
						if(isVersion != null && isVersion.equals("Y")){
							isRequired = true;
						}
					}
					
				}else if (apiNameLocal.equals(AWAG1Constant.DELETE_EVENT_NAME)){
					// 削除API					
					if(isPK == null){						
						isRequired = false;
						
						// ロックバージョンが"Y"の場合のみ必須に設定
						if(isVersion != null && isVersion.equals("Y")){
							isRequired = true;
						}
					}					
				}	
				inputItemDef.setNotNull(isRequired);
				
				// 削除API以外 or 削除APIで必須値のみ、入力チェック関連項目を設定する
				if(!apiNameLocal.equals(AWAG1Constant.DELETE_EVENT_NAME) || (apiNameLocal.equals(AWAG1Constant.DELETE_EVENT_NAME) && isRequired)){
	
					// 入力チェック - 形式
					String formatType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_formatValidationRule")).getColumnIndex()));
					if (formatType != null && !formatType.isEmpty()) {
						inputItemDef.setFormatType(com.ibm.jp.awag2.generator.model.common.FormatType.valueOf(formatType));
					}
		
					// 入力チェック - 最大桁数
					Double maxLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_maxLength")).getColumnIndex()));
					// 入力チェック - 最小桁数
					Double minLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_minLength")).getColumnIndex()));
					// 入力チェック - 一致桁数
					Double length = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_length")).getColumnIndex()));
					
					if (maxLength != null) {
						if(inputItemDef.getFormatType() == FormatType.DECIMAL || inputItemDef.getFormatType() == FormatType.FLOAT){
		
							if(minLength != null){
		
								// 整数部桁数
								inputItemDef.setInteger(Integer.valueOf(maxLength.intValue() - minLength.intValue()));
		
								// 小数部桁数
								inputItemDef.setFraction(Integer.valueOf(minLength.intValue()));
		
							}else{
		
								// 整数部桁数
								inputItemDef.setInteger(Integer.valueOf(maxLength.intValue()));
								
								// 小数部桁数
								inputItemDef.setFraction(0);
								
		
							}
		
						}else{
							// 最大桁数
							inputItemDef.setMaxLength(Integer.valueOf(maxLength.intValue()));
		
							if(minLength != null){
								// 最小桁数
								inputItemDef.setMinLength(Integer.valueOf(minLength.intValue()));
		
							}
						}
						
					}else{
						
						if(minLength == null && length != null){
							// 最大桁数
							inputItemDef.setMaxLength(Integer.valueOf(length.intValue()));
							// 最小桁数
							inputItemDef.setMinLength(Integer.valueOf(length.intValue()));
						}
						
					}
				
				}
				
				if(apiNameLocal.equals(AWAG1Constant.SEARCH_EVENT_NAME)){
					
					// オプション
					Map<String,String> options = new HashMap<String,String>();
					
					String conditionOperator = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_conditionOperator")).getColumnIndex()));
					
					// 検索 - 一致条件
					if (conditionOperator.equals("完全")) {

						options.put("select_where_op", "EQUALS");
						
					} else if (conditionOperator.equals("前方")) {

						options.put("select_where_op", "STARTSWITH");

					} else if (conditionOperator.equals("部分")) {

						options.put("select_where_op", "CONTAINS");
					
					}

					inputItemDef.setOptions(options);
					
				}
				
				inputItemList.add(inputItemDef);
		
			}
			
		}	

		return inputItemList;

	}

	/**
	 * 指定したリソース定義シートよりAPI項目定義を読み込み、指定したAPIに対するAPI出力項目のリストにマップする。
	 * @param apiNameLocal 指定したAPI名
	 * @param sheet 対象のSheetオブジェクト
	 * @return API出力項目定義のリスト
	 */
	private List<OutputItemDefinition> applyAWAG2OutputItemDefinition(String apiNameLocal, Sheet sheet) {
		
		// API出力項目定義リスト
		List<OutputItemDefinition> outputItemDefs = new ArrayList<OutputItemDefinition>();
		
		OutputItemDefinition outputItemDef = null;
		List<OutputItemDefinition> outputItemDefsSecond = null;
		
		if(apiNameLocal.equals(AWAG1Constant.SEARCH_EVENT_NAME)){
		
			outputItemDef = new OutputItemDefinition();
			outputItemDefs.add(outputItemDef);
			
			outputItemDefsSecond = new ArrayList<OutputItemDefinition>();
			outputItemDef.setItemDefinitions(outputItemDefsSecond);
		}
		
		// テーブル名（英字） 
		String entityName1 = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_tableName")));
		
		// カラム名（英字）
		String firstGroupCellAddr = this.definitionSheetMappings.get("AWAG1_column_columnName");
		Cell firstColumnCell = getCell(sheet, firstGroupCellAddr);
		int numOfCode = countColumnNum(sheet, firstGroupCellAddr);
		int offset = firstColumnCell.getRowIndex();
		
		for (int i = 0; i < numOfCode; i++) {

			int targetRow = offset + i;
			
			String itemNameLocal = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_displayName")).getColumnIndex()));
			String itemName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_fieldName")).getColumnIndex()));
			String field1orEntity2 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_columnName")).getColumnIndex()));
			
			if(apiNameLocal.equals(AWAG1Constant.SEARCH_EVENT_NAME)){
				
				// 階層1-データ項目名（表示用）
				outputItemDef.setNameLocal("一覧");
				
				// 階層1-データ項目名（英字）
				outputItemDef.setName("list");
				
				// 階層1-多重
				outputItemDef.setMultiple(true);
				
				// モデルタイプ
				outputItemDef.setModelType(ModelType.Entity);
				
				// テーブル名（英字）
				outputItemDef.setMappedFieldName(entityName1);				
				
				// API出力項目（第2階層）
				OutputItemDefinition outputItemDefSecond = new OutputItemDefinition();
				
				// 階層2-データ項目名
				outputItemDefSecond.setNameLocal(itemNameLocal);
				
				// 階層2-データ項目名 英字
				outputItemDefSecond.setName(itemName);
				
				// 第1階層 - カラム名 or 第2階層 - テーブル名
				outputItemDefSecond.setMappedEntityName(entityName1);
				
				// 第2階層 - カラム名 or 第3階層 - テーブル名
				outputItemDefSecond.setMappedFieldName(field1orEntity2);					
				
				outputItemDefsSecond.add(outputItemDefSecond);
				
				
			}else if(apiNameLocal.equals(AWAG1Constant.GET_EVENT_NAME)){
				
				outputItemDef = new OutputItemDefinition();
				
				// 階層1-データ項目名（表示用）
				outputItemDef.setNameLocal(itemNameLocal);
	
				// 階層1-データ項目名（英字）
				outputItemDef.setName(itemName);
	
				// 第1階層 - テーブル名
				if(entityName1 != null){
					outputItemDef.setMappedEntityName(entityName1);
				}
	
				// 第1階層 - カラム名 or 第2階層 - テーブル名
				if(field1orEntity2 != null){
					outputItemDef.setMappedFieldName(field1orEntity2);
				}
				
				outputItemDefs.add(outputItemDef);
				
			}			
			
		}	

		return outputItemDefs;

	}	
	
	/**
	 * 指定したAPI定義シートを読み込み、APIDefinitionオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return API定義を格納したAPIDefinitionオブジェクト
	 */
	private APIDefinition buildAPIDefinition(Sheet sheet) {

		APIDefinition apiDef = new APIDefinition();

		// API名
		apiDef.setName(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("apiName"))));

		// API名（表示用）
		apiDef.setNameLocal(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("apiNameLocal"))));

		// パッケージ名
		apiDef.setJavaPackage(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("javaPackage"))));

		// PATH
		apiDef.setPath(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("apiPath"))));

		// METHOD
		String method = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("apiMethod")));
		if (method != null && !method.isEmpty()) {
			apiDef.setMethod(HTTPMethodType.valueOf(method));
		}

		// API種別
		String apiType = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("apiType")));
		if (apiType != null && !apiType.isEmpty()) {
			apiDef.setApiType(APIType.valueOf(apiType));
		}

		// 説明
		apiDef.setDescription(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("description"))));

		// APIオプション
		try {
			apiDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("api_options")))));
		} catch(Exception e) {
			throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] APIオプションの書式が不正です。", e);
		}

		//API入力項目定義（第1階層）
		List<InputItemDefinition> inputItemDefs = new ArrayList<InputItemDefinition>();
		InputItemDefinition inputItemDef = null;
		//API出力項目定義（第1階層）
		List<OutputItemDefinition> outputItemDefs = new ArrayList<OutputItemDefinition>();
		OutputItemDefinition outputItemDef = null;

		//API入力項定義目（第2階層）
		List<InputItemDefinition> inputItemDefsSecond = new ArrayList<InputItemDefinition>();
		InputItemDefinition inputItemDefSecond = null;
		//API出力項定義目（第2階層）
		List<OutputItemDefinition> outputItemDefsSecond = new ArrayList<OutputItemDefinition>();
		OutputItemDefinition outputItemDefSecond = new OutputItemDefinition();

		// 第2階層判定用の値（入力「データ項目名（表示用）」）
		String inputItemNameBefore = null;
		// 第2階層判定用の値（出力「データ項目名（表示用）」）
		String outputItemNameBefore = null;

		//API入力項定義目（第3階層）
		List<InputItemDefinition> inputItemDefsThird = new ArrayList<InputItemDefinition>();
		//API出力項定義目（第3階層）
		List<OutputItemDefinition> outputItemDefsThird = new ArrayList<OutputItemDefinition>();

		// 第3階層判定用の値（入力「データ項目名」（第2階層））
		String inputItemNameSecondBefore = null;
		// 第3階層判定用の値（出力「データ項目名」（第2階層））
		String outputItemNameSecondBefore = null;

		// 1行目のデータ項目名（英字）（第1階層）
		String firstItemName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("column_itemName")));

		if(firstItemName != null){

			//API入出力項目がブランクでない場合、API入出力項目の行数分繰り返す
			String firstColumnCellAddr = this.definitionSheetMappings.get("column_itemName");
			Cell firstColumnCell = getCell(sheet, firstColumnCellAddr);
			int numOfColumn = countColumnNum(sheet, firstColumnCellAddr);
			int offset = firstColumnCell.getRowIndex();
			for (int i = 0; i < numOfColumn; i++) {

				int targetRow = offset + i;

				// 階層1-データ項目名（英字）
				String itemName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemName")).getColumnIndex()));

				// 第2階層判定フラグ
				Boolean isSecond = false;

				// データ項目名（第2階層）
				String itemNameSecond = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameSecond")).getColumnIndex()));
				if (itemNameSecond != null && !itemNameSecond.isEmpty()) {
					isSecond = true;
				}

				// 第3階層判定フラグ
				Boolean isThird = false;

				// データ項目名（第3階層）
				String itemNameThird = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameThird")).getColumnIndex()));
				if (itemNameThird != null && !itemNameThird.isEmpty()) {
					isThird = true;
				}

				// I/O
				String IO = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_IO")).getColumnIndex()));

				if(IO == null){

					List<String> errorList = new ArrayList<String>();
					errorList.add("I/0は必須入力項目です。[sheet]" + apiDef.getName());

					throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error]" + errorList, null);

				}else{

					if(IO.equals("I")){

						// 入力チェック - 必須
						Cell cell = getCell(sheet, this.definitionSheetMappings.get("column_api_isNotNull"));
						String isStr = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
						Boolean isNotNull = false;
						if (isStr != null && isStr.equals("Y")) {
							isNotNull = true;
						}

						// 入力チェック - 形式
						String formatType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_formatType")).getColumnIndex()));

						// 入力チェック - 最大桁数
						Double maxLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_maxLength")).getColumnIndex()));
						// 入力チェック - 最小桁数
						Double minLength = getNumberValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_minLength")).getColumnIndex()));

						//初回判定
						if(inputItemNameBefore == null){

							//API入力項目定義を初期化
							inputItemDef = new InputItemDefinition();
							inputItemDefsSecond = new ArrayList<InputItemDefinition>();
	//						print("「" + itemName + "」用にAPI入力項目定義を初期化(初回)");
							inputItemDefs.add(inputItemDef);

							//API入力項目定義（第2階層）を初期化
							inputItemDefSecond = new InputItemDefinition();
	//						print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");

							inputItemDefsSecond.add(inputItemDefSecond);

						}else{

							if(itemName == null){

								//API入力項目定義を初期化
								inputItemDef = new InputItemDefinition();
								inputItemDefsSecond = new ArrayList<InputItemDefinition>();
								inputItemDefs.add(inputItemDef);

								//API入力項目定義（第2階層）を初期化
								inputItemDefSecond = new InputItemDefinition();
	//							print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");
								inputItemDefsSecond.add(inputItemDefSecond);

							}else{

								//「データ項目名（表示用）」が1シート前の値と異なる場合
								if(!inputItemNameBefore.equals(itemName)){

									//API入力項目定義を初期化
									inputItemDef = new InputItemDefinition();
									inputItemDefsSecond = new ArrayList<InputItemDefinition>();
									inputItemDefs.add(inputItemDef);

									//API入力項目定義（第2階層）を初期化
									inputItemDefSecond = new InputItemDefinition();
		//							print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");
									inputItemDefsSecond.add(inputItemDefSecond);

								}else{

									if(inputItemNameSecondBefore == null){

										//API入力項目定義（第2階層）を初期化
										inputItemDefSecond = new InputItemDefinition();
										inputItemDefsThird = new ArrayList<InputItemDefinition>();;
		//								print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");
										inputItemDefsSecond.add(inputItemDefSecond);

									}else{
										//「データ項目名（第2階層）」が1シート前の値と異なる場合
										if(!inputItemNameSecondBefore.equals(itemNameSecond)){

											//API入力項目定義（第2階層）を初期化
											inputItemDefSecond = new InputItemDefinition();
											inputItemDefsThird = new ArrayList<InputItemDefinition>();

		//									print("「" + itemNameSecond + "」用にAPI出力項目定義（第2階層）を初期化");
											inputItemDefsSecond.add(inputItemDefSecond);

										}

									}

								}
							}

						}

						// データ項目名（表示用）
						inputItemDef.setNameLocal(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameLocal")).getColumnIndex())));

						// データ項目名（英字）
						inputItemDef.setName(itemName);

						// 多重
						String multiple = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_multiple")).getColumnIndex()));
						Boolean isMultiple = false;
						if (multiple != null && multiple.equals("Y")) {
							isMultiple = true;
						}
						inputItemDef.setMultiple(isMultiple);

						// 第1階層 - テーブル名
						String entityName1 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_entityName1")).getColumnIndex()));
						if(entityName1 != null){
							inputItemDef.setMappedEntityName(entityName1);
						}

						// 第1階層 - カラム名 or 第2階層 - テーブル名
						String field1orEntity2 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName1_or_entityName2")).getColumnIndex()));
						if(field1orEntity2 != null){
							inputItemDef.setMappedFieldName(field1orEntity2);
						}

						// 第2階層判定
						if (isSecond) {

							// モデルタイプ
							inputItemDef.setModelType(ModelType.Entity);

							// データ項目名
							inputItemDefSecond.setNameLocal(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameLocalSecond")).getColumnIndex())));

							// データ項目名 英字
							inputItemDefSecond.setName(itemNameSecond);

							// 多重
							String multipleSecond = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_multipleSecond")).getColumnIndex()));
							Boolean isMultipleSecond = false;
							if (multipleSecond != null && multipleSecond.equals("Y")) {
								isMultipleSecond = true;
							}
							inputItemDefSecond.setMultiple(isMultipleSecond);

							// 第2階層 - テーブル名
							if(field1orEntity2 != null){
								inputItemDefSecond.setMappedEntityName(field1orEntity2);
							}

							// 第2階層 - カラム名 or 第3階層 - テーブル名
							String field2orEntity3 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName2_or_entityName3")).getColumnIndex()));
							if(field2orEntity3 != null){
								inputItemDefSecond.setMappedFieldName(field2orEntity3);
							}
							// 第3階層判定
							if(isThird){

								inputItemDefSecond.setModelType(ModelType.Entity);

								InputItemDefinition inputItemDefThird = new InputItemDefinition();

								// データ項目名
								inputItemDefThird.setNameLocal(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameLocalThird")).getColumnIndex())));

								// データ項目名 英字
								inputItemDefThird.setName(itemNameThird);

								// 第3階層 - テーブル名
								if(field2orEntity3 != null){
									inputItemDefThird.setMappedEntityName(field2orEntity3);
								}

								// 第3階層 - カラム名
								String field3 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName3")).getColumnIndex()));
								if(field3 != null){
									inputItemDefThird.setMappedFieldName(field3);
								}

								// 入力チェック - 必須
								inputItemDefThird.setNotNull(isNotNull);

								// 入力チェック - 形式
								if (formatType != null && !formatType.isEmpty()) {
									inputItemDefThird.setFormatType(com.ibm.jp.awag2.generator.model.common.FormatType.valueOf(formatType));
								}

								if (maxLength != null) {
									if(inputItemDefThird.getFormatType() == com.ibm.jp.awag2.generator.model.common.FormatType.DECIMAL || inputItemDefThird.getFormatType() == com.ibm.jp.awag2.generator.model.common.FormatType.FLOAT){

										if (minLength != null) {

											// 整数部桁数
											inputItemDefThird.setInteger(Integer.valueOf(maxLength.intValue() - minLength.intValue()));

											// 小数部桁数
											inputItemDefThird.setFraction(Integer.valueOf(minLength.intValue()));

										}else{

											// 整数部桁数
											inputItemDefThird.setInteger(Integer.valueOf(maxLength.intValue()));

										}

									}else{
										// 最大桁数
										inputItemDefThird.setMaxLength(Integer.valueOf(maxLength.intValue()));

										if (minLength != null) {
											// 最小桁数
											inputItemDefThird.setMinLength(Integer.valueOf(minLength.intValue()));
										}

									}

								}

								//オプション
								try {
									inputItemDefThird.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_options")).getColumnIndex()))));
								} catch(Exception e) {
									throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
								}

								inputItemDefsThird.add(inputItemDefThird);

								//第3階層のAPI出力項目を第2階層のAPI出力項目リストにセット
								inputItemDefSecond.setItemDefinitions(inputItemDefsThird);

							}else{

								// 入力チェック - 必須
								inputItemDefSecond.setNotNull(isNotNull);

								// 入力チェック - 形式
								if (formatType != null && !formatType.isEmpty()) {
									inputItemDefSecond.setFormatType(com.ibm.jp.awag2.generator.model.common.FormatType.valueOf(formatType));
								}

								if (maxLength != null) {
									if(inputItemDefSecond.getFormatType() == com.ibm.jp.awag2.generator.model.common.FormatType.DECIMAL  || inputItemDefSecond.getFormatType() == com.ibm.jp.awag2.generator.model.common.FormatType.FLOAT){

										if (minLength != null) {

											// 整数部桁数
											inputItemDefSecond.setInteger(Integer.valueOf(maxLength.intValue() - minLength.intValue()));

											// 小数部桁数
											inputItemDefSecond.setFraction(Integer.valueOf(minLength.intValue()));

										}else{

											// 整数部桁数
											inputItemDefSecond.setInteger(Integer.valueOf(maxLength.intValue()));

										}

									}else{
										// 最大桁数
										inputItemDefSecond.setMaxLength(Integer.valueOf(maxLength.intValue()));

										if (minLength != null) {
											// 最小桁数
											inputItemDefSecond.setMinLength(Integer.valueOf(minLength.intValue()));
										}

									}

								}

								//オプション
								try {
									inputItemDefSecond.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_options")).getColumnIndex()))));
								} catch(Exception e) {
									throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
								}

							}

							//第2階層のAPI出力項目を第1階層のAPI出力項目リストにセット
							inputItemDef.setItemDefinitions(inputItemDefsSecond);


						}else{

							// 入力チェック - 必須
							inputItemDef.setNotNull(isNotNull);

							// 入力チェック - 形式
							if (formatType != null && !formatType.isEmpty()) {
								inputItemDef.setFormatType(com.ibm.jp.awag2.generator.model.common.FormatType.valueOf(formatType));
							}

							if (maxLength != null) {
								if(inputItemDef.getFormatType() == com.ibm.jp.awag2.generator.model.common.FormatType.DECIMAL  || inputItemDef.getFormatType() == com.ibm.jp.awag2.generator.model.common.FormatType.FLOAT){

									if (minLength != null) {

										// 整数部桁数
										inputItemDef.setInteger(Integer.valueOf(maxLength.intValue() - minLength.intValue()));

										// 小数部桁数
										inputItemDef.setFraction(Integer.valueOf(minLength.intValue()));

									}else{

										// 整数部桁数
										inputItemDef.setInteger(Integer.valueOf(maxLength.intValue()));

									}

								}else{
									// 最大桁数
									inputItemDef.setMaxLength(Integer.valueOf(maxLength.intValue()));

									if (minLength != null) {
										// 最小桁数
										inputItemDef.setMinLength(Integer.valueOf(minLength.intValue()));
									}

								}

							}

							//オプション
							try {
								inputItemDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_options")).getColumnIndex()))));
							} catch(Exception e) {
								throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
							}

						}

						//2回目以降の判定用に入力「データ項目名（表示用）」を変数にセット
						inputItemNameBefore = itemName;

						//2回目以降の判定用に入力「データ項目名」（第2階層）を変数にセット
						inputItemNameSecondBefore = itemNameSecond;

					}else{

						//初回判定
						if(outputItemNameBefore == null){

							//API出力項目定義を初期化
							outputItemDef = new OutputItemDefinition();
							outputItemDefsSecond = new ArrayList<OutputItemDefinition>();
	//						print("「" + itemName + "」用にAPI出力項目定義を初期化(初回)");

							outputItemDefs.add(outputItemDef);

							//API入力項目定義（第2階層）を初期化
							outputItemDefSecond = new OutputItemDefinition();
	//						print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");

							outputItemDefsSecond.add(outputItemDefSecond);

						}else{

							//データ項目名が1シート前の値と異なる場合
							if(!outputItemNameBefore.equals(itemName)){

								//API出力項目定義を初期化
								outputItemDef = new OutputItemDefinition();
								outputItemDefsSecond = new ArrayList<OutputItemDefinition>();
								outputItemDefs.add(outputItemDef);

								//API出力項目定義（第2階層）を初期化
								outputItemDefSecond = new OutputItemDefinition();
	//							print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");
								outputItemDefsSecond.add(outputItemDefSecond);

							}else{

								if(outputItemNameSecondBefore == null){

									//API入力項目定義（第2階層）を初期化
									outputItemDefSecond = new OutputItemDefinition();
									outputItemDefsThird = new ArrayList<OutputItemDefinition>();;
	//								print("「" + itemNameSecond + "」用にAPI入力項目定義（第2階層）を初期化");
									outputItemDefsSecond.add(outputItemDefSecond);

								}else{
									//「データ項目名」（第2階層）が1シート前の値と異なる場合
									if(!outputItemNameSecondBefore.equals(itemNameSecond)){

										//API入力項目定義（第2階層）を初期化
										outputItemDefSecond = new OutputItemDefinition();
										outputItemDefsThird = new ArrayList<OutputItemDefinition>();

	//									print("「" + itemNameSecond + "」用にAPI出力項目定義（第2階層）を初期化");
										outputItemDefsSecond.add(outputItemDefSecond);

									}

								}

							}

						}

						// データ項目名（表示用）
						outputItemDef.setNameLocal(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameLocal")).getColumnIndex())));

						// データ項目名（英字）
						outputItemDef.setName(itemName);

						// 多重
						String multiple = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_multiple")).getColumnIndex()));
						Boolean isMultiple = false;
						if (multiple != null && multiple.equals("Y")) {
							isMultiple = true;
						}
						outputItemDef.setMultiple(isMultiple);

						// 第1階層 - テーブル名
						String entityName1 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_entityName1")).getColumnIndex()));
						if(entityName1 != null){
							outputItemDef.setMappedEntityName(entityName1);
						}

						// 第1階層 - カラム名 or 第2階層 - テーブル名
						String field1orEntity2 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName1_or_entityName2")).getColumnIndex()));
						if(field1orEntity2 != null){
							outputItemDef.setMappedFieldName(field1orEntity2);
						}
						// 第2階層判定
						if (isSecond) {

							// モデルタイプ
							outputItemDef.setModelType(ModelType.Entity);

							// データ項目名
							outputItemDefSecond.setNameLocal(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameLocalSecond")).getColumnIndex())));

							// データ項目名 英字
							outputItemDefSecond.setName(itemNameSecond);

							// 多重
							String multipleSecond = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_multipleSecond")).getColumnIndex()));
							Boolean isMultipleSecond = false;
							if (multipleSecond != null && multipleSecond.equals("Y")) {
								isMultipleSecond = true;
							}
							outputItemDefSecond.setMultiple(isMultipleSecond);

							// 第2階層 - テーブル名
							if(field1orEntity2 != null){
								outputItemDefSecond.setMappedEntityName(field1orEntity2);
							}

							// 第2階層 - カラム名 or 第3階層 - テーブル名
							String field2orEntity3 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName2_or_entityName3")).getColumnIndex()));
							if(field2orEntity3 != null){
								outputItemDefSecond.setMappedFieldName(field2orEntity3);
							}

							// 第3階層判定
							if(isThird){

								outputItemDefSecond.setModelType(ModelType.Entity);

								OutputItemDefinition outputItemDefThird = new OutputItemDefinition();

								// データ項目名
								outputItemDefThird.setNameLocal(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_itemNameLocalThird")).getColumnIndex())));

								// データ項目名 英字
								outputItemDefThird.setName(itemNameThird);

								// 第3階層 - テーブル名
								if(field2orEntity3 != null){
									outputItemDefThird.setMappedEntityName(field2orEntity3);
								}
								// 第3階層 - カラム名
								String field3 = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_fieldName3")).getColumnIndex()));
								if(field3 != null){
									outputItemDefThird.setMappedFieldName(field3);
								}
								//オプション
								try {
									outputItemDefThird.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_options")).getColumnIndex()))));
								} catch(Exception e) {
									throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
								}

								outputItemDefsThird.add(outputItemDefThird);

								//第3階層のAPI出力項目を第2階層のAPI出力項目リストにセット
								outputItemDefSecond.setItemDefinitions(outputItemDefsThird);

							}else{

								//オプション
								try {
									outputItemDefSecond.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_options")).getColumnIndex()))));
								} catch(Exception e) {
									throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
								}

							}

							//第2階層のAPI出力項目を第1階層のAPI出力項目リストにセット
							outputItemDef.setItemDefinitions(outputItemDefsSecond);

						}else{

							//オプション
							try {
								outputItemDef.setOptions(flatJsonStringToMap(getEvaluatedCellValue(sheet.getRow(targetRow).getCell(getCell(sheet, this.definitionSheetMappings.get("column_api_options")).getColumnIndex()))));
							} catch(Exception e) {
								throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] オプションの書式が不正です。", e);
							}

						}

						//2回目以降の判定用に出力「データ項目名（表示用）を変数にセット
						outputItemNameBefore = itemName;

						//2回目以降の判定用に出力「データ項目名」（第2階層）を変数にセット
						outputItemNameSecondBefore = itemNameSecond;

					}

				}

			}

			apiDef.setOutputItemDefinitions(outputItemDefs);
			apiDef.setInputItemDefinitions(inputItemDefs);

			List<String> errorList = validate(apiDef);
			if (!errorList.isEmpty()) {
				throw new GenerateException("API定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error]" + errorList, null);
			}

		}

		return apiDef;

	}

	/**
	 * API定義シートの入力チェックを行う。
	 * @param apiDef API定義シートの値を読み込んだAPIDefinitionオブジェクト
	 * @return エラーリスト
	 */
	private List<String> validate(APIDefinition apiDef) {
		List<String> errorList = new ArrayList<String>();

		// 単項目チェック
		FormatValidator validator = new FormatValidator();
		errorList.addAll(validator.isValid(apiDef));

		// API種別判定用フラグ
		Boolean isMulti = false;

		if(apiDef.getApiType() == APIType.MultiRead){
			isMulti = true;
		}else if(apiDef.getApiType() == APIType.MultiCreate){
			isMulti = true;
		}else if(apiDef.getApiType() == APIType.MultiUpdate){
			isMulti = true;
		}

		for (InputItemDefinition iidDef : apiDef.getInputItemDefinitions()) {
			List<String> inputItemDefErrorList = validator.isValid(iidDef);

			if(!inputItemDefErrorList.isEmpty()) {
				errorList.add("API定義入力チェックエラー。[Column]" + iidDef.getName() + inputItemDefErrorList);
			} else {

				// 相関チェック
				if(iidDef.getItemDefinitions() != null){

					// API入力項目数分繰り返す
					for (ItemDefinition iidDefSecond : iidDef.getItemDefinitions()) {

						if(iidDefSecond.getName() == null) {
							// 階層2-「データ項目名 英字」(F列)が存在しない場合

							// 階層1-多重が"Yの場合"
							if (iidDef.isMultiple()){
								errorList.add("階層2-データ項目名 英字が未入力。[1stItem]"  + iidDef.getName() + "[2ndItem]"+ iidDefSecond.getName());
							}

						}else{
							// 階層2-「データ項目名 英字」(F列)が存在する場合

							// API種別が"Multi"から始まるシートにおいて、「第1階層 - テーブル名」(K列)の値が存在するとエラー　※最終行のみ
							if(isMulti && iidDef.getMappedEntityName() != null){
								errorList.add("第1階層 - テーブル名に値が入力されています。[1stItem]" + iidDef.getName() + "[2ndItem]"+ iidDefSecond.getName());
							}
						}

						if(iidDefSecond.getItemDefinitions() != null){
							// API入力項目数分繰り返す
							for (ItemDefinition iidDefThird : iidDefSecond.getItemDefinitions()) {
								if (iidDefSecond.isMultiple()){
									// 階層2-「多重」(G列)が"Y"の場合、階層3-「データ項目名 英字」(I列)がブランクだとエラー
									if(iidDefThird.getName() == null){
										errorList.add("階層3-データ項目名 英字が未入力。[1stItem]" + iidDef.getName() + "[2ndItem]"+ iidDefSecond.getName() + "[3ndItem]"+ iidDefThird.getName());
									}
								}
							}
						}else{
							//入力項目「階層3-データ項目名 英字」が存在しない場合
							if (iidDefSecond.isMultiple()) {
								errorList.add("階層3-データ項目名 英字が未入力。[1stItem]" + iidDef.getName() + "[2ndItem]"+ iidDefSecond.getName() + "[3ndItem]"+ null);
							}
						}

					}

				}else{

					if (iidDef.isMultiple()) {
						//入力項目「階層2-データ項目名 英字」が存在しない場合
						errorList.add("階層2-データ項目名 英字が未入力。[1stItem]"  + iidDef.getName() + "[2ndItem]"+ null);
					}
				}
			}
		}

		for (OutputItemDefinition oidDef : apiDef.getOutputItemDefinitions()) {
			List<String> outputItemDefErrorList = validator.isValid(oidDef);

			if(!outputItemDefErrorList.isEmpty()) {
				errorList.add("API定義入力チェックエラー。[Column]" + oidDef.getName() + outputItemDefErrorList);
			} else {

				// 相関チェック
				if(oidDef.getItemDefinitions() != null){

					// API入力項目数分繰り返す
					for (ItemDefinition oidDefSecond : oidDef.getItemDefinitions()) {

						if(oidDefSecond.getName() == null) {
							// 階層2-「データ項目名 英字」(F列)が存在しない場合

							// 階層1-多重が"Yの場合"
							if (oidDef.isMultiple()){
								errorList.add("階層2-データ項目名 英字が未入力。[1stItem]"  + oidDef.getName() + "[2ndItem]"+ oidDefSecond.getName());
							}

						}else{
							// 階層2-「データ項目名 英字」(F列)が存在する場合

							// API種別が"Multi"から始まるシートにおいて、「第1階層 - テーブル名」(K列)の値が存在するとエラー　※最終行のみ
							if(isMulti && oidDef.getMappedEntityName() != null){
								errorList.add("第1階層 - テーブル名に値が入力されています。[1stItem]" + oidDef.getName() + "[2ndItem]"+ oidDefSecond.getName());
							}
						}

						if(oidDefSecond.getItemDefinitions() != null){
							// API入力項目数分繰り返す
							for (ItemDefinition oidDefThird : oidDefSecond.getItemDefinitions()) {
								if (oidDefSecond.isMultiple()){
									// 階層2-「多重」(G列)が"Y"の場合、階層3-「データ項目名 英字」(I列)がブランクだとエラー
									if(oidDefThird.getName() == null){
										errorList.add("階層3-データ項目名 英字が未入力。[1stItem]" + oidDef.getName() + "[2ndItem]"+ oidDefSecond.getName() + "[3ndItem]"+ oidDefThird.getName());
									}
								}
							}
						}else{
							//入力項目「階層3-データ項目名 英字」が存在しない場合
							if (oidDefSecond.isMultiple()) {
								errorList.add("階層3-データ項目名 英字が未入力。[1stItem]" + oidDef.getName() + "[2ndItem]"+ oidDefSecond.getName() + "[3ndItem]"+ null);
							}
						}

					}

				}else{

					if (oidDef.isMultiple()) {
						//入力項目「階層2-データ項目名 英字」が存在しない場合
						errorList.add("階層2-データ項目名 英字が未入力。[1stItem]"  + oidDef.getName() + "[2ndItem]"+ null);
					}

				}
			}
		}


		return errorList;
	}

	// ---------------------------------------------------------------------------
	// ExcelブックのJPA定義情報を取得する
	// ---------------------------------------------------------------------------

	/**
	 * 指定したWorkbookに含まれるテーブル定義シートを読み込み、TableCategoryオブジェクトにマップする。
	 * @param workbook 対象のWorkbookオブジェクト
	 * @return テーブル定義を格納したTableCategoryオブジェクト
	 */

	private TableCategory buildTableCategory(Workbook workbook) {

		TableCategory tc = new TableCategory();
		List<TableDefinition> tableDefs = new ArrayList<TableDefinition>();

		//テーブル名重複チェック用
		List<String> tableList = new ArrayList<String>();

		// シート毎にリソース定義を構築する
		workbook.forEach(sheet -> {
			
			//AWAG1シート判定フラグ
			Boolean isAWAG1sheet = false;
			
			try {
				Cell topCell = getCell(sheet, this.definitionSheetMappings.get("sheetType"));
				if(topCell != null){
					if (topCell.getStringCellValue().equals("テーブル定義")) {
						tc.setJavaPackage(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("packageName"))));
						tableDefs.add(buildTableDefinition(sheet, tableList, isAWAG1sheet));
					}else if (topCell.getStringCellValue().equals("リソース定義")) {
						isAWAG1sheet = true;
						tc.setJavaPackage(getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_packageName"))));
						tableDefs.add(buildTableDefinition(sheet, tableList, isAWAG1sheet));
						print("リソース定義シート数(テーブル定義)：" + tableDefs.size());
					}
				}
			} catch (RuntimeException e) {
				if(!isAWAG1sheet){
					throw new GenerateException("テーブル定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);
				}else{
					throw new GenerateException("リソース定義シートのパース処理に失敗。[Sheet]" + sheet.getSheetName(), e);					
				}
			}
		});

		// 関連リソース定義紐付け用にリソース定義のMapを構築する
		Map<String, TableDefinition> tableDefMap = null;
		try {
			tableDefMap = tableDefs.stream().collect(Collectors.toMap(TableDefinition::getTableName, s -> s));
		} catch (Exception e) {
			throw new GenerateException("テーブル名の指定が不正です。", e);
		}

		// 関連リソース定義を設定する
		for (TableDefinition tableDef : tableDefs) {

			Map<String, ColumnDefinition> tablecolumnDefMap = null;
			try {
				tablecolumnDefMap = tableDef.getColumnDefinitions().stream().collect(Collectors.toMap(ColumnDefinition::getName, s -> s));
			} catch (Exception e) {
					throw new GenerateException("カラム名（英字）の指定が不正です。[Table]" + tableDef.getTableName(), e);
			}

			for (RelationDefinition relationDef : tableDef.getRelationDefinitions()) {
				TableDefinition tableDefEmbed = tableDefMap.get(relationDef.getName());
				if (tableDefEmbed != null) {

					Map<String, ColumnDefinition> embeddedTablecolumnDefMap = null;
					try {
						embeddedTablecolumnDefMap = tableDefEmbed.getColumnDefinitions().stream().collect(Collectors.toMap(ColumnDefinition::getName, s -> s));
					} catch (Exception e) {
						throw new GenerateException("関連定義の結合キー（子）の指定が不正です。[Embedded Resource]" + relationDef.getName(), e);
					}

					// 関連リソース定義の結合キー存在チェック
					for (JoinKey joinKey : relationDef.getJoinKeyList()) {
						if (tablecolumnDefMap.get(joinKey.getKeyParent()) == null) {
							throw new GenerateException("関連リソースに定義された結合キー（親）が存在しません。[Table]" + tableDefEmbed.getTableName() + "[Join Key]" + joinKey.getKeyParent(), new RuntimeException());
						}

						if (embeddedTablecolumnDefMap.get(joinKey.getKeyDependence()) == null) {
							throw new GenerateException("関連リソースに定義された結合キー（子）が存在しません。[Table]" + tableDefEmbed.getTableName() + "[Join Key]" + joinKey.getKeyDependence(), new RuntimeException());
						}
					}

					relationDef.setTableDefinition(tableDefEmbed);
				} else {
					// 関連リソース定義のリソース存在チェック
					throw new GenerateException("関連リソースに定義されたテーブルが存在しません。[Table]" + tableDef.getTableName(), new RuntimeException());
				}
			}
		}

		tc.setTableDefinitions(tableDefs);

		return tc;
	}

	/**
	 * 指定したテーブル定義シートを読み込み、TableDefinitionオブジェクトにマップする。
	 * @param sheet 対象のSheetオブジェクト
	 * @return テーブル定義を格納したTableDefinitionオブジェクト
	 */
	private TableDefinition buildTableDefinition(Sheet sheet, List<String> tableList, Boolean isAWAG1sheet) {

		TableDefinition tableDef = new TableDefinition();

		String packageName = null;
		Double maxResult = null;
		String schemaName = null;
		String tableNameLocal = null;
		String tableName = null;
		String tableOptions = null;

		if(isAWAG1sheet){			
			// AWAG1の定義シートより値を取得
			packageName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_packageName")));
			maxResult = getNumberValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_maxResult")));
			schemaName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_schemaName")));
			tableNameLocal = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_tableNameLocal")));
			tableName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("AWAG1_tableName")));
		}else{			
			// AWAG2の定義シートより値を取得
			packageName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("packageName")));
			maxResult = getNumberValue(getCell(sheet, this.definitionSheetMappings.get("maxResult")));
			schemaName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("schemaName")));
			tableNameLocal = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("tableNameLocal")));
			tableName = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("tableName")));
			tableOptions = getEvaluatedCellValue(getCell(sheet, this.definitionSheetMappings.get("table_options")));
		}		
		
		// パッケージ名
		tableDef.setJavaPackage(packageName);

		// 最大取得件数
		if (maxResult != null) {
			tableDef.setMaxResult(Integer.valueOf(maxResult.intValue()));
		}

		// スキーマ名
		tableDef.setSchemaName(schemaName);

		// テーブル名
		tableDef.setNameLocal(tableNameLocal);

		// テーブル名（英字）
		tableDef.setName(tableName);

		// テーブル・オプション
		try {
			tableDef.setOptions(flatJsonStringToMap(tableOptions));
		} catch(Exception e) {
			throw new GenerateException("テーブル定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error] テーブル・オプションの書式が不正です。", e);
		}

		// データ項目定義の構築
		List<ColumnDefinition> columnDefs = new ArrayList<ColumnDefinition>();
		
		String firstColumnCellAddr = null;
		
		if(isAWAG1sheet){
			firstColumnCellAddr = this.definitionSheetMappings.get("AWAG1_column_name");
		}else{
			firstColumnCellAddr = this.definitionSheetMappings.get("column_name");			
		}
		
		Cell firstColumnCell = getCell(sheet, firstColumnCellAddr);
		int numOfColumn = countColumnNum(sheet, firstColumnCellAddr);
		int offset = firstColumnCell.getRowIndex();
		for (int i = 0; i < numOfColumn; i++) {

			ColumnDefinition columnDef = new ColumnDefinition();
			int targetRow = offset + i;

			String isPK = null;
			String columnNameLocal = null;
			String columnName = null;
			String isAllowedNull = null;
			String dataType = null;
			Double length = null;
			Double fraction = null;
			String isVersion = null;
			String columnOptions = null;			
			
			if(isAWAG1sheet){
				
				// AWAG1の定義シートより値を取得
				Cell cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isPk"));
				isPK = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_name"));
				columnNameLocal = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_columnName"));
				columnName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));				
				
				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isAllowedNull"));
				isAllowedNull = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_dataType"));
				dataType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_table_length"));
				length = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_fraction"));
				fraction = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_isVersion"));
				isVersion = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

//				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_column_options"));				
//				columnOptions = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
			}else{
				
				// AWAG2の定義シートより値を取得
				Cell cell = getCell(sheet, this.definitionSheetMappings.get("column_isPk"));
				isPK = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("column_name"));
				columnNameLocal = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("column_columnName"));
				columnName = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));				
				
				cell = getCell(sheet, this.definitionSheetMappings.get("column_isNotNull"));
				isAllowedNull = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("column_dataType"));
				dataType = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("column_table_length"));
				length = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("column_fraction"));
				fraction = getNumberValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("column_isVersion"));
				isVersion = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("column_table_options"));				
				columnOptions = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));				
				
			}			
			
			
			// PK
			Boolean is = false;
			if (isPK != null && isPK.equals("Y")) {
				is = true;
			}
			columnDef.setPk(is);

			// カラム名
			columnDef.setNameLocal(Optional.ofNullable(columnNameLocal).map(s -> s.toUpperCase()).orElse(null));

			// カラム名（英字）
			columnDef.setName(Optional.ofNullable(columnName).map(s -> s.toUpperCase()).orElse(null));

			// Null許可
			Boolean  isNotNull = false;
			if (isAllowedNull != null && isAllowedNull.equals("Y")) {
				isNotNull = true;
			}
			columnDef.setNotNull(isNotNull);

			// データ型
			if (dataType != null && !dataType.isEmpty()) {
				columnDef.setDataType(DataType.valueOf(dataType));
			}

			// 全体桁数
			if (length != null) {
				columnDef.setLength(length.intValue());
			}

			// 小数桁数
			if (fraction != null) {
				columnDef.setFraction(fraction.intValue());
			}else{
				columnDef.setFraction(0);
			}
			columnDefs.add(columnDef);

			// ロック用バージョン
			Boolean isVer = false;

			if (isVersion != null && isVersion.equals("Y")) {
				isVer = true;

				if (columnDef.getDataType().equals(DataType.TIMESTAMP)) {
					tableDef.setVersionType(VersionType.TIMESTAMP);
				} else if(columnDef.getDataType().equals(DataType.FLOAT)) {
					tableDef.setVersionType(VersionType.FLOAT);
				}
			}
			columnDef.setVersion(isVer);

			// カラム・オプション
			try {
				columnDef.setOptions(flatJsonStringToMap(columnOptions));
			} catch(Exception e) {
				throw new GenerateException("テーブル定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Column]" + columnDef.getName() + "[Error] オプションの書式が不正です。", e);
			}
		}

		// 関連定義の構築
		Map<String, RelationDefinition> relationDefMap = new HashMap<>();

		if(isAWAG1sheet){
			firstColumnCellAddr = this.definitionSheetMappings.get("AWAG1_relation_dependentResource");
		}else{		
			firstColumnCellAddr = this.definitionSheetMappings.get("relation_dependentResource");
		}
		
		firstColumnCell = getCell(sheet, firstColumnCellAddr);
		numOfColumn = countColumnNum(sheet, firstColumnCellAddr);
		offset = firstColumnCell.getRowIndex();

		for (int i = 0; i < numOfColumn; i++) {

			int targetRow = offset + i;

			String dependentResource = null;
			String relationPattern = null;
			String parentKeys = null;
			String dependenceKeys = null;			
			
			if(isAWAG1sheet){
				
				// AWAG1の定義シートより値を取得
				Cell cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_relation_dependentResource"));
				dependentResource = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_relation_pattern"));
				relationPattern = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_relation_joinKeyParent"));
				parentKeys = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("AWAG1_relation_joinKeyDependence"));
				dependenceKeys = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
			}else{
				
				// AWAG2の定義シートより値を取得
				Cell cell = getCell(sheet, this.definitionSheetMappings.get("relation_dependentResource"));
				dependentResource = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
				cell = getCell(sheet, this.definitionSheetMappings.get("relation_pattern"));
				relationPattern = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("relation_joinKeyParent"));
				parentKeys = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));

				cell = getCell(sheet, this.definitionSheetMappings.get("relation_joinKeyDependence"));
				dependenceKeys = getEvaluatedCellValue(sheet.getRow(targetRow).getCell(cell.getColumnIndex()));
				
			}			
			
			// 子リソース
			if (dependentResource == null || dependentResource.isEmpty()) {
				continue;
			}

			RelationDefinition relationDef = Optional.ofNullable(relationDefMap.get(dependentResource)).orElse(new RelationDefinition());
			relationDef.setName(dependentResource);

			// 関連パターン
			relationDef.setRelationPattern(RelationPattern.patternOf(relationPattern));

			// 結合キー
			List<String> parentKeyList = Optional.ofNullable(parentKeys).map(s -> Arrays.asList(s.split("\\s*,\\s*"))).orElse(new ArrayList<String>());

			List<String> dependenceKeyList = Optional.ofNullable(dependenceKeys).map(s -> Arrays.asList(s.split("\\s*,\\s*"))).orElse(new ArrayList<String>());

			if (parentKeyList.size() == dependenceKeyList.size()) {
				for (int j = 0; j < parentKeyList.size(); j++) {

					JoinKey joinKey = new JoinKey();

					joinKey.setKeyParent(parentKeyList.get(j).trim());
					joinKey.setKeyDependence(dependenceKeyList.get(j).trim());

					relationDef.addJoinKey(joinKey);
				}
			}

			relationDefMap.put(dependentResource, relationDef);
		}

		tableDef.setColumnDefinitions(columnDefs);
		tableDef.setRelationDefinitions(new ArrayList<RelationDefinition>(relationDefMap.values()));
		tableDef.setPkFields();

		List<String> errorList = validate(tableDef, tableList);
		if (!errorList.isEmpty()) {
			if(!isAWAG1sheet){
				throw new GenerateException("テーブル定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error]" + errorList, null);
			}else{
				throw new GenerateException("リソース定義シート入力チェックエラー。[Sheet]" + sheet.getSheetName() + "[Error]" + errorList, null);				
			}
		}

		tableList.add(tableName);

		return tableDef;
	}

	/**
	 * テーブル定義シートの入力チェックを行う。
	 * @param tableDef テーブル定義シートの値を読み込んだTableDefinitionオブジェクト
	 * @return エラーリスト
	 */
	private List<String> validate(TableDefinition tableDef, List<String> tableList) {
		List<String> errorList = new ArrayList<String>();

		// 単項目チェック
		FormatValidator validator = new FormatValidator();
		errorList.addAll(validator.isValid(tableDef));

		// テーブル名重複チェック
		if(tableList.contains((tableDef.getName()))){
			errorList.add("テーブル名が重複しています。[Table]" + tableDef.getName());
		}

		int versionCnt = 0;

		for (ColumnDefinition columnDef : tableDef.getColumnDefinitions()) {
			List<String> cloumnDefErrorList = validator.isValid(columnDef);

			if(!cloumnDefErrorList.isEmpty()) {
				errorList.add("カラム定義入力チェックエラー。[Column]" + columnDef.getName() + cloumnDefErrorList);
			} else {

				// 相関チェック
				if (columnDef.getDataType().equals(DataType.DECIMAL) && columnDef.getLength() == 0 && columnDef.getFraction() == 0) {
					errorList.add("全体桁数または小数桁数が未入力。[Column]" + columnDef.getName());
				}

				if (columnDef.isVersion()) {
					versionCnt++;
					if (versionCnt > 1) {
						errorList.add("ロック用バージョンは複数指定できません。[Column]" + columnDef.getName());
					}

					if (!columnDef.getDataType().equals(DataType.TIMESTAMP) && !columnDef.getDataType().equals(DataType.FLOAT)) {
						errorList.add("ロック用バージョンはTIMESTAMP, FLOAT以外のカラムには指定できません。[Column]" + columnDef.getName());
					}
				}
			}
		}

		if (tableDef.getPkFields().isEmpty()) {
			errorList.add("PK列が未指定。");
		}

		// 単項目チェック（関連リソース定義）
		for (RelationDefinition relationDef : tableDef.getRelationDefinitions()) {

			errorList.addAll(validator.isValid(relationDef));

			if (relationDef.getRelationPattern() == null) {
				errorList.add("関連定義の関連パターンを指定してください。[DependenceTable]" + relationDef.getName());
			}

			if (relationDef.getJoinKeyList().size() == 0) {
				errorList.add("関連定義の結合キーが不正です。同数の結合キーをカンマ区切りで指定してください。[DependenceTable]" + relationDef.getName());
			}
		}
		return errorList;
	}

}