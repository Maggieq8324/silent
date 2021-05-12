package pms.entity;

import java.util.Date;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "qsyr_fjxx") // 秋水伊人附件信息
public class QsyrFjxx implements Entity {

	@PrimaryKey
	private String fjbh; // 附件编号
	private String scrq; // 上传日期
	private String fjmc; // 附件名称
	private String fjml; // 附件根目录
	private String fjlj; // 附件路径
	private String fjhzm; // 后缀名
	private String wzsm; // 文字说明
	private Integer fjlx; // 附件类型
	private Integer xssxh; // 显示顺序号
	private String tjrbh; // 提交人编号
	private String tjrmc; // 提交人名称
	private Date tjsj; // 提交时间

	public QsyrFjxx() {
	}

	public String getFjbh() {
		return this.fjbh;
	}

	public void setFjbh(String fjbh) {
		this.fjbh = fjbh;
	}

	public String getScrq() {
		return this.scrq;
	}

	public void setScrq(String scrq) {
		this.scrq = scrq;
	}

	public String getFjmc() {
		return this.fjmc;
	}

	public void setFjmc(String fjmc) {
		this.fjmc = fjmc;
	}

	public String getFjml() {
		return this.fjml;
	}

	public void setFjml(String fjml) {
		this.fjml = fjml;
	}

	public String getFjlj() {
		return this.fjlj;
	}

	public void setFjlj(String fjlj) {
		this.fjlj = fjlj;
	}

	public String getFjhzm() {
		return this.fjhzm;
	}

	public void setFjhzm(String fjhzm) {
		this.fjhzm = fjhzm;
	}

	public String getWzsm() {
		return this.wzsm;
	}

	public void setWzsm(String wzsm) {
		this.wzsm = wzsm;
	}

	public Integer getFjlx() {
		return this.fjlx;
	}

	public void setFjlx(Integer fjlx) {
		this.fjlx = fjlx;
	}

	public Integer getXssxh() {
		return this.xssxh;
	}

	public void setXssxh(Integer xssxh) {
		this.xssxh = xssxh;
	}

	public String getTjrbh() {
		return this.tjrbh;
	}

	public void setTjrbh(String tjrbh) {
		this.tjrbh = tjrbh;
	}

	public String getTjrmc() {
		return this.tjrmc;
	}

	public void setTjrmc(String tjrmc) {
		this.tjrmc = tjrmc;
	}

	public Date getTjsj() {
		return this.tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

}
