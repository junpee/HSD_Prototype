package com.ibm.jp.awag.rest.common.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

import com.ibm.jp.awag.rest.common.util.TextUtility;

/**
 * Timestamp形式のリソースVersionを示すクラス。
 *
 */
public class ResourceVersionTimestampImpl implements ResourceVersion<Timestamp> {

	/** Versionの文字列表現 */
	private String versionString;
	
	/** Version */
	private Timestamp version;

	@Override
	public Timestamp getVersion() {
		return this.version;
	}

	@Override
	public void setVersion(Timestamp version) {
		this.versionString = Optional.ofNullable(version).map(v -> TextUtility.formatTimestamp(v)).orElse(null);
		this.version = version;
	}

	@Override
	public String getVersionString() {
		return this.versionString;
	}

	@Override
	public void setVersionString(String version) {
		this.versionString = version;
		this.version = Optional.ofNullable(version).map(v -> Timestamp.valueOf(v)).orElse(null);
	}
	
}
