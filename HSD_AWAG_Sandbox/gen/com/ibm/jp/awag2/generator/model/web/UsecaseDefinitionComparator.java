/**
 * 
 */
package com.ibm.jp.awag2.generator.model.web;

import java.util.Comparator;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.comparePriority;

/**
 * を行うクラス。
 *
 */
public class UsecaseDefinitionComparator implements Comparator<UsecaseDefinition> {

	/* (非 Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(UsecaseDefinition def1, UsecaseDefinition def2) {
		return comparePriority(def1.getDisplayPriority(), def2.getDisplayPriority());
	}
}
