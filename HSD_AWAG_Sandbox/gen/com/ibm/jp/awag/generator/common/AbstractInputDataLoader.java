package com.ibm.jp.awag.generator.common;

import java.util.Map;

import com.ibm.jp.awag.generator.common.model.AWAGConfig;

/**
 * InputDataLoaderの抽象クラス。
 * 継承してGenerator毎のコード生成入力情報の読み込み処理を実装する。
 * 
 */
public abstract class AbstractInputDataLoader {

	/** AWAG設定ファイルオブジェクト */
	protected AWAGConfig awagConfig;
	
	/** 入力データGeneratorのバージョン */
	private String inputDataGeneratorVersion;
	
	/**
	 * 唯一のコンストラクタ。
	 * @param awagConfig AWAG設定ファイルオブジェクト
	 */
	public AbstractInputDataLoader (AWAGConfig awagConfig) {
		this.awagConfig = awagConfig;	
	}
	
	/**
	 * inputDataGeneratorVersionを取得する。
	 * @return inputDataGeneratorVersion
	 */
	public abstract String getInputDataGeneratorVersion();

	/**
	 * コード生成入力情報を読み込む。
	 * @return 読み込んだコード生成入力情報を格納したMapオブジェクト
	 */
	public abstract Map<String, Object> load();
	
}
