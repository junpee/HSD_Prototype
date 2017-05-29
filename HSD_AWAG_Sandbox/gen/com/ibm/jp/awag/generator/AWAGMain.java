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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.jp.awag.generator.common.AbstractGenerator;
import com.ibm.jp.awag.generator.common.AbstractGeneratorFactory;
import com.ibm.jp.awag.generator.common.model.AWAGConfig;

/**
 * AutomatedWebAppGeneratorのメインクラス。
 *
 */
public class AWAGMain {

	/** AWAG設定ファイルのデフォルトパス */
	private static final String DEFAULT_CONFIG_FILE_PATH = "gen/AWAGConfig.xml";

	/** デフォルトGeneratorFactory */
	private static final String DEFAULT_FACTORY_NAME = "com.ibm.jp.awag.generator.DefaultGeneratorFactory";

	/** コマンドラインオプション接頭辞（汎用オプション） */
	private static final String ARG_PREFIX_OPTION = "-O";

	/** Excelファイル拡張子 xls */
	private static final String ARG_XLS_EXT = "xls";

	/** Excelファイル拡張子 xlsx */
	private static final String ARG_XLSX_EXT = "xlsx";
	
	/** XMLファイル拡張子 xml */
	private static final String ARG_XML_EXT = "xml";

	//	/** コマンドラインオプション接頭辞（AWAG設定ファイルパス） */
//	private static final String ARG_PREFIX_CONFIGPATH = "-C";

	/**
	 * Generatorのメインメソッド。 Generatorインスタンスの生成と実行を行う。
	 *
	 * @param args
	 *            Generatorのコマンドライン引数
	 */
	public static void main(String[] args) {

		// コマンドライン引数の処理
		Map<String, Object> optionMap = new HashMap<>();
		// Excelファイル引数
		ArrayList<String> files = new ArrayList<String>();
		optionMap.put("files", files);

		List<String> argList = Arrays.asList(args);
		for (String arg : argList) {
			// コマンドラインオプション接頭辞（汎用オプション）をparseする
			if (arg.startsWith(ARG_PREFIX_OPTION)) {
				String argStr = arg.substring(2);
				String[] argStrArray = argStr.split("=");
				try {
					optionMap.put(argStrArray[0], argStrArray[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					printError("不正な引数指定。[引数] " + arg);
					halt();
				}
			} else if (arg.endsWith(ARG_XLSX_EXT) || arg.endsWith(ARG_XLS_EXT) || arg.endsWith(ARG_XML_EXT)) {
				// Excelファイル格納
				files.add(arg);
			}
/*			// コマンドラインオプション接頭辞（AWAG設定ファイルパス）をparaseする
			else if (arg.startsWith(ARG_PREFIX_CONFIGPATH)) {
				awagConfigFilePath = arg.substring(2);
				if (awagConfigFilePath == null | awagConfigFilePath.isEmpty()) {
					printError("不正な引数指定。[引数] " + arg);
					halt();
				}
			}*/
		}

		// AWAG設定ファイルを読み込む
		String awagConfigFilePath = (String) optionMap.get("AWAG.configFilePath");
		if (awagConfigFilePath == null) {
			awagConfigFilePath = DEFAULT_CONFIG_FILE_PATH;
		}

		AWAGConfig awagConfig = null;
		try {
			awagConfig = loadConfig(awagConfigFilePath, AWAGConfig.class);
		} catch (RuntimeException e) {
			printError("AWAGConfigファイルの読み込み失敗。" + getStackErrorMessage(e));
			halt();
		}
		print("AWAG Version: " + awagConfig.getVersion());
		print("AWAG Config File: " + awagConfigFilePath);

		awagConfig.setOptionMap(optionMap);

		// Generator Factoryと実装を生成する
		String factoryClassName = (String) optionMap.get("AWAG.generatorFactory");
		if (factoryClassName == null || factoryClassName.isEmpty()) {
			factoryClassName = awagConfig.getFactoryClassName();
		}
		if (factoryClassName == null || factoryClassName.isEmpty()) {
			factoryClassName = DEFAULT_FACTORY_NAME;
		}

		AbstractGenerator generator = null;
		try {
			AbstractGeneratorFactory factory = (AbstractGeneratorFactory) Class.forName(factoryClassName).newInstance();
			generator = factory.create(awagConfig);
		} catch (RuntimeException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			printError("Generatorの初期化に失敗。" + getStackErrorMessage(e));
			halt();
		}
		print("Generator Factory: " + factoryClassName);

		// Generatorを実行する
		try {
			generator.run();
		} catch (RuntimeException e) {
			printError("Generator実行処理で失敗。" + getStackErrorMessage(e));
			halt();
		}
	}

	/**
	 * Mainを強制終了する。
	 */
	private static void halt() {
		System.exit(1);
	}
}
