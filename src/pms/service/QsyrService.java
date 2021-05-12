package pms.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.platform.dataaccess.JdbcManager;
import com.platform.dataaccess.support.PaginationCondition;
import com.platform.dataaccess.support.PaginationResult;
import pms.controller.FileController;
import pms.entity.QsyrFjxx;
import pms.model.LoginInfo;
import pms.support.BusinessException;

/**
 * @author velocity
 * 秋水伊人信息管理服务
 *
 */

@Service
public class QsyrService {
	@Autowired
	private JdbcManager jdbcManager;
	
	public PaginationResult<QsyrFjxx> QueryQsyrPhotos(PaginationCondition p) throws BusinessException{
		return jdbcManager.parameterizedQuery("select * from qsyr_fjxx where fjlx = 0 ",p).getPaginationEntities(QsyrFjxx.class);
	}
	
	public Map<String,String> upload(QsyrFjxx entity,MultipartFile file, LoginInfo loginInfo) throws BusinessException{
		Map<String,String> message = new HashMap<String,String>();
		String rootPath = FileController.HT_REAl_PATH;
		//File forld = new File(rootPath + "\\qsyr\\");
		if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().trim().equals("")) {
			List<QsyrFjxx> qsyrList = jdbcManager.parameterizedQuery("select * from qsyr_fjxx where fjlx = 0 ").getEntityList(QsyrFjxx.class);
			int length = qsyrList.size()+1;
			String fileName = file.getOriginalFilename();
			String fjhzm = fileName.substring(fileName.lastIndexOf("."), fileName.length());

			//String fjmc = fileName.substring(0, fileName.lastIndexOf("."));
			String filepath = "\\qsyr\\" + "img" + length + fjhzm;
			File localFile = new File(rootPath + filepath);
			
			try {
				file.transferTo(localFile);
				message.put("message", "上传成功");
			} catch (IOException e) {
				//return "文件上传失败：保存文件时出现错误:" + e.getMessage();
				e.printStackTrace();
				throw new BusinessException("文件上传失败：保存文件时出现错误:" + e.getMessage());
			}
			
			QsyrFjxx qsyrfjxx = new QsyrFjxx();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date now = jdbcManager.getCurrentDateTime();
			String scrq = formatter.format(now);
			
			String fjbh = jdbcManager.getNextStringValue("qsyr_fjxx", "fjbh");
			qsyrfjxx.setFjbh(fjbh);
			qsyrfjxx.setScrq(scrq);
			qsyrfjxx.setFjmc("img"+length);
			qsyrfjxx.setFjml(rootPath);
			qsyrfjxx.setFjlj(filepath);
			qsyrfjxx.setFjhzm(fjhzm);
			qsyrfjxx.setWzsm(entity.getWzsm());
			qsyrfjxx.setFjlx(0);
			qsyrfjxx.setXssxh(length);
			qsyrfjxx.setTjrbh(loginInfo.getCzybh());
			qsyrfjxx.setTjrmc(loginInfo.getCzymc());
			qsyrfjxx.setTjsj(now);
			jdbcManager.entityInsert(qsyrfjxx);
			message.put("message", "上传成功!");
		}
		
		return message;
	}
	
	
	public List<QsyrFjxx> QueryZhifouPhotosFor(Integer fjlx) throws BusinessException{
		return jdbcManager.parameterizedQuery("select * from qsyr_fjxx where fjlx = ? order by xssxh asc ")
				.setParameter(fjlx)
				.getEntityList(QsyrFjxx.class);
	}

}
