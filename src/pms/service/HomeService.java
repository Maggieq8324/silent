package pms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.platform.dataaccess.JdbcManager;
import pms.entity.XtCzyxx;
import pms.model.LoginInfo;
import pms.support.BusinessException;
import pms.support.EncryptionHelper;

@Service("homeService")
public class HomeService {

	@Autowired
	private JdbcManager jdbcManager;

	@Transactional(rollbackFor = Throwable.class,noRollbackFor = BusinessException.class, readOnly = false)
	public String login(HttpServletRequest request, Model model, String dlzh, String dlmm, String captcha)
			throws BusinessException {
		LoginInfo result = _login(request, model, dlzh, dlmm, captcha);
		if (result != null){
			return "home";
		} else {
			throw new BusinessException("登录验证错误！");
		}
		
	}
	
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public Map<String,Object> register(HttpServletRequest request, Model model, String dlzh, String czymc, String dlmm, String qrmm, String tjm)
			throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		if(dlzh == null || dlzh.equals("")) {
			result.put("message","请输入登录账号");
		}else if(czymc == null || czymc.equals("")) {
			result.put("message","请输入用户名");
		}else if(dlmm == null || dlmm.equals("")) {
			result.put("message","请输入登录密码");
		}else if(qrmm == null || qrmm.equals("")) {
			result.put("message","请输入确认密码");
		}else if(tjm == null || tjm.equals("")) {
			result.put("message","请输入推荐码");
		}else {
			result.put("message","注册成功,请返回登录!");
		}
		return result;
		
	}
	
	/*@Transactional(rollbackFor = Throwable.class,noRollbackFor = BusinessException.class, readOnly = false)
	public String welogin(HttpServletRequest request, Model model, String dlzh, String dlmm, String captcha)
			throws BusinessException {
		LoginInfo result = _login(request, model, dlzh, dlmm, captcha);
		
		if (result != null){
			return "wehome";
		} else {
			throw new BusinessException("登录验证错误！");
		}
	}*/
	
	@Transactional(rollbackFor = Throwable.class,noRollbackFor = BusinessException.class, readOnly = false)
	public LoginInfo _login(HttpServletRequest request, Model model, String dlzh, String dlmm, String captcha)
			throws BusinessException {
		if (dlzh == null || dlzh.equals("")) {
			throw new BusinessException("请输入登录账号");
		}
		if (dlmm == null || dlmm.equals("")) {
			throw new BusinessException("请输入登录密码");
		}
		/*if (captcha == null || captcha.equals("")) {
			throw new BusinessException("请输入验证码");
		}*/
		XtCzyxx czy = new XtCzyxx();
		czy.setDlzh(dlzh);
		
		List<XtCzyxx> lxc = jdbcManager.entitiesQuery(czy, XtCzyxx.class);
		if (lxc == null || lxc.isEmpty()) {
			czy = null;
		} else {
			czy = lxc.get(0);
		}
		Long ztbgsj=null;
		Long nowsj=jdbcManager.getCurrentDateTimeLongValue();
		if (czy == null) {
			throw new BusinessException("登录账号不存在");
		} else if (czy.getDlmm() == null || !czy.getDlmm().equals(dlmm)) {
			ztbgsj=czy.getZtbgsj();
			Long sjc=nowsj-ztbgsj;
			int dlmmycwcs=czy.getDlmmycwcs();
			if(sjc<=900 && dlmmycwcs<5) {
				czy.setDlmmycwcs(dlmmycwcs+1);
				jdbcManager.entityUpdate(czy);
			}else if(sjc<=900 && dlmmycwcs>=5) {
				throw new BusinessException("账号已被锁定，请稍后再进行登录");
			}else {
				czy.setDlmmycwcs(1);
				czy.setZtbgsj(nowsj);
				jdbcManager.entityUpdate(czy);
			}
			throw new BusinessException("登录密码错误，您还有"+(5-czy.getDlmmycwcs())+"次机会！");
		} /*else if (request.getSession() == null || request.getSession().getAttribute("captcha") == null
				|| !captcha.equalsIgnoreCase(request.getSession().getAttribute("captcha").toString())) {
			throw new BusinessException("验证码错误");
		}*/else {
			ztbgsj=czy.getZtbgsj();
			Long sjc=nowsj-ztbgsj;
			int dlmmycwcs=czy.getDlmmycwcs();
			if(sjc<=900 && dlmmycwcs>=5) {
				throw new BusinessException("您的密码错误次数已超限，请30分钟后再登录！");
			}else {
				LoginInfo loginInfo = new LoginInfo();
				czy.setZtbgsj(nowsj);
				czy.setDlmmycwcs(0);
				jdbcManager.entityUpdate(czy);
				
				loginInfo.setHhid(request.getSession().getId());
				loginInfo.setCzybh(czy.getCzybh());
				loginInfo.setDlzh(czy.getDlzh());
				loginInfo.setCzymc(czy.getCzymc());
				loginInfo.setSjhm(czy.getSjhm());
				model.addAttribute("loginInfo", loginInfo);
				return loginInfo;
			}
			
		}
	}

	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public void modifyPassword(LoginInfo loginInfo, String ymm, String xmm, String qrxmm) throws BusinessException {
		XtCzyxx czy = new XtCzyxx();
		czy.setCzybh(loginInfo.getCzybh());
		czy = jdbcManager.entityQuery(czy, XtCzyxx.class);
		if (!czy.getDlmm().equals(EncryptionHelper.encrypt(ymm))) {
			throw new BusinessException("原密码不对");
		}
		if (!xmm.equals(qrxmm)) {
			throw new BusinessException("两次输入的新密码不一致");
		}
		czy.setDlmm(EncryptionHelper.encrypt(xmm));
		jdbcManager.entityUpdate(czy);
	}


	public List<String> getHiddenYsidList(LoginInfo loginInfo, String link) throws BusinessException {
		String args;
		if (link.startsWith("/")) {
			args = link.substring(1);
		} else {
			args = link;
		}
		return jdbcManager
				.parameterizedQuery(
						"select ysid from xt_gnxx where sjgnbh in (select gnbh from xt_gnxx where gnljdz = ? or gnljdz = ?)")
				.setParameter(args).setParameter(link).getObjectList(String.class);
	}
	
	
	
	


}
