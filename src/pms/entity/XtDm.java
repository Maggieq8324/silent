package pms.entity;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "xt_dm") // 代码
public class XtDm implements Entity {

	@PrimaryKey
	private String dmbh; // 代码编号
	private String sjdmbh; // 上级代码编号
	private String dmlb; // 代码类别（0_系统代码,1_用户代码,系统代码固定在系统开发过程中，用户代码可以动态添加）
	private String dmflbm; // 代码分类编码
	private String dmflmc; // 代码分类名称
	private String dmbm; // 代码编码
	private String dmmc; // 代码名称
	private Integer xssxh; // 显示顺序号
	private String dmzt; // 代码状态（正常、停用）
	private Long ztbgsj; // 状态变更时间
	private String cjrbh; // 创建人编号
	private Long cjsj; // 创建时间
	private String czrbh; // 操作人编号
	private Long czsj; // 操作时间

	public XtDm() {
	}

	public String getDmbh() {
		return this.dmbh;
	}

	public void setDmbh(String dmbh) {
		this.dmbh = dmbh;
	}

	public String getSjdmbh() {
		return this.sjdmbh;
	}

	public void setSjdmbh(String sjdmbh) {
		this.sjdmbh = sjdmbh;
	}

	public String getDmlb() {
		return this.dmlb;
	}

	public void setDmlb(String dmlb) {
		this.dmlb = dmlb;
	}

	public String getDmflbm() {
		return this.dmflbm;
	}

	public void setDmflbm(String dmflbm) {
		this.dmflbm = dmflbm;
	}

	public String getDmflmc() {
		return this.dmflmc;
	}

	public void setDmflmc(String dmflmc) {
		this.dmflmc = dmflmc;
	}

	public String getDmbm() {
		return this.dmbm;
	}

	public void setDmbm(String dmbm) {
		this.dmbm = dmbm;
	}

	public String getDmmc() {
		return this.dmmc;
	}

	public void setDmmc(String dmmc) {
		this.dmmc = dmmc;
	}

	public Integer getXssxh() {
		return this.xssxh;
	}

	public void setXssxh(Integer xssxh) {
		this.xssxh = xssxh;
	}

	public String getDmzt() {
		return this.dmzt;
	}

	public void setDmzt(String dmzt) {
		this.dmzt = dmzt;
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
