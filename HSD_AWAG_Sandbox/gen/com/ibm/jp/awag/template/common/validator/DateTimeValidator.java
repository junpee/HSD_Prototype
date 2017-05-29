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
package com.ibm.jp.awag.rest.common.validator;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ibm.jp.awag.rest.common.validator.DateTime.Type;

/**
 * 日付、タイムスタンプのValidatorクラス。
 *
 */
public class DateTimeValidator extends ValidatorBase implements ConstraintValidator<DateTime, String> {

	private Type type;

	@Override
	public void initialize(DateTime value) {
		this.type = value.type();
		messageKey = value.type().getMessageKey();
	}

	@Override
	protected boolean checkFormat(String value, ConstraintValidatorContext context) {
		message = messageProp.getString(this.type.getMessageKey());
		return Pattern.matches(this.type.getRegex(), value);
	}

	@Override
	protected boolean checkParsing(String value, ConstraintValidatorContext context) {
		boolean ret = false;

		switch (this.type) {
		case DATE:
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.setLenient(false);
				sdf.parse(value);
				ret = true;
			} catch (ParseException e) {
				ret = false;
			}
			break;
		case TIMESTAMP:
			try {
				Timestamp.valueOf(value);
				ret = true;
			} catch (java.lang.IllegalArgumentException e) {
				ret = false;
			}
			break;
		default:
			break;
		}

		return ret;
	}
}
