package pms.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platform.dataaccess.JdbcManager;
import com.platform.dataaccess.support.Sort;
import pms.entity.XtDm;
import pms.entity.XtGnxx;
import pms.model.LoginInfo;
import pms.support.BusinessException;

@Service("xtglService")
public class XtglService {

	@Autowired
	private JdbcManager jdbcManager;

	public List<XtGnxx> queryDjmkByCzy(LoginInfo loginInfo) throws BusinessException {
		return jdbcManager
				.parameterizedQuery("select distinct c.* from xt_czyxx a, xt_jsgngx b, xt_gnxx c " + 
						" where a.czybh=? and b.jsbh = a.jsbh and b.gnbh = c.gnbh order by mksxh,gnsxh,czsxh")
				.setParameter(loginInfo.getCzybh())
				.getEntityList(XtGnxx.class);
	}
	
	public List<XtGnxx> queryGnByCzy(String czybh, String djmkgnbh) throws BusinessException {
		List<XtGnxx> xtGnxxList = jdbcManager
				.parameterizedQuery("select * from xt_gnxx "
						+ " where sjgnbh <> '0' or gnlxdm <> '0' order by djmksxh,mksxh,gnsxh,czsxh").getEntityList(XtGnxx.class);
		if (xtGnxxList == null || xtGnxxList.isEmpty()) {
			return xtGnxxList;
		}
		List<XtGnxx> resultXtGnxxList = new ArrayList<XtGnxx>();
		int j = -1;
		for (int i = 0; i < xtGnxxList.size(); i++) {
			XtGnxx xtGnxx = xtGnxxList.get(i);
			if (xtGnxx.getSjgnbh().equals(djmkgnbh) && xtGnxx.getGnlxdm().equals("0")) {
				j = i;
			}
			if (!xtGnxx.getSjgnbh().equals(djmkgnbh) && xtGnxx.getGnlxdm().equals("0")) {
				j = -1;
			}
			if (j != -1) {
				resultXtGnxxList.add(xtGnxx);
			}
		}
		return resultXtGnxxList;
	}
	
	
	public List<XtDm> queryAllDm() throws BusinessException {
		XtDm dm = new XtDm();
		dm.setDmzt("0");
		return jdbcManager.entitiesQuery(dm, XtDm.class, new Sort("dmflbm,xssxh"));
	}
	
	

}
