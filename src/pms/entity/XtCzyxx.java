package pms.entity;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "xt_czyxx") // 操作员信息
public class XtCzyxx implements Entity {

	@PrimaryKey
	private String czybh; // 操作员编号
	private String jsbh;
	private String dlzh; // 登录账号
	private String dlmm; // 登录密码
	private String czymc; // 操作员名称
	private String sjhm; // 手机号码
	private String xbdm; // 性别代码
	private Integer dlmmycwcs; // 登录密码已错误次数
	private Integer dlmmzdcwcs; // 登录密码最大错误次数
	private String czyztdm; // 操作员状态代码（正常、停用、锁定等）
	private Long ztbgsj; // 状态变更时间
	private String cjrbh; // 创建人编号
	private Long cjsj; // 创建时间
	private String czrbh; // 操作人编号
	private Long czsj; // 操作时间

	public XtCzyxx() {
	}

	public String getCzybh() {
		return this.czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getJsbh() {
		return this.jsbh;
	}

	public void setJsbh(String jsbh) {
		this.jsbh = jsbh;
	}

	public String getDlzh() {
		return this.dlzh;
	}

	public void setDlzh(String dlzh) {
		this.dlzh = dlzh;
	}

	public String getDlmm() {
		return this.dlmm;
	}

	public void setDlmm(String dlmm) {
		this.dlmm = dlmm;
	}

	public String getCzymc() {
		return this.czymc;
	}

	public void setCzymc(String czymc) {
		this.czymc = czymc;
	}

	public String getSjhm() {
		return this.sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getXbdm() {
		return this.xbdm;
	}

	public void setXbdm(String xbdm) {
		this.xbdm = xbdm;
	}

	public Integer getDlmmycwcs() {
		return this.dlmmycwcs;
	}

	public void setDlmmycwcs(Integer dlmmycwcs) {
		this.dlmmycwcs = dlmmycwcs;
	}

	public Integer getDlmmzdcwcs() {
		return this.dlmmzdcwcs;
	}

	public void setDlmmzdcwcs(Integer dlmmzdcwcs) {
		this.dlmmzdcwcs = dlmmzdcwcs;
	}

	public String getCzyztdm() {
		return this.czyztdm;
	}

	public void setCzyztdm(String czyztdm) {
		this.czyztdm = czyztdm;
	}

	public Long getZtbgsj() {
		return this.ztbgsj;
	}

	public void setZtbgsj(Long ztbgsj) {
		this.ztbgsj = ztbgsj;
	}

	public String getCjrbh() {
		return this.cjrbh;
	}

	public void setCjrbh(String cjrbh) {
		this.cjrbh = cjrbh;
	}

	public Long getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Long cjsj) {
		this.cjsj = cjsj;
	}

	public String getCzrbh() {
		return this.czrbh;
	}

	public void setCzrbh(String czrbh) {
		this.czrbh = czrbh;
	}

	public Long getCzsj() {
		return this.czsj;
	}

	public void setCzsj(Long czsj) {
		this.czsj = czsj;
	}

}
