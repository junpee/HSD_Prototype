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

import java.sql.Timestamp;
import java.util.Optional;

import com.ibm.jp.awag2.common.util.TextUtility;

/**
 * Timestamp形式のリソースVersionを示すクラス。
 *
 */
public class ResourceVersionTimestampImpl implements ResourceVersion<Timestamp> {

	/** Versionの文字列表現 */
	private String versionString;
	
	/** Version */
	private Timestamp version;

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#getVersion()
	 */
	@Override
	public Timestamp getVersion() {
		return this.version;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#setVersion(java.lang.Object)
	 */
	@Override
	public void setVersion(Timestamp version) {
		this.versionString = Optional.ofNullable(version).map(v -> TextUtility.formatTimestamp(v)).orElse(null);
		this.version = version;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#getVersionString()
	 */
	@Override
	public String getVersionString() {
		return this.versionString;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#setVersionString(java.lang.String)
	 */
	@Override
	public void setVersionString(String version) {
		this.versionString = version;
		this.version = Optional.ofNullable(version).map(v -> Timestamp.valueOf(v)).orElse(null);
	}
	
}
