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

import com.ibm.jp.awag.rest.common.logic.ErrorContainer;
import com.ibm.jp.awag.rest.common.logic.ExceptionBase;

/**
 * 入力チェックエラーを示す例外クラス。
 *
 */
public class ValidationException extends ExceptionBase {
	
	/**
	 * エラー情報コンテナを指定してインスタンスを初期化する。
	 * @param ec エラー情報コンテナ
	 */
	public ValidationException(ErrorContainer ec) {
		super(ec);
	}
}
