package pms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.platform.dataaccess.support.PaginationCondition;
import com.platform.dataaccess.support.PaginationResult;
import pms.entity.QsyrFjxx;
import pms.model.LoginInfo;
import pms.service.QsyrService;
import pms.support.BusinessException;

/**
 * @author velocity
 * 秋水伊人信息管理控制器
 *
 */

@Controller
@RequestMapping("/qsyr")
public class QsyrController {
	@Autowired
	private QsyrService qsyrService;
	
	@RequestMapping(value="/QueryQsyrPhotos" , method = RequestMethod.POST)
	@ResponseBody
	public PaginationResult<QsyrFjxx> QueryQsyrPhotos(PaginationCondition p) throws BusinessException{
		return qsyrService.QueryQsyrPhotos(p);
	}
	
	@RequestMapping(value="/upload" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> upload(HttpServletRequest request, QsyrFjxx entity,MultipartFile file) throws BusinessException{
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
		return qsyrService.upload(entity, file, loginInfo);
	}
	
	@RequestMapping(value="/QueryZhifouPhotosFor" , method = RequestMethod.POST)
	@ResponseBody
	public List<QsyrFjxx> QueryZhifouPhotosFor(Integer fjlx) throws BusinessException{
		return qsyrService.QueryZhifouPhotosFor(fjlx);
	}

}
