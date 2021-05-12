package pms.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.platform.dataaccess.JdbcManager;
import com.platform.dataaccess.support.PaginationCondition;

import pms.entity.XtCzjl;
import pms.entity.XtCzyxx;
import pms.entity.XtGnxx;
import pms.model.LoginInfo;
import pms.support.BusinessException;

@Service("interceptorService")
public class InterceptorService {

	@Autowired
	private JdbcManager jdbcManager;
	
	private String hostaddr;

	public String getHostaddr() {
		return hostaddr;
	}

	public void setHostaddr(String hostaddr) {
		this.hostaddr = hostaddr;
	}


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws BusinessException {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		if (lookupPath.endsWith("wechat/verify") || lookupPath.endsWith("wechat/login") || lookupPath.endsWith("home") || lookupPath.endsWith("muiHome") || lookupPath.endsWith("weuiHome") || lookupPath.endsWith("relogin")
				|| lookupPath.endsWith("weHome") || lookupPath.endsWith("welogin") || lookupPath.endsWith("muiRelogin") || lookupPath.endsWith("captcha")
				|| lookupPath.endsWith("queryZzbmCzy") || lookupPath.endsWith("queryXjZzbm")) {
			
			if (lookupPath.endsWith("relogin") || lookupPath.endsWith("muiRelogin") || lookupPath.endsWith("welogin")) {
				request.getSession().invalidate();
			}
			return true;
		}
		if (request.getSession() != null && request.getSession().getAttribute("loginInfo") != null) {
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
			
			XtCzyxx czy = new XtCzyxx();
			czy.setCzybh(loginInfo.getCzybh());
			czy = jdbcManager.entityQuery(czy, XtCzyxx.class);
			if (czy == null) {
				throw new BusinessException("不能获取当前登录的操作员信息，请重新登录");
			}
			
			String referer=request.getHeader("Referer"); 
			String orgin = request.getHeader("Origin");
			String[] hostaddrs = hostaddr.split(";");
			boolean withHostaddr = false;
			StringBuffer url_str = new StringBuffer();
			if(referer != null){
				for(int i = 0;i < hostaddrs.length;i++){
					url_str.append(hostaddrs[i]+"/xsfx;");
					if(referer.startsWith(hostaddrs[i])){
						withHostaddr = true;
					}
				}
			}
			if (referer != null&&!withHostaddr){
				throw new BusinessException("请通过以下地址访问系统："+url_str.toString());
			}
			boolean withHostaddrO = false;
			if(orgin != null){
				for(int i = 0;i < hostaddrs.length;i++){
					if(orgin.startsWith(hostaddrs[i])){
						withHostaddrO = true;
						continue;
					}
				}
			}
			if (orgin != null&&!withHostaddrO){
				throw new BusinessException("请通过以下地址访问系统："+url_str.toString());
			}
			
			PaginationCondition p = new PaginationCondition();
			p.setPage(1);
			p.setRows(1);
			p.setSort("cjsj");
			p.setOrder("desc");
			String czyLastHhid = jdbcManager.parameterizedQuery("select hhid from xt_czjl where czybh = ?", p)
					.setParameter(loginInfo.getCzybh()).getPaginationObjects(String.class).getRows().get(0);
			if (!czyLastHhid.equals(loginInfo.getHhid())) {
				throw new BusinessException("操作员已在其它地方登录，不允许操作");
			}
			
			String gnljdz;
			if ((lookupPath.endsWith("navigation") || lookupPath.endsWith("flowNavigation"))
					&& request.getParameter("link") != null && !request.getParameter("link").equals("")) {
				gnljdz = request.getParameter("link");
			} else {
				gnljdz = lookupPath;
			}
			String args;
			if (gnljdz.startsWith("/")) {
				args = gnljdz.substring(1);
			} else {
				args = gnljdz;
			}
			XtGnxx gn = jdbcManager.parameterizedQuery(" select * from xt_gnxx where gnljdz = ? or gnljdz = ? ")
					.setParameter(args).setParameter(gnljdz).getEntity(XtGnxx.class);
			if (gn != null) {
				/*XtJsgngx jsgngx = jdbcManager
						.parameterizedQuery("  select * from xt_jsgngx a,xt_gwjsgx b where  b.gwbh=? and a.gnbh=? and a.jsbh=b.JSBH")
						.setParameter(loginInfo.getGwbh()).setParameter(gn.getGnbh()).getEntity(XtJsgngx.class);
				if (jsgngx == null) {
					throw new BusinessException("操作员没有 [ " + gn.getGnmc() + " ] 操作权限，不允许操作");
				}*/
			}
			return true;
		} else if (request.getSession() != null && request.getSession().getAttribute("yhxx") != null) {
			return true;
		} else {
			return true;
			//throw new BusinessException("登录会话超时或中断，请重新登录");
		}
	}

	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws BusinessException {
		if (request.getSession() != null && request.getSession().getAttribute("loginInfo") != null) {
			String url = request.getRequestURI();
			if (!url.endsWith("relogin")) {
				LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
				XtCzjl czjl = new XtCzjl();
				Long datetime = jdbcManager.getCurrentDateTimeLongValue();
				czjl.setHhid(loginInfo.getHhid());
				czjl.setCzybh(loginInfo.getCzybh());
				czjl.setGnbh("0");
				czjl.setGnljdz(url);
				czjl.setLjdzfjcs("0");
				czjl.setCzkssj(datetime);
				czjl.setCzjssj(datetime);
				if (request.getAttribute("exceptionMessage") != null) {
					czjl.setCzjgdm("1");
					String message = request.getAttribute("exceptionMessage").toString();
					if (message.length() > 1000) {
						czjl.setJgyyms(message.substring(0, 1000));
					} else {
						czjl.setJgyyms(message);
					}
				} else {
					czjl.setCzjgdm("0");
					czjl.setJgyyms("0");
				}
				czjl.setCjrbh(loginInfo.getCzybh());
				czjl.setCjsj(datetime);
				czjl.setCzrbh(loginInfo.getCzybh());
				czjl.setCzsj(datetime);
				jdbcManager.entityInsert(czjl);
			}
		}
	}

	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws BusinessException {
		if (ex != null) {
			Log logger = LogFactory.getLog(getClass());
			logger.error(ex.getLocalizedMessage(), ex);
		} else {
			if (request.getAttribute("exceptionMessage") != null) {
				String url = request.getRequestURI();
				if (!url.endsWith("relogin") && !url.endsWith("muiHome")) {
					if (request.getSession() != null && request.getSession().getAttribute("loginInfo") != null) {
						LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
						XtCzjl czjl = new XtCzjl();
						Long datetime = jdbcManager.getCurrentDateTimeLongValue();
						czjl.setHhid(loginInfo.getHhid());
						czjl.setCzybh(loginInfo.getCzybh());
						czjl.setGnbh("0");
						czjl.setGnljdz(url);
						czjl.setLjdzfjcs("0");
						czjl.setCzkssj(datetime);
						czjl.setCzjssj(datetime);
						czjl.setCzjgdm("1");
						String exceptionMessage = request.getAttribute("exceptionMessage").toString();
						if (exceptionMessage.length() > 1000) {
							czjl.setJgyyms(exceptionMessage.substring(0, 1000));
						} else {
							czjl.setJgyyms(exceptionMessage);
						}
						czjl.setCjrbh(loginInfo.getCzybh());
						czjl.setCjsj(datetime);
						czjl.setCzrbh(loginInfo.getCzybh());
						czjl.setCzsj(datetime);
						jdbcManager.entityInsert(czjl);
					}
				}
				if (!url.endsWith("relogin") && !url.endsWith("weuiHome")) {
					if (request.getSession() != null && request.getSession().getAttribute("loginInfo") != null) {
						LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");
						XtCzjl czjl = new XtCzjl();
						Long datetime = jdbcManager.getCurrentDateTimeLongValue();
						czjl.setHhid(loginInfo.getHhid());
						czjl.setCzybh(loginInfo.getCzybh());
						czjl.setGnbh("0");
						czjl.setGnljdz(url);
						czjl.setLjdzfjcs("0");
						czjl.setCzkssj(datetime);
						czjl.setCzjssj(datetime);
						czjl.setCzjgdm("1");
						String exceptionMessage = request.getAttribute("exceptionMessage").toString();
						if (exceptionMessage.length() > 1000) {
							czjl.setJgyyms(exceptionMessage.substring(0, 1000));
						} else {
							czjl.setJgyyms(exceptionMessage);
						}
						czjl.setCjrbh(loginInfo.getCzybh());
						czjl.setCjsj(datetime);
						czjl.setCzrbh(loginInfo.getCzybh());
						czjl.setCzsj(datetime);
						jdbcManager.entityInsert(czjl);
					}
				}
				
			}
		}
	}

}
