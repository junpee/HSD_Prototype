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
import com.ibm.salessample.dto.GetApproveListInputDTO;
import com.ibm.salessample.dto.GetApproveListOutputDTO;
import com.ibm.salessample.dto.GetApproveListOutputDTOList;
import com.ibm.salessample.dto.GetApproveListOutputDTOStaff;
import com.ibm.salessample.dto.GetApproveListOutputDTOApprovelog;
import com.ibm.salessample.dao.impl.CUSTOMERCustomizeDAO;
import com.ibm.salessample.entity.CUSTOMER;
import com.ibm.salessample.entity.CUSTOMERPK;
import com.ibm.salessample.dao.impl.STAFFCustomizeDAO;
import com.ibm.salessample.entity.STAFF;
import com.ibm.salessample.entity.STAFFPK;
import com.ibm.salessample.dao.impl.APPROVELOGCustomizeDAO;
import com.ibm.salessample.entity.APPROVELOG;
import com.ibm.salessample.entity.APPROVELOGPK;
import com.ibm.salessample.dto.GetApproveListOutputDTOList;

/**
* 承認対象一覧取得 サービスクラス（GetApproveList）
* 承認対象一覧取得
*/ 
@Stateless
public class GetApproveListService extends ServiceBase {

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
	 * @param GetApproveListInputDTO InputDTO
	 * @return persistメソッドへの持ち回りデータを格納したMapオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	protected Map<String, Object> processInput(GetApproveListInputDTO getapprovelistinputdto)  throws ServiceDBException, ServiceAppException {
		
		Map<String, Object> daoParameterMap = new HashMap<>();

		// InputDTOからDAOParameterへの詰替
		DAOParameter daoParamForCUSTOMER = (DAOParameter) Optional.ofNullable(daoParameterMap.get(DAO_PARAM_MAP_KEY_CUSTOMER)).orElse(new DAOParameter());

		if (daoParamForCUSTOMER.get("customerid") == null  && getapprovelistinputdto.getCustomerid() != null) daoParamForCUSTOMER.set("customerid", getapprovelistinputdto.getCustomerid());
		if (daoParamForCUSTOMER.get("checkbox") == null  && getapprovelistinputdto.getCheckbox() != null) daoParamForCUSTOMER.set("checkbox", SelectWhereOperator.NOTEQUALS
, getapprovelistinputdto.getCheckbox());
		daoParameterMap.put(DAO_PARAM_MAP_KEY_CUSTOMER, daoParamForCUSTOMER);

		// Join対象Entity設定
		List<JoinEntity> joinEntityListForCUSTOMER = new ArrayList<>();
		JoinEntity joinEntityForSTAFF = new JoinEntity("STAFF");
		joinEntityListForCUSTOMER.add(joinEntityForSTAFF);
		JoinEntity joinEntityForAPPROVELOG = new JoinEntity("APPROVELOG");
		joinEntityListForCUSTOMER.add(joinEntityForAPPROVELOG);
		daoParameterMap.put(DAO_PARAM_MAP_KEY_CUSTOMER_JOIN, joinEntityListForCUSTOMER);

		// AND,OR検索条件
		daoParameterMap.put("_isInclusiveOr", getapprovelistinputdto.isInclusiveOr());

		// 最大取得件数
		daoParameterMap.put("_maxResults", getapprovelistinputdto.getMaxResults());
				
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
		List<JoinEntity> joinEntityListForCUSTOMER = (List<JoinEntity>) daoParameterMap.get(DAO_PARAM_MAP_KEY_CUSTOMER_JOIN);

		// DAO呼び出し
		Map<String, Object> resultMap = new HashMap<>();
		
		List<CUSTOMER> customerList = customerDao.findList(daoParamForCUSTOMER, joinEntityListForCUSTOMER, (boolean) daoParameterMap.get("_isInclusiveOr"), (Integer) daoParameterMap.get("_maxResults"));
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
	 	protected GetApproveListOutputDTO processOutput(Map<String, Object> resultMap)  throws ServiceDBException, ServiceAppException {

		if (resultMap.isEmpty()) {
			return null;
		}
		
		// DAO実行結果の取得
		List<CUSTOMER> customerList = (List<CUSTOMER>) resultMap.get(RESULT_MAP_KEY_CUSTOMER);

		// DAO実行結果からOutputDTOへの詰替

		GetApproveListOutputDTO getapprovelistoutputdtoTmp = new GetApproveListOutputDTO();

		List<GetApproveListOutputDTOList> getapprovelistoutputdtolistTmpWrap = null;
		if (customerList != null && !customerList.isEmpty()) getapprovelistoutputdtolistTmpWrap = new ArrayList<>();
		if (customerList != null && !customerList.isEmpty()) {
		for (CUSTOMER customer : customerList) {
			GetApproveListOutputDTOList getapprovelistoutputdtolistTmp = new GetApproveListOutputDTOList();	
			if (customer != null) getapprovelistoutputdtolistTmp.setCustomerid(customer.getCUSTOMERID());
			if (customer != null) getapprovelistoutputdtolistTmp.setNameen(customer.getNAMEEN());
			if (customer != null) getapprovelistoutputdtolistTmp.setName(customer.getNAME());
			if (customer != null) getapprovelistoutputdtolistTmp.setInchargeid(customer.getINCHARGEID());
			if (customer != null) getapprovelistoutputdtolistTmp.setCheckbox(customer.getCHECKBOX());
			if (customer != null) getapprovelistoutputdtolistTmp.setTime(customer.getTIME());

			GetApproveListOutputDTOStaff getapprovelistoutputdtostaffTmpWrap = null;
			if (customer != null && customer.getSTAFF() != null) getapprovelistoutputdtostaffTmpWrap = new GetApproveListOutputDTOStaff();
			STAFF staff = null;
			if (customer != null && customer.getSTAFF() != null) staff = customer.getSTAFF();
				if (staff != null) getapprovelistoutputdtostaffTmpWrap.setEmployeeno(staff.getEMPLOYEENO());
				if (staff != null) getapprovelistoutputdtostaffTmpWrap.setName(staff.getNAME());
			getapprovelistoutputdtolistTmp.setStaff(getapprovelistoutputdtostaffTmpWrap);

			GetApproveListOutputDTOApprovelog getapprovelistoutputdtoapprovelogTmpWrap = null;
			if (customer != null && customer.getAPPROVELOG() != null) getapprovelistoutputdtoapprovelogTmpWrap = new GetApproveListOutputDTOApprovelog();
			APPROVELOG approvelog = null;
			if (customer != null && customer.getAPPROVELOG() != null) approvelog = customer.getAPPROVELOG();
				if (approvelog != null) getapprovelistoutputdtoapprovelogTmpWrap.setCustomerid(approvelog.getCUSTOMERID());
				if (approvelog != null) getapprovelistoutputdtoapprovelogTmpWrap.setComment(approvelog.getCOMMENT());
				if (approvelog != null) getapprovelistoutputdtoapprovelogTmpWrap.setRank(approvelog.getRANK());
				if (approvelog != null) getapprovelistoutputdtoapprovelogTmpWrap.setStatus(approvelog.getSTATUS());
				if (approvelog != null) getapprovelistoutputdtoapprovelogTmpWrap.setFlag(approvelog.getFLAG());
				if (approvelog != null) getapprovelistoutputdtoapprovelogTmpWrap.setVersion(approvelog.getVERSION());
			getapprovelistoutputdtolistTmp.setApprovelog(getapprovelistoutputdtoapprovelogTmpWrap);
			getapprovelistoutputdtolistTmpWrap.add(getapprovelistoutputdtolistTmp);
		}
		}
		getapprovelistoutputdtoTmp.setList(getapprovelistoutputdtolistTmpWrap);
		return getapprovelistoutputdtoTmp;
	}}