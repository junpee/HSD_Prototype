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
package com.ibm.jp.awag2.generator.model.common;

import javax.xml.bind.annotation.XmlElement;

import com.ibm.jp.awag.generator.model.DefaultGeneratorConfig;

/** 
 * AWAG2の設定ファイルを示すクラス。
 * 
 */
public class AWAG2GeneratorConfig extends DefaultGeneratorConfig {
	
	/** Integrated Repository 連携ファイルPATH*/
	@XmlElement
	private String dataFilePath;

	/**
	 * Integrated Repository 連携ファイルPATHを取得する。
	 * @return Integrated Repository 連携ファイルPATH
	 */
	public String getDataFilePath() {
		return this.dataFilePath;
	}

	/**
	 * Integrated Repository 連携ファイルPATHを設定する。
	 * @param dataFilePath 設定するIntegrated Repository 連携ファイルPATH
	 */
	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}
	
}
