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
import com.ibm.salessample.dto.GetCustomerInputDTO;
import com.ibm.salessample.dto.GetCustomerOutputDTO;
import com.ibm.salessample.dao.impl.CUSTOMERCustomizeDAO;
import com.ibm.salessample.entity.CUSTOMER;
import com.ibm.salessample.entity.CUSTOMERPK;

/**
* お客様取得 サービスクラス（GetCustomer）
* お客様取得
*/ 
@Stateless
public class GetCustomerService extends ServiceBase {

	/** RESULTマップのキー（CUSTOMER） */
	protected static final String RESULT_MAP_KEY_CUSTOMER = "customer";

	/** DAOパラメーターマップのキー（CUSTOMER） */
	protected static final String DAO_PARAM_MAP_KEY_CUSTOMER = "customer";

	/** DAOパラメーターマップのキー（CUSTOMERのPK） */
	protected static final String DAO_PARAM_MAP_KEY_CUSTOMER_PK = "customerPk";

	/** DAOパラメーターマップのキー（CUSTOMERのJoinList） */
	protected static final String DAO_PARAM_MAP_KEY_CUSTOMER_JOIN = "joinEntityListForCUSTOMER";

	/** CUSTOMERテーブルDAO */
	@Inject
	protected CUSTOMERCustomizeDAO customerDao;
	
	/**
	 * DBアクセス前の入力データ処理を行う。主にInputDTOからDAOParameterへの詰替処理を行う。
	 * @param GetCustomerInputDTO InputDTO
	 * @return persistメソッドへの持ち回りデータを格納したMapオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	protected Map<String, Object> processInput(GetCustomerInputDTO getcustomerinputdto)  throws ServiceDBException, ServiceAppException {
																		
		Map<String, Object> daoParameterMap = new HashMap<>();

		// InputDTOからPKへの詰替		
		CUSTOMERPK customerPk = new CUSTOMERPK();

		customerPk.setCUSTOMERID(getcustomerinputdto.getCustomerid());
		
		daoParameterMap.put(DAO_PARAM_MAP_KEY_CUSTOMER_PK, customerPk);		

		// Join対象Entity設定

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
		CUSTOMERPK customerPk = (CUSTOMERPK) daoParameterMap.get(DAO_PARAM_MAP_KEY_CUSTOMER_PK);

		// Join対象Entityの取得

		// DAO呼び出し
		Map<String, Object> resultMap = new HashMap<>();

		Optional<CUSTOMER> customer = customerDao.find(customerPk, null);
		customer.ifPresent(value -> resultMap.put(RESULT_MAP_KEY_CUSTOMER, customer.get()));
		
		return resultMap;
	}
	
	/**
	 * DBアクセス後の出力データ処理を行う。主にDBアクセス結果からOutputDTOへの詰替処理を行う。
	 * @param resultMap persistメソッドからの持ち回りデータを格納したMapオブジェクト
	 * @return OutputDTO
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	 	protected GetCustomerOutputDTO processOutput(Map<String, Object> resultMap)  throws ServiceDBException, ServiceAppException {

		if (resultMap.isEmpty()) {
			return null;
		}
		
		// DAO実行結果の取得
		CUSTOMER customer = (CUSTOMER) resultMap.get(RESULT_MAP_KEY_CUSTOMER);
				
		// DAO実行結果からOutputDTOへの詰替

		GetCustomerOutputDTO getcustomeroutputdtoTmp = new GetCustomerOutputDTO();
		if (customer != null) getcustomeroutputdtoTmp.setCustomerid(customer.getCUSTOMERID());
		if (customer != null) getcustomeroutputdtoTmp.setNameen(customer.getNAMEEN());
		if (customer != null) getcustomeroutputdtoTmp.setName(customer.getNAME());
		if (customer != null) getcustomeroutputdtoTmp.setInchargeid(customer.getINCHARGEID());
		if (customer != null) getcustomeroutputdtoTmp.setRegistereddate(customer.getREGISTEREDDATE());
		if (customer != null) getcustomeroutputdtoTmp.setHiragana(customer.getHIRAGANA());
		if (customer != null) getcustomeroutputdtoTmp.setKatakana(customer.getKATAKANA());
		if (customer != null) getcustomeroutputdtoTmp.setDescription(customer.getDESCRIPTION());
		if (customer != null) getcustomeroutputdtoTmp.setIsin(customer.getISIN());
		if (customer != null) getcustomeroutputdtoTmp.setHoujinno(customer.getHOUJINNO());
		if (customer != null) getcustomeroutputdtoTmp.setRank(customer.getRANK());
		if (customer != null) getcustomeroutputdtoTmp.setCustclass1(customer.getCUSTCLASS1());
		if (customer != null) getcustomeroutputdtoTmp.setCustclass2(customer.getCUSTCLASS2());
		if (customer != null) getcustomeroutputdtoTmp.setCheckbox(customer.getCHECKBOX());
		if (customer != null) getcustomeroutputdtoTmp.setTime(customer.getTIME());

		return getcustomeroutputdtoTmp;
	}}