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
package com.ibm.jp.awag.generator.model;
import java.util.Comparator;

/**
 * リソース定義シートの表示順序をソートするためのComparator。
 *
 */
public class ColumnDefinitionComparator implements Comparator<ColumnDefinition> {

	@Override
	public int compare(ColumnDefinition def1, ColumnDefinition def2) {
		int order1 = def1.getDisplayOrder();
		int order2 = def2.getDisplayOrder();

		if (order1 > order2) {
			return 1;
		} else if (order1 == order2) {
			return 0;
		} else {
			return -1;
		}
	}

}