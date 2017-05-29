package com.ibm.jp.awag.rest.common.model;

/**
 * Timestamp形式のリソースVersionを示すクラス。
 *
 */
public class ResourceVersionNotlockImpl implements ResourceVersion<Object> {

	/** Versionの文字列表現 */
	private Object version = new Object();
	
	@Override
	public Object getVersion() {
		return version;
	}

	@Override
	public void setVersion(Object version) {
	}

	@Override
	public String getVersionString() {
		return null;
	}

	@Override
	public void setVersionString(String version) {
	}
	
}
