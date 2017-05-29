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
package com.ibm.jp.awag2.common.entity;

/**
 * リソースのVersionを示すクラス。
 *
 * @param <VERSIONTYPE> Versionの型
 */
public interface ResourceVersion<VERSIONTYPE> {
	
	/**
	 * Versionを文字列形式で取得する。
	 * @return Versionの文字列表現
	 */
	public abstract String getVersionString();
	
	/**
	 * Versionを文字列形式で設定する。
	 * @param version Versionの文字列表現
	 */
	public abstract void setVersionString(String version);
	
	/**
	 * Versionを取得する。
	 * @return Version
	 */
	public abstract VERSIONTYPE getVersion();
	
	/**
	 * Versionを設定する。
	 * @param version Version
	 */
	public abstract void setVersion(VERSIONTYPE version);

}
