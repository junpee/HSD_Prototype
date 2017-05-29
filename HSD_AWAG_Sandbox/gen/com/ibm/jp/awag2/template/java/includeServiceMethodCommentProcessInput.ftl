	/**
	 * DBアクセス前の入力データ処理を行う。主にInputDTOからDAOParameterへの詰替処理を行う。
	 * @param ${apiDefinition.name?cap_first}InputDTO InputDTO
	 * @return persistメソッドへの持ち回りデータを格納したMapオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	 