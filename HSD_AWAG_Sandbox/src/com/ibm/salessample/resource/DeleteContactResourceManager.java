/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.resource;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.resource.ResourceManagerBase;
import com.ibm.jp.awag2.common.validator.ValidationException;
import com.ibm.salessample.dto.DeleteContactInputDTO;
import com.ibm.salessample.resource.impl.DeleteContactResource;
 
/**
* コンタクト履歴削除 リソース・マネージャクラス（DeleteContact）
* コンタクト履歴削除
*/ 
@Path("/contact/")
public class DeleteContactResourceManager extends ResourceManagerBase {
	
	@Inject 
	private DeleteContactResource deletecontactResource;
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public Response execute(DeleteContactInputDTO input) throws ServiceDBException, ServiceAppException, ValidationException {
		
		// 単項目入力チェック
		super.validateBean(input);
		
		Response response = deletecontactResource.execute(input);

		return response.status(Status.NO_CONTENT).build();
	}
}
