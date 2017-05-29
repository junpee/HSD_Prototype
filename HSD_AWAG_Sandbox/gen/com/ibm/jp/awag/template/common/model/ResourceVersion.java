package com.ibm.jp.awag.rest.common.model;

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
