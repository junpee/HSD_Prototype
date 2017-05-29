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

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;
import com.ibm.jp.awag.rest.common.validator.StringFormat.Type;

/**
 * 文字列のValidatorクラス。
 *
 */
public class StringFormatValidator extends ValidatorBase implements ConstraintValidator<StringFormat, String> {

	private Type type;
	private int minLength;
	private int maxLength;
	private int length;

	@Override
	public void initialize(StringFormat value) {
		this.type = value.type();
		this.minLength = value.minLength();
		this.maxLength = value.maxLength();
		this.length = value.length();
		messageKey = value.type().getMessageKey();
	}

	@Override
	protected boolean checkFormat(String value, ConstraintValidatorContext context) {

		StringBuilder regexSb = new StringBuilder(this.type.getRegex());
		message = messageProp.getString(this.type.getMessageKey());

		// 桁数が指定された場合
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
		
		String regex = regexSb.toString();
		Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
		logger.debug("value= " + value + "regex= " + regex + " minLength= " + minLength + "maxLength= " + maxLength + "length= " + length);

		if (Pattern.compile(regex, Pattern.DOTALL).matcher(value).matches() ) {
			return true;
		}
		return false;		
	}

	@Override
	protected boolean checkParsing(String value, ConstraintValidatorContext context) {
		return true;
	}
}
