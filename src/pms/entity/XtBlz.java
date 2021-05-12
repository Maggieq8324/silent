package pms.entity;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "xt_blz") // 表列值
public class XtBlz implements Entity {

	@PrimaryKey
	private String bmc; // 表名称
	@PrimaryKey
	private String lmc; // 列名称
	private Long dqz; // 当前值
	private Long qzlsh; // 取值流水号
	private String cjrbh; // 创建人编号
	private Long cjsj; // 创建时间
	private String czrbh; // 操作人编号
	private Long czsj; // 操作时间

	public XtBlz() {
	}

	public String getBmc() {
		return this.bmc;
	}

	public void setBmc(String bmc) {
		this.bmc = bmc;
	}

	public String getLmc() {
		return this.lmc;
	}

	public void setLmc(String lmc) {
		this.lmc = lmc;
	}

	public Long getDqz() {
		return this.dqz;
	}

	public void setDqz(Long dqz) {
		this.dqz = dqz;
	}

	public Long getQzlsh() {
		return this.qzlsh;
	}

	public void setQzlsh(Long qzlsh) {
		this.qzlsh = qzlsh;
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
