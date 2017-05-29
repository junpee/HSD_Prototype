/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import com.ibm.jp.awag2.common.entity.DisplayName;
import com.ibm.jp.awag2.common.validator.DateTime;
import com.ibm.jp.awag2.common.validator.Number;
import com.ibm.jp.awag2.common.validator.StringFormat;
import com.ibm.jp.awag2.common.dto.DTOBase;

/**
 * GetCustomerのInput DTOクラス。
 *
 */
public class GetCustomerInputDTO  extends DTOBase {

	/** お客様番号(customerid) */
	@PathParam(value = "customerid")
	@NotNull
	@StringFormat(type = StringFormat.Type.HALF_NUM, minLength = 8, maxLength = 8)			
	@DisplayName(name = "お客様番号")
	private String customerid;
	

	/**
	 * お客様番号を取得する。
	 * @return customerid
	 */
	public String getCustomerid() {
		return customerid;
	}

	/**
	 * お客様番号を設定する。
	 * @param customerid
	 */
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
}