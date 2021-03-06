/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.ibm.jp.awag2.common.dao.DAOBase.SelectWhereOperator;
import com.ibm.jp.awag2.common.dao.DAOParameter;
import com.ibm.jp.awag2.common.entity.JoinEntity;
import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceBase;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.salessample.dto.CTSearchCustomerInputDTO;
import com.ibm.salessample.dto.CTSearchCustomerOutputDTO;
import com.ibm.salessample.dto.CTSearchCustomerOutputDTOList;
import com.ibm.salessample.dao.impl.CUSTOMERCustomizeDAO;
import com.ibm.salessample.entity.CUSTOMER;
import com.ibm.salessample.entity.CUSTOMERPK;
import com.ibm.salessample.dto.CTSearchCustomerOutputDTOList;

/**
* コンタクトお客様検索 サービスクラス（CTSearchCustomer）
* コンタクトお客様検索
*/ 
@Stateless
public class CTSearchCustomerService extends ServiceBase {

	/** RESULTマップのキー（CUSTOMER） */
	protected static final String RESULT_MAP_KEY_CUSTOMER = "customerList";

	/** DAOパラメーターマップのキー（CUSTOMER） */
	protected static final String DAO_PARAM_MAP_KEY_CUSTOMER = "customer";


	/** DAOパラメーターマップのキー（CUSTOMERのJoinList） */
	protected static final String DAO_PARAM_MAP_KEY_CUSTOMER_JOIN = "joinEntityListForCUSTOMER";

	/** CUSTOMERテーブルDAO */
	@Inject
	protected CUSTOMERCustomizeDAO customerDao;
	
	/**
	 * DBアクセス前の入力データ処理を行う。主にInputDTOからDAOParameterへの詰替処理を行う。
	 * @param CTSearchCustomerInputDTO InputDTO
	 * @return persistメソッドへの持ち回りデータを格納したMapオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	protected Map<String, Object> processInput(CTSearchCustomerInputDTO ctsearchcustomerinputdto)  throws ServiceDBException, ServiceAppException {
		
		Map<String, Object> daoParameterMap = new HashMap<>();

		// InputDTOからDAOParameterへの詰替
		DAOParameter daoParamForCUSTOMER = (DAOParameter) Optional.ofNullable(daoParameterMap.get(DAO_PARAM_MAP_KEY_CUSTOMER)).orElse(new DAOParameter());

		if (daoParamForCUSTOMER.get("customerid") == null  && ctsearchcustomerinputdto.getCustomerid() != null) daoParamForCUSTOMER.set("customerid", ctsearchcustomerinputdto.getCustomerid());
		if (daoParamForCUSTOMER.get("nameen") == null  && ctsearchcustomerinputdto.getNameen() != null) daoParamForCUSTOMER.set("nameen", ctsearchcustomerinputdto.getNameen());
		if (daoParamForCUSTOMER.get("name") == null  && ctsearchcustomerinputdto.getName() != null) daoParamForCUSTOMER.set("name", ctsearchcustomerinputdto.getName());
		if (daoParamForCUSTOMER.get("inchargeid") == null  && ctsearchcustomerinputdto.getInchargeid() != null) daoParamForCUSTOMER.set("inchargeid", ctsearchcustomerinputdto.getInchargeid());
		daoParameterMap.put(DAO_PARAM_MAP_KEY_CUSTOMER, daoParamForCUSTOMER);

		// Join対象Entity設定

		// AND,OR検索条件
		daoParameterMap.put("_isInclusiveOr", ctsearchcustomerinputdto.isInclusiveOr());

		// 最大取得件数
		daoParameterMap.put("_maxResults", ctsearchcustomerinputdto.getMaxResults());
				
		return daoParameterMap;
	}
	
	/**
	 * DBアクセス処理を行う。
	 * @param daoParameterMap processInputメソッドからの持ち回りデータを格納したMapオブジェクト
	 * @return processOutputメソッドへの持ち回りデータを格納したMapオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	 	protected Map<String, Object> persist(Map<String, Object> daoParameterMap)  throws ServiceDBException, ServiceAppException  {
		
		// DAOパラメータの取得
		DAOParameter daoParamForCUSTOMER = (DAOParameter) daoParameterMap.get(DAO_PARAM_MAP_KEY_CUSTOMER);

		// Join対象Entityの取得

		// DAO呼び出し
		Map<String, Object> resultMap = new HashMap<>();
		
		List<CUSTOMER> customerList = customerDao.findList(daoParamForCUSTOMER, null, (boolean) daoParameterMap.get("_isInclusiveOr"), (Integer) daoParameterMap.get("_maxResults"));
		if (!customerList.isEmpty()) {
			resultMap.put(RESULT_MAP_KEY_CUSTOMER, customerList);
		}

		return resultMap;
	}
	
	/**
	 * DBアクセス後の出力データ処理を行う。主にDBアクセス結果からOutputDTOへの詰替処理を行う。
	 * @param resultMap persistメソッドからの持ち回りデータを格納したMapオブジェクト
	 * @return OutputDTO
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	 	protected CTSearchCustomerOutputDTO processOutput(Map<String, Object> resultMap)  throws ServiceDBException, ServiceAppException {

		if (resultMap.isEmpty()) {
			return null;
		}
		
		// DAO実行結果の取得
		List<CUSTOMER> customerList = (List<CUSTOMER>) resultMap.get(RESULT_MAP_KEY_CUSTOMER);

		// DAO実行結果からOutputDTOへの詰替

		CTSearchCustomerOutputDTO ctsearchcustomeroutputdtoTmp = new CTSearchCustomerOutputDTO();

		List<CTSearchCustomerOutputDTOList> ctsearchcustomeroutputdtolistTmpWrap = null;
		if (customerList != null && !customerList.isEmpty()) ctsearchcustomeroutputdtolistTmpWrap = new ArrayList<>();
		if (customerList != null && !customerList.isEmpty()) {
		for (CUSTOMER customer : customerList) {
			CTSearchCustomerOutputDTOList ctsearchcustomeroutputdtolistTmp = new CTSearchCustomerOutputDTOList();	
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setCustomerid(customer.getCUSTOMERID());
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setNameen(customer.getNAMEEN());
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setName(customer.getNAME());
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setInchargeid(customer.getINCHARGEID());
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setRank(customer.getRANK());
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setCustclass1(customer.getCUSTCLASS1());
			if (customer != null) ctsearchcustomeroutputdtolistTmp.setCheckbox(customer.getCHECKBOX());
			ctsearchcustomeroutputdtolistTmpWrap.add(ctsearchcustomeroutputdtolistTmp);
		}
		}
		ctsearchcustomeroutputdtoTmp.setList(ctsearchcustomeroutputdtolistTmpWrap);
		return ctsearchcustomeroutputdtoTmp;
	}}