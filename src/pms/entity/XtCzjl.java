package pms.entity;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "xt_czjl") // 操作记录
public class XtCzjl implements Entity {

	@PrimaryKey
	private Long czlsh; // 操作流水号
	private String hhid; // 会话ID
	private String czybh; // 操作员编号
	private String gnbh; // 功能编号
	private String gnljdz; // 功能链接地址
	private String ljdzfjcs; // 链接地址附加参数
	private Long czkssj; // 操作开始时间
	private Long czjssj; // 操作结束时间
	private String czjgdm; // 操作结果代码（成功、失败）
	private String jgyyms; // 结果原因描述（存储失败原因信息）
	private String cjrbh; // 创建人编号
	private Long cjsj; // 创建时间
	private String czrbh; // 操作人编号
	private Long czsj; // 操作时间

	public XtCzjl() {
	}

	public Long getCzlsh() {
		return this.czlsh;
	}

	public void setCzlsh(Long czlsh) {
		this.czlsh = czlsh;
	}

	public String getHhid() {
		return this.hhid;
	}

	public void setHhid(String hhid) {
		this.hhid = hhid;
	}

	public String getCzybh() {
		return this.czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getGnbh() {
		return this.gnbh;
	}

	public void setGnbh(String gnbh) {
		this.gnbh = gnbh;
	}

	public String getGnljdz() {
		return this.gnljdz;
	}

	public void setGnljdz(String gnljdz) {
		this.gnljdz = gnljdz;
	}

	public String getLjdzfjcs() {
		return this.ljdzfjcs;
	}

	public void setLjdzfjcs(String ljdzfjcs) {
		this.ljdzfjcs = ljdzfjcs;
	}

	public Long getCzkssj() {
		return this.czkssj;
	}

	public void setCzkssj(Long czkssj) {
		this.czkssj = czkssj;
	}

	public Long getCzjssj() {
		return this.czjssj;
	}

	public void setCzjssj(Long czjssj) {
		this.czjssj = czjssj;
	}

	public String getCzjgdm() {
		return this.czjgdm;
	}

	public void setCzjgdm(String czjgdm) {
		this.czjgdm = czjgdm;
	}

	public String getJgyyms() {
		return this.jgyyms;
	}

	public void setJgyyms(String jgyyms) {
		this.jgyyms = jgyyms;
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
