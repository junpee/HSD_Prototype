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
package com.ibm.jp.awag2.common.util;

import java.sql.Timestamp;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * タイムスタンプ文字列をjava.sql.TimeStamp型に変換するConverter
 *
 */
@Converter
public class TimestampConverter implements AttributeConverter<String, Timestamp> {

	/* (非 Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public Timestamp convertToDatabaseColumn(String timestampString) {
		
		return (timestampString != null) ? Timestamp.valueOf(timestampString) : null;
	
	}

	/* (非 Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public String convertToEntityAttribute(Timestamp timestamp) {

		return (timestamp != null) ? TextUtility.formatTimestamp(timestamp) : null;

	}

}
