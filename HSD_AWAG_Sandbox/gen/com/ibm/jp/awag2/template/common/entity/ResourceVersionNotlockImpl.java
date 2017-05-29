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
 * Timestamp形式のリソースVersionを示すクラス。
 *
 */
public class ResourceVersionNotlockImpl implements ResourceVersion<Object> {

	/** Versionの文字列表現 */
	private Object version = new Object();
	
	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#getVersion()
	 */
	@Override
	public Object getVersion() {
		return version;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#setVersion(java.lang.Object)
	 */
	@Override
	public void setVersion(Object version) {
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#getVersionString()
	 */
	@Override
	public String getVersionString() {
		return null;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.entity.ResourceVersion#setVersionString(java.lang.String)
	 */
	@Override
	public void setVersionString(String version) {
	}
	
}
