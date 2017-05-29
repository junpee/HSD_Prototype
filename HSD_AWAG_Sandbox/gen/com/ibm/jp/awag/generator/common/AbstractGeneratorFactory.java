package com.ibm.jp.awag.generator.common;

import com.ibm.jp.awag.generator.common.model.AWAGConfig;

/**
 * Generator Factoryの抽象クラス。
 * 継承してGenerator毎に必要な初期化処理を実装する。
 *
 */
public abstract class AbstractGeneratorFactory {

	/** Generator設定ファイルパス */
	protected String generatorConfigFilePath;
	
	/**
	 * Generatorインスタンスを生成する。
	 * @param awagConfig Generator設定ファイルオブジェクト
	 * @return Generatorインスタンス
	 */
	public abstract AbstractGenerator create(AWAGConfig awagConfig);
	
}
