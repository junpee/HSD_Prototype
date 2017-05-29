package com.ibm.jp.awag.rest.common.model;

import java.util.Optional;

/**
 * Long形式のリソースVersionを示すクラス。
 *
 */
public class ResourceVersionLongImpl implements ResourceVersion<Long> {

	/** Versionの文字列表現 */
	private String versionString;
	
	/** Version */
	private Long version;

	@Override
	public Long getVersion() {
		return this.version;
	}

	@Override
	public void setVersion(Long version) {
		this.versionString = Optional.ofNullable(version).map(v -> v.toString()).orElse(null);
		this.version = version;
	}
	
	@Override
	public String getVersionString() {
		return this.versionString;
	}

	@Override
	public void setVersionString(String version) {
		this.versionString = version;
		this.version = Optional.ofNullable(version).map(v -> Long.valueOf(version)).orElse(null);
	}
	
}
