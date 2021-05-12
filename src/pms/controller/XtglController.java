package pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pms.entity.XtDm;
import pms.entity.XtGnxx;
import pms.model.LoginInfo;
import pms.service.XtglService;

@Controller
@RequestMapping("/xtgl")
public class XtglController {

	@Autowired
	private XtglService xtglService;

	@RequestMapping(value = "/queryDjmkByCzy", method = RequestMethod.POST)
	@ResponseBody
	public List<XtGnxx> queryGnByCzy(HttpServletRequest request) throws Exception {
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
		return xtglService.queryDjmkByCzy(loginInfo);
	}
	
	@RequestMapping(value = "/queryGnByCzy", method = RequestMethod.POST)
	@ResponseBody
	public List<XtGnxx> queryGnByCzy(HttpServletRequest request, String djmkgnbh) throws Exception {
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
		return xtglService.queryGnByCzy(loginInfo.getCzybh(), djmkgnbh);
	}


	@RequestMapping(value = "/queryAllDm", method = RequestMethod.POST)
	@ResponseBody
	public List<XtDm> queryAllDm() throws Exception {
		return xtglService.queryAllDm();
	}

	
}
