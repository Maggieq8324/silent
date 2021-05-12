package pms.view;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExcelView extends AbstractXlsView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (model.get("fileName") != null && !model.get("fileName").toString().equals("")) {
			String fileName = model.get("fileName").toString();
			if (model.get("msie") != null && model.get("msie").toString().equals("true")) {
				response.setHeader("Content-Disposition",
						"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			} else {
				String type = request.getHeader("user-agent");
				if (type != null && type.indexOf("Edge") != -1) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				} else {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				}
			}
		}
		Sheet sheet = workbook.createSheet("Sheet1");
		sheet.setDefaultColumnWidth(18);
		Map<String, Map<String, Object>> mapMapData = null;
		if (model.get("data") != null && !model.get("data").toString().equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			mapMapData = objectMapper.readValue(model.get("data").toString(),
					new TypeReference<Map<String, Map<String, Object>>>() {
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
	}
}
