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

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;
import com.ibm.jp.awag.rest.common.validator.Number.Type;

/**
 * 数値のValidatorクラス。
 *
 */
public class NumberValidator extends ValidatorBase implements ConstraintValidator<Number, String> {

	private Type type;
	private int minLength;
	private int maxLength;
	private int integer;
	private int fraction;
	private int length;

	@Override
	public void initialize(Number value) {
		this.type = value.type();
		this.minLength = value.minLength();
		this.maxLength = value.maxLength();
		this.length = value.length();
		this.integer = value.integer();
		this.fraction = value.fraction();
		messageKey = value.type().getMessageKey();
	}

	@Override
	protected boolean checkFormat(String value, ConstraintValidatorContext context) {

		StringBuilder regexSb = new StringBuilder();
		regexSb.append("^");
		message = messageProp.getString(this.type.getMessageKey());

		if (this.type == Type.DECIMAL) {
			if (fraction > 0) {
				regexSb.append("\\d{1,").append(integer).append("}(\\.\\d{1,").append(fraction).append("})*$");
			} else {
				regexSb.append("\\d{1,").append(integer).append("}$");
			}

			String lengthMessage = messageProp.getString("Validator.Format.common.lengthmsg.decimal").replaceAll("\\{integer\\}", String.valueOf(integer)).replaceAll("\\{fraction\\}", String.valueOf(fraction));
			message = message.replaceAll("\\{lengthmsg\\}", lengthMessage);

		} else {
			regexSb.append("[0-9]");

			if (minLength > 0 || maxLength > 0 || length > 0) {

				regexSb.append("{");
				String lengthMessage = null;

				if (length > 0) {
					regexSb.append(length);
					lengthMessage = messageProp.getString("Validator.Format.common.lengthmsg.match").replaceAll("\\{length\\}", String.valueOf(length));
				} else {
					if (minLength > 0 && maxLength == 0) {
						regexSb.append(minLength).append(",");
						lengthMessage = messageProp.getString("Validator.Format.common.lengthmsg.min").replaceAll("\\{min\\}", String.valueOf(minLength));
					}
					if (minLength == 0 && maxLength > 0) {
						regexSb.append("0,").append(maxLength);
						lengthMessage = messageProp.getString("Validator.Format.common.lengthmsg.max").replaceAll("\\{max\\}", String.valueOf(maxLength));
					}
					if (minLength > 0 && maxLength > 0) {
						regexSb.append(minLength).append(",").append(maxLength);
						lengthMessage = messageProp.getString("Validator.Format.common.lengthmsg.minmax").replaceAll("\\{min\\}", String.valueOf(minLength)).replaceAll("\\{max\\}", String.valueOf(maxLength));
					}
				}

				regexSb.append("}$");

				message = message.replaceAll("\\{lengthmsg\\}", lengthMessage);

			} else {
				regexSb.append("+$");
			}
		}

		String regex = regexSb.toString();

		Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
		logger.debug("value= " + value + "regex= " + regex + " minLength= " + minLength + "maxLength= " + maxLength + "length= " + length);

		if (Pattern.matches(regex, value)) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean checkParsing(String value, ConstraintValidatorContext context) {
		boolean ret = false;

		switch (this.type) {
		case SMALLINT:
			try {
				Short.parseShort(value);
				ret = true;
			} catch (NumberFormatException e) {
				ret = false;
			}
			break;
		case INTEGER:
			try {
				Integer.parseInt(value);
				ret = true;
			} catch (NumberFormatException e) {
				ret = false;
			}
			break;
		case BIGINT:
			try {
				Long.parseLong(value);
				ret = true;
			} catch (NumberFormatException e) {
				ret = false;
			}
			break;
		case FLOAT:
			try {
				Double.parseDouble(value);
				ret = true;
			} catch (NumberFormatException e) {
				ret = false;
			}
			break;
		case DECIMAL:
			try {
				new BigDecimal(value);
				ret = true;
			} catch (NumberFormatException e) {
				ret = false;
			}
			break;
		default:
			break;
		}

		return ret;
	}
}
