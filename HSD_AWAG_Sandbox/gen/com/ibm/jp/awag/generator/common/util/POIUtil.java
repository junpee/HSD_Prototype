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
package com.ibm.jp.awag.generator.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.*;

public class POIUtil {
	public static String getStringValue(Cell cell) {

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return replaceNullStringwithNull(cell.getStringCellValue());
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(cell.getNumericCellValue());
			case Cell.CELL_TYPE_FORMULA:
				return getEvaluatedCellValue(cell);
			case Cell.CELL_TYPE_BLANK:
				return null;
			default:
				throw new RuntimeException("文字列への変換に失敗。 [Sheet]" + cell.getSheet().getSheetName() + "[Address]"+ cell.getAddress(), null);
		}
	}


	public static Double getNumberValue(Cell cell) {
		
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				try {
					return Double.valueOf(cell.getStringCellValue());
				} catch (NumberFormatException e) {
					throw new RuntimeException("数値への変換に失敗。 [Sheet]" + cell.getSheet().getSheetName() + "[Address]"+ cell.getAddress(), e);
				}
			case Cell.CELL_TYPE_NUMERIC:
				return Double.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_FORMULA:
				String cellValue = getEvaluatedCellValue(cell);
				if(cellValue == null){
					return null;
				}
				return Double.valueOf(cellValue);
			case Cell.CELL_TYPE_BLANK:
				return null;
			default:
				throw new RuntimeException("数値への変換に失敗。 [Sheet]" + cell.getSheet().getSheetName() + "[Address]"+ cell.getAddress(), null);
		}
	}
	
	public static String getEvaluatedCellValue(Cell cell) {

		if(cell == null){
			return null;
		}
		
		Workbook book = cell.getSheet().getWorkbook();
		CreationHelper helper = book.getCreationHelper();
		FormulaEvaluator evaluator = helper.createFormulaEvaluator();
		CellValue value = evaluator.evaluate(cell);
		
		if (value == null) {
			return null;
		}
		
		switch (value.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return replaceNullStringwithNull(value.getStringValue());
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(value.getNumberValue());
			case Cell.CELL_TYPE_BLANK:
				return null;
			default:
				throw new RuntimeException("値の取得に失敗。 [Book]" + cell.getSheet().getSheetName() + "[Address]"+ cell.getAddress(), null);
		}
	}
	
	/**
	 * 指定したアドレス以降の行を走査し、データ件数を取得する。
	 * @param sheet 対象のシート
	 * @param address 走査を開始するセルの英数字アドレス
	 * @return
	 */
	public static int countColumnNum(Sheet sheet, String address) {

		Cell firstCell = getCell(sheet, address);
		int length = 1;

		while (length < 65536) {

			try {
				String nextCellValue = getStringValue(sheet.getRow(firstCell.getRowIndex() + length).getCell(firstCell.getColumnIndex()));
				if (nextCellValue != null && !nextCellValue.isEmpty()) {
					length++;
				} else {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}

		return length;
	}
	
	/**
	 * Excelセルの英数字アドレスを指定してCellオブジェクトを取得する。
	 * @param sheet 対象のシートオブジェクト
	 * @param address Excelセルの英数字アドレス
	 * @return Cellオブジェクト
	 */
	public static Cell getCell(Sheet sheet, String address) {
		
		CellReference reference = new CellReference(address);
		
		Cell cell = null;
		Boolean isA1 = false;
		
		Row row = sheet.getRow(reference.getRow());
		
		if (row == null) {
			
			// A1セル判定
			if(address.equals("A1")){
				isA1 = true;
			}else{
				throw new RuntimeException("対象のアドレスが無効。 [Sheet]" + sheet.getSheetName() + " [Address]" + address);				
			}
		}

		if(!isA1){
			cell = row.getCell(reference.getCol());
		}
		
		return cell;
	}
}
