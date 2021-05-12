package pms.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelImportHelper {

	private ExcelImportHelper() {
	}

	public static List<List<List<String>>> importExcel(MultipartFile file) throws Exception {
		if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().trim().equals("")) {
			Workbook workbook = null;
			InputStream inputStream = file.getInputStream();
			String importFileName = file.getOriginalFilename();
			if (importFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (importFileName.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				if( inputStream!=null){
					inputStream.close();
				}
				throw new BusinessException("请传入Excel导入文件正确的后缀名");
			}
			inputStream.close();
			// Excel数据List
			List<List<List<String>>> excelSheetList = new ArrayList<List<List<String>>>();
			// 遍历所有工作簿
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				List<List<String>> excelRowList = new ArrayList<List<String>>();
				excelSheetList.add(excelRowList);
				Sheet sheet = workbook.getSheetAt(i);
				if (sheet == null) {
					continue;
				}
				// 遍历所有行
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					List<String> excelCellList = new ArrayList<String>();
					excelRowList.add(excelCellList);
					Row row = sheet.getRow(j);
					if (row == null) {
						continue;
					}
					// 遍历所有列
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell == null) {
							excelCellList.add(null);
							continue;
						}
						String value = null;
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue().trim();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
								value = dateTimeFormat.format(cell.getDateCellValue());
							} else {
//								value = Double.toString(cell.getNumericCellValue());
//								String[] vauleArray = value.split("\\.");
//								if (vauleArray != null && vauleArray.length == 2) {
//									if (Long.valueOf(vauleArray[1]) == 0) {
//										value = vauleArray[0];
//									}
//								}
								BigDecimal tempValue = new BigDecimal(cell.getNumericCellValue());
								value = tempValue.toPlainString();
								
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
							CellValue cellValue = formulaEvaluator.evaluate(cell);
							if (cellValue != null) {
								switch (cellValue.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									value = cellValue.getStringValue().trim();
									break;
								case Cell.CELL_TYPE_NUMERIC:
									value = Double.toString(cellValue.getNumberValue());
									String[] vauleArray = value.split("\\.");
									if (vauleArray != null && vauleArray.length == 2) {
										if (Long.valueOf(vauleArray[1]) == 0) {
											value = vauleArray[0];
										}
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									value = String.valueOf(cellValue.getBooleanValue());
									break;
								case Cell.CELL_TYPE_FORMULA:
									break;
								case Cell.CELL_TYPE_BLANK:
									break;
								case Cell.CELL_TYPE_ERROR:
									break;
								default:
									break;
								}
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							break;
						default:
							break;
						}
						excelCellList.add(value);
					}
				}
			}
			workbook.close();
			return excelSheetList;
		} else {
			throw new BusinessException("请传入Excel导入文件");
		}
	}
	
	
	public static List<List<List<String>>> importExcel(File file) throws Exception {
		if (file != null && file.getName()!= null && !file.getName().trim().equals("")) {
			Workbook workbook = null;
			
			InputStream inputStream = new FileInputStream(file);
			InputStream inputStream1 = new FileInputStream(file);
			String importFileName = file.getName();
			if(importFileName.endsWith(".xls") || importFileName.endsWith(".xlsx")) {
				try {            
					workbook = new XSSFWorkbook(inputStream);        
				} catch (Exception ex) {            
					workbook = new HSSFWorkbook(inputStream1);        
				}
			}else {
				if( inputStream!=null){
					inputStream.close();
					inputStream1.close();
				}
				throw new BusinessException("请传入Excel导入文件正确的后缀名");
			}
			
			/*if (importFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (importFileName.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				if( inputStream!=null){
					inputStream.close();
				}
				throw new BusinessException("请传入Excel导入文件正确的后缀名");
			}*/
			inputStream.close();
			inputStream1.close();
			// Excel数据List
			List<List<List<String>>> excelSheetList = new ArrayList<List<List<String>>>();
			// 遍历所有工作簿
			
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				List<List<String>> excelRowList = new ArrayList<List<String>>();
				excelSheetList.add(excelRowList);
				Sheet sheet = workbook.getSheetAt(i);
				if (sheet == null || sheet.getLastRowNum()<1) {
					continue;
				}
				// 遍历所有行
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					List<String> excelCellList = new ArrayList<String>();
					Row row = sheet.getRow(j);
					if (row == null || row.getCell(2)==null) {
						continue;
					}
					excelRowList.add(excelCellList);
					// 遍历所有列
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell == null) {
							excelCellList.add(null);
							continue;
						}
						String value = null;
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue().trim();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
								value = dateTimeFormat.format(cell.getDateCellValue());
							} else {
//								value = Double.toString(cell.getNumericCellValue());
//								String[] vauleArray = value.split("\\.");
//								if (vauleArray != null && vauleArray.length == 2) {
//									if (Long.valueOf(vauleArray[1]) == 0) {
//										value = vauleArray[0];
//									}
//								}
								BigDecimal tempValue = new BigDecimal(cell.getNumericCellValue());
								value = tempValue.toPlainString();
								
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
							CellValue cellValue = formulaEvaluator.evaluate(cell);
							if (cellValue != null) {
								switch (cellValue.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									value = cellValue.getStringValue().trim();
									break;
								case Cell.CELL_TYPE_NUMERIC:
									value = Double.toString(cellValue.getNumberValue());
									String[] vauleArray = value.split("\\.");
									if (vauleArray != null && vauleArray.length == 2) {
										if (Long.valueOf(vauleArray[1]) == 0) {
											value = vauleArray[0];
										}
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									value = String.valueOf(cellValue.getBooleanValue());
									break;
								case Cell.CELL_TYPE_FORMULA:
									break;
								case Cell.CELL_TYPE_BLANK:
									break;
								case Cell.CELL_TYPE_ERROR:
									break;
								default:
									break;
								}
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							break;
						default:
							break;
						}
						excelCellList.add(value);
					}
				}
			}
			workbook.close();
			return excelSheetList;
		} else {
			throw new BusinessException("Excel计划中存在空数据");
		}
	}
	
	public static List<List<List<String>>> importExceltoString(File file) throws Exception {
		if (file != null && file.getName()!= null && !file.getName().trim().equals("")) {
			Workbook workbook = null;
			
			InputStream inputStream = new FileInputStream(file);
			String importFileName = file.getName();
			if (importFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (importFileName.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				if( inputStream!=null){
					inputStream.close();
				}
				throw new BusinessException("请传入Excel导入文件正确的后缀名");
			}
			inputStream.close();
			// Excel数据List
			List<List<List<String>>> excelSheetList = new ArrayList<List<List<String>>>();
			// 遍历所有工作簿
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				List<List<String>> excelRowList = new ArrayList<List<String>>();
				excelSheetList.add(excelRowList);
				Sheet sheet = workbook.getSheetAt(i);
				if (sheet == null) {
					continue;
				}
				// 遍历所有行
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					List<String> excelCellList = new ArrayList<String>();
					excelRowList.add(excelCellList);
					Row row = sheet.getRow(j);
					if (row == null) {
						continue;
					}
					// 遍历所有列
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell == null) {
							excelCellList.add(null);
							continue;
						}
						String value = null;
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue().trim();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy年MM月dd日");
								value = dateTimeFormat.format(cell.getDateCellValue());
							} else {
//								value = Double.toString(cell.getNumericCellValue());
//								String[] vauleArray = value.split("\\.");
//								if (vauleArray != null && vauleArray.length == 2) {
//									if (Long.valueOf(vauleArray[1]) == 0) {
//										value = vauleArray[0];
//									}
//								}
								BigDecimal tempValue = new BigDecimal(cell.getNumericCellValue());
								value = tempValue.toPlainString();
								
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
							CellValue cellValue = formulaEvaluator.evaluate(cell);
							if (cellValue != null) {
								switch (cellValue.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									value = cellValue.getStringValue().trim();
									break;
								case Cell.CELL_TYPE_NUMERIC:
									value = Double.toString(cellValue.getNumberValue());
									String[] vauleArray = value.split("\\.");
									if (vauleArray != null && vauleArray.length == 2) {
										if (Long.valueOf(vauleArray[1]) == 0) {
											value = vauleArray[0];
										}
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									value = String.valueOf(cellValue.getBooleanValue());
									break;
								case Cell.CELL_TYPE_FORMULA:
									break;
								case Cell.CELL_TYPE_BLANK:
									break;
								case Cell.CELL_TYPE_ERROR:
									break;
								default:
									break;
								}
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							break;
						default:
							break;
						}
						excelCellList.add(value);
					}
				}
			}
			workbook.close();
			return excelSheetList;
		} else {
			throw new BusinessException("请传入Excel导入文件");
		}
	}
	

}
