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

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#getVersion()
	 */
	@Override
	public Long getVersion() {
		return this.version;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#setVersion(java.lang.Object)
	 */
	@Override
	public void setVersion(Long version) {
		this.versionString = Optional.ofNullable(version).map(v -> v.toString()).orElse(null);
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
		this.version = Optional.ofNullable(version).map(v -> Long.valueOf(version)).orElse(null);
	}
	
}
