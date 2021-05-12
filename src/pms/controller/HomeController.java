package pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import pms.model.LoginInfo;
import pms.service.HomeService;
import pms.support.Aes;
import pms.support.BusinessException;
import pms.view.ExcelView;


@Controller
@SessionAttributes({ "loginInfo", "yhxx" })
public class HomeController {

	@Autowired
	private HomeService homeService;

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	@Transactional(rollbackFor = Throwable.class,noRollbackFor = BusinessException.class, readOnly = false)
	public String home(HttpServletRequest request, Model model, 
			@RequestParam("dlzh") String dlzh,
			@RequestParam("dlmm") String dlmm
			,@RequestParam("captcha") String captcha) throws Exception {
		
		Aes aes = new Aes();
		dlmm = aes.aesDecrypt(dlmm);
		
		return homeService.login(request, model, dlzh, dlmm, captcha);
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public String home() throws Exception {
		return "relogin";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Throwable.class,readOnly = false)
	public Map<String,Object> register(HttpServletRequest request, Model model, 
			@RequestParam("dlzh") String dlzh,
			@RequestParam("czymc") String czymc,
			@RequestParam("dlmm") String dlmm,
			@RequestParam("qrmm") String qrmm,
			@RequestParam("tjm") String tjm) throws Exception {
		
		Aes aes = new Aes();
		dlmm = aes.aesDecrypt(dlmm);
		qrmm = aes.aesDecrypt(qrmm);
		Map<String,Object> result = homeService.register(request, model, dlzh, czymc, dlmm, qrmm, tjm);
		return result;
	}
	
	
	/*@RequestMapping(value = "/weHome")
	@Transactional(rollbackFor = Throwable.class,noRollbackFor = BusinessException.class, readOnly = false)
	public String weHome(HttpServletRequest request, Model model, @RequestParam("dlzh") String dlzh,
			@RequestParam("dlmm") String dlmm,@RequestParam("captcha") String captcha) throws Exception {
			dlmm = EncryptionHelper.encrypt(dlmm);
			return homeService.welogin(request, model, dlzh, dlmm, captcha);
	}
	
	@RequestMapping(value = "/weuiHome", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public Map<String, Object> weuiHome(HttpServletRequest request, Model model, String dlzh,
			String dlmm, String captcha,String clientid,String token,Integer zdbs) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		LoginInfo loginresult = homeService.mui_login(request, model, dlzh, dlmm, captcha,clientid,token,zdbs);
		
		if (loginresult != null){
			result.put("loginInfo", loginresult);
			result.put("message", "系统登录成功！");
		}
		
		return result;
	}*/

	@RequestMapping(value = "/relogin", method = RequestMethod.GET)
	public String relogin() throws Exception {
		return "relogin";
	}
	
	@RequestMapping(value = "/welogin", method = RequestMethod.GET)
	public String welogin() throws Exception {
		return "welogin";
	}
	
	@RequestMapping(value = "/vitae", method = RequestMethod.GET)
	public String vitae() {
		return "vitae";
	}

	@RequestMapping(value = "/muiRelogin")
	@ResponseBody
	public Map<String, Object> muiRelogin(Model model) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		//Map<String, Object> modelMap = model.asMap();
		result.put("message", "系统登录成功！");
		
		return result;
	}
	
	//muiRelogin

	@RequestMapping(value = "/navigation", method = RequestMethod.GET)
	public String navigation(HttpServletRequest request, @RequestParam("link") String link, Model model) throws Exception {
		try {
			if (link == null || link.trim().equals("") || link.trim().equals("#")) {
				model.addAttribute("error", "系统功能未实现");
				return "error";
			}
			if (request.getSession() != null && request.getSession().getAttribute("loginInfo") != null) {
				LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
				List<String> hiddenYsidList = homeService.getHiddenYsidList(loginInfo, link);
				StringBuilder hiddenYsids = new StringBuilder();
				for (int i = 0; i < hiddenYsidList.size(); i++) {
					if (i != 0) {
						hiddenYsids.append(";;");
					}
					hiddenYsids.append(hiddenYsidList.get(i));
				}
				model.addAttribute("hiddenYsids", hiddenYsids.toString());
			}
			
			String param = request.getParameter("Parameter");
			if (param != null && !param.equals("")){
				model.addAttribute("Parameter", param);
			}
			model.addAttribute("currUrl", link);
			return link;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("HomeController.navigation"+e.getMessage(),e);
		}
		
	}

	@RequestMapping(value = "/flowNavigation", method = RequestMethod.GET)
	public String flowNavigation(HttpServletRequest request, @RequestParam("link") String link,
			@RequestParam("processId") String processId, @RequestParam("orderId") String orderId,
			@RequestParam("taskId") String taskId, @RequestParam("flowReadonly") String flowReadonly, Model model)
					throws Exception {
		if (link == null || link.trim().equals("") || link.trim().equals("#")) {
			model.addAttribute("error", "系统功能未实现");
			return "error";
		}
		if (request.getSession() != null && request.getSession().getAttribute("loginInfo") != null) {
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
			List<String> hiddenYsidList = homeService.getHiddenYsidList(loginInfo, link);
			StringBuilder hiddenYsids = new StringBuilder();
			for (int i = 0; i < hiddenYsidList.size(); i++) {
				if (i != 0) {
					hiddenYsids.append(";;");
				}
				hiddenYsids.append(hiddenYsidList.get(i));
			}
			model.addAttribute("hiddenYsids", hiddenYsids.toString());
		}
		model.addAttribute("processId", processId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("currUrl", link);
		model.addAttribute("flowReadonly", flowReadonly);
		return link;
	}

	@RequestMapping(value = "/datagridExportExcel", method = RequestMethod.POST)
	public ModelAndView datagridExportExcel(@RequestParam("msie") String msie,
			@RequestParam("fileName") String fileName, @RequestParam("data") String data) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msie", msie);
		map.put("fileName", fileName);
		map.put("data", data);
		ExcelView excelView = new ExcelView();
		return new ModelAndView(excelView, map);
	}

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public Map<String, Object> modifyPassword(HttpServletRequest request, @RequestParam("ymm") String ymm,
			@RequestParam("xmm") String xmm, @RequestParam("qrxmm") String qrxmm) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
		Aes aes = new Aes();
		ymm = aes.aesDecrypt(ymm);
		xmm = aes.aesDecrypt(xmm);
		qrxmm = aes.aesDecrypt(qrxmm);
		homeService.modifyPassword(loginInfo, ymm, xmm, qrxmm);
		result.put("message", "密码修改成功");
		return result;
	}

}
