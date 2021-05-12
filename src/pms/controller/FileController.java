package pms.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pms.support.BusinessException;

@Controller
@RequestMapping("/file")
public class FileController {
	public static final String HT_REAl_PATH = "F:\\Program Files\\java\\eclipse_workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp3\\webapps\\upload";
	//public static final String HT_REAl_PATH = "C:\\apache-tomcat-8.0.53\\webapps\\upload";

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public Map<String, Object> uploadImage(HttpServletRequest request, List<MultipartFile> files) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String rootPath = FileController.HT_REAl_PATH;
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String curDateTime = formatter.format(now);

		String year = curDateTime.substring(0, 4);
		String mouth = curDateTime.substring(5, 7);
		String day = curDateTime.substring(8, 10);

		if (files != null && files.size() > 0) {
			MultipartFile oneFile = files.get(0);

			if (oneFile != null && oneFile.getOriginalFilename() != null
					&& !oneFile.getOriginalFilename().trim().equals("")) {
				String forldpath = "/yy/" + year + "/" + mouth + "/" + day + "/";

				File forld = new File(rootPath + forldpath);
				if (!forld.exists()) {
					forld.mkdirs();
				}
				String fileName = oneFile.getOriginalFilename();
				String uuid = UUID.randomUUID().toString();
				String filepath = forldpath + uuid + fileName.substring(fileName.lastIndexOf("."), fileName.length());
				File localFile = new File(rootPath + filepath);

				try {
					oneFile.transferTo(localFile);
				} catch (IOException e) {
					throw new BusinessException("文件上传失败：保存文件时出现错误:" + e.getMessage());
				}

				result.put("filepath", filepath);
			}
		}

		result.put("message", "增加常用信息成功！");

		return result;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request, @RequestParam("file") List<MultipartFile> files)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		for (MultipartFile file : files) {
			if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().trim().equals("")) {
				String fileName = "upload" + file.getOriginalFilename();
				String path = "D:/" + fileName;
				File localFile = new File(path);
				file.transferTo(localFile);
			}
		}
		result.put("message", "上传成功");

		return result;
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("msie") String msie,
			@RequestParam("fileName") String fileName) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		// 如果修改tomcat server.xml 文件, 增加useBodyEncodingForURI="true"，就不用下面的代码了
		// fileName = URLDecoder.decode(new String(
		// fileName.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
		// 如果修改tomcat server.xml 文件, 增加useBodyEncodingForURI="true"，就不用上面的代码了

		if (msie != null && !msie.equals("") && msie.equals("true")) {
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
		} else {
			String type = request.getHeader("user-agent");
			if (type != null && type.indexOf("Edge") != -1) {
				headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
			} else {
				headers.setContentDispositionFormData("attachment",
						new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
			}
		}
		String path = HT_REAl_PATH + fileName;
		File downloadFile = new File(path);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downloadFile), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/datagridExportExcel", method = RequestMethod.POST)
	public ResponseEntity<byte[]> datagridExportExcel(HttpServletRequest request, @RequestParam("msie") String msie,
			@RequestParam("fileName") String fileName, @RequestParam("data") String data) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		if (fileName != null && !fileName.equals("")) {
			if (msie != null && msie.equals("true")) {
				headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
			} else {
				String type = request.getHeader("user-agent");
				if (type != null && type.indexOf("Edge") != -1) {
					headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
				} else {
					headers.setContentDispositionFormData("attachment",
							new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				}
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		sheet.setDefaultColumnWidth(18);
		Map<String, Map<String, Object>> mapMapData = null;
		if (data != null && !data.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			mapMapData = objectMapper.readValue(data, new TypeReference<Map<String, Map<String, Object>>>() {
			});
		}
		if (mapMapData != null) {
			int rowNum = 0;
			while (true) {
				if (mapMapData.containsKey("" + rowNum)) {
					if (mapMapData.get("" + rowNum) != null) {
						Row sheetRow = sheet.createRow(rowNum);
						Map<String, Object> map = mapMapData.get("" + rowNum);
						int columnNum = 0;
						while (true) {
							if (map.containsKey("" + columnNum)) {
								if (map.get("" + columnNum) != null) {
									sheetRow.createCell(columnNum).setCellValue(map.get("" + columnNum).toString());
								} else {
									sheetRow.createCell(columnNum).setCellValue("");
								}
								columnNum++;
							} else {
								break;
							}
						}
					}
					rowNum++;
				} else {
					break;
				}
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] resultByteArray = os.toByteArray();
		os.close();
		workbook.close();
		return new ResponseEntity<byte[]>(resultByteArray, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/datagridExportExcel2", method = RequestMethod.POST)
	public ResponseEntity<byte[]> datagridExportExcel2(HttpServletRequest request, @RequestParam("msie") String msie,
			@RequestParam("fileName") String fileName, @RequestParam("data") String data) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		if (fileName != null && !fileName.equals("")) {
			if (msie != null && msie.equals("true")) {
				headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
			} else {
				String type = request.getHeader("user-agent");
				if (type != null && type.indexOf("Edge") != -1) {
					headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
				} else {
					headers.setContentDispositionFormData("attachment",
							new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				}
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		sheet.setDefaultColumnWidth(18);
		CellRangeAddress region1 = new CellRangeAddress(0, 0, 1, 4);
		CellRangeAddress region2 = new CellRangeAddress(0, 0, 5, 8);
		CellRangeAddress region3 = new CellRangeAddress(0, 0, 9, 12);
		CellRangeAddress region4 = new CellRangeAddress(0, 0, 13, 16);
		CellRangeAddress region5 = new CellRangeAddress(0, 0, 17, 20);
		CellRangeAddress region6 = new CellRangeAddress(0, 0, 21, 24);
		CellRangeAddress region7 = new CellRangeAddress(0, 0, 25, 28);
		sheet.addMergedRegion(region1);
		sheet.addMergedRegion(region2);
		sheet.addMergedRegion(region3);
		sheet.addMergedRegion(region4);
		sheet.addMergedRegion(region5);
		sheet.addMergedRegion(region6);
		sheet.addMergedRegion(region7);
		Map<String, Map<String, Object>> mapMapData = null;
		if (data != null && !data.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			mapMapData = objectMapper.readValue(data, new TypeReference<Map<String, Map<String, Object>>>() {
			});
		}
		if (mapMapData != null) {
			int rowNum = 0;
			while (true) {
				if(rowNum == 0){
					if (mapMapData.containsKey("" + rowNum)) {
						if (mapMapData.get("" + rowNum) != null) {
							Row sheetRow = sheet.createRow(rowNum);
							Map<String, Object> map = mapMapData.get("" + rowNum);
							int columnNum = 0;
							while (true) {
								if (map.containsKey("" + columnNum)) {
									if(columnNum == 0){
										if (map.get("" + columnNum) != null) {
											sheetRow.createCell(columnNum).setCellValue(map.get("" + columnNum).toString());
										} else {
											sheetRow.createCell(columnNum).setCellValue("");
										}
										columnNum++;
									}else{
										if (map.get("" + columnNum) != null) {
											sheetRow.createCell(columnNum*4-3).setCellValue(map.get("" + columnNum).toString());
											sheetRow.createCell(columnNum*4-2).setCellValue("");
											sheetRow.createCell(columnNum*4-1).setCellValue("");
											sheetRow.createCell(columnNum*4).setCellValue("");
										} else {
											sheetRow.createCell(columnNum*4-3).setCellValue("");
											sheetRow.createCell(columnNum*4-2).setCellValue("");
											sheetRow.createCell(columnNum*4-1).setCellValue("");
											sheetRow.createCell(columnNum*4).setCellValue("");
										}
										columnNum++;
									}
									
								} else {
									break;
								}
							}
						}
						rowNum++;
					}
				}else{
					if (mapMapData.containsKey("" + rowNum)) {
						if (mapMapData.get("" + rowNum) != null) {
							Row sheetRow = sheet.createRow(rowNum);
							Map<String, Object> map = mapMapData.get("" + rowNum);
							int columnNum = 0;
							while (true) {
								if (map.containsKey("" + columnNum)) {
									if (map.get("" + columnNum) != null) {
										sheetRow.createCell(columnNum).setCellValue(map.get("" + columnNum).toString());
									} else {
										sheetRow.createCell(columnNum).setCellValue("");
									}
									columnNum++;
								} else {
									break;
								}
							}
						}
						rowNum++;
					} else {
						break;
					}
				}
				
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] resultByteArray = os.toByteArray();
		os.close();
		workbook.close();
		return new ResponseEntity<byte[]>(resultByteArray, headers, HttpStatus.OK);
	}
	

@RequestMapping(value = "/datagridExportExcel3", method = RequestMethod.POST)
	public ResponseEntity<byte[]> datagridExportExcel3(HttpServletRequest request, @RequestParam("msie") String msie,
			@RequestParam("fileName") String fileName, @RequestParam("data") String data) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		if (fileName != null && !fileName.equals("")) {
			if (msie != null && msie.equals("true")) {
				headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
			} else {
				String type = request.getHeader("user-agent");
				if (type != null && type.indexOf("Edge") != -1) {
					headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
				} else {
					headers.setContentDispositionFormData("attachment",
							new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				}
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		sheet.setDefaultColumnWidth(18);
		CellRangeAddress region1 = new CellRangeAddress(0, 0, 1, 6);
		CellRangeAddress region2 = new CellRangeAddress(0, 0, 7, 12);
		CellRangeAddress region3 = new CellRangeAddress(0, 0, 13, 18);
		sheet.addMergedRegion(region1);
		sheet.addMergedRegion(region2);
		sheet.addMergedRegion(region3);
		Map<String, Map<String, Object>> mapMapData = null;
		if (data != null && !data.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			mapMapData = objectMapper.readValue(data, new TypeReference<Map<String, Map<String, Object>>>() {
			});
		}
		if (mapMapData != null) {
			int rowNum = 0;
			while (true) {
				if(rowNum == 0){
					if (mapMapData.containsKey("" + rowNum)) {
						if (mapMapData.get("" + rowNum) != null) {
							Row sheetRow = sheet.createRow(rowNum);
							Map<String, Object> map = mapMapData.get("" + rowNum);
							int columnNum = 0;
							while (true) {
								if (map.containsKey("" + columnNum)) {
									if(columnNum == 0){
										if (map.get("" + columnNum) != null) {
											sheetRow.createCell(columnNum).setCellValue(map.get("" + columnNum).toString());
										} else {
											sheetRow.createCell(columnNum).setCellValue("");
										}
										columnNum++;
									}else{
										if (map.get("" + columnNum) != null) {
											sheetRow.createCell(columnNum*6-5).setCellValue(map.get("" + columnNum).toString());
											sheetRow.createCell(columnNum*6-4).setCellValue("");
											sheetRow.createCell(columnNum*6-3).setCellValue("");
											sheetRow.createCell(columnNum*6-2).setCellValue("");
											sheetRow.createCell(columnNum*6).setCellValue("");
										} else {
											sheetRow.createCell(columnNum*6-5).setCellValue("");
											sheetRow.createCell(columnNum*6-4).setCellValue("");
											sheetRow.createCell(columnNum*6-3).setCellValue("");
											sheetRow.createCell(columnNum*6-2).setCellValue("");
											sheetRow.createCell(columnNum*6).setCellValue("");
										}
										columnNum++;
									}
									
								} else {
									break;
								}
							}
						}
						rowNum++;
					}
				}else{
					if (mapMapData.containsKey("" + rowNum)) {
						if (mapMapData.get("" + rowNum) != null) {
							Row sheetRow = sheet.createRow(rowNum);
							Map<String, Object> map = mapMapData.get("" + rowNum);
							int columnNum = 0;
							while (true) {
								if (map.containsKey("" + columnNum)) {
									if (map.get("" + columnNum) != null) {
										sheetRow.createCell(columnNum).setCellValue(map.get("" + columnNum).toString());
									} else {
										sheetRow.createCell(columnNum).setCellValue("");
									}
									columnNum++;
								} else {
									break;
								}
							}
						}
						rowNum++;
					} else {
						break;
					}
				}
				
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] resultByteArray = os.toByteArray();
		os.close();
		workbook.close();
		return new ResponseEntity<byte[]>(resultByteArray, headers, HttpStatus.OK);
	}
	

}
