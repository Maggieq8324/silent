package pms.entity;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "xt_gnxx") // 功能信息
public class XtGnxx implements Entity {

	@PrimaryKey
	private String gnbh; // 功能编号
	private String sjgnbh; // 上级功能编号(操作的上级是功能,功能的上级是模块,模块的上级也可以是模块)
	private String gnmc; // 功能名称
	private String gnms; // 功能描述
	private String ysid; // 元素ID(针对操作类界面元素是否显示的元素ID，比如按钮等)
	private String gnljdz; // 功能链接地址
	private String ljdzfjcs; // 链接地址附加参数
	private String gnljfsdm; // 功能链接方式代码（内部链接、外部链接）
	private String gnlxdm; // 功能类型代码(0_模块,1_功能,2_操作)
	private String gnytdm; // 功能用途代码(0_系统管理,1_业务操作)
	private String gntblmc; // 功能图标类名称(存储功能图标CSS类名称)
	private String gntbljdz; // 功能图标链接地址(存储功能图标文件名和相对路径)
	private String gnbbh; // 功能版本号
	private Integer djmksxh; // 顶级模块顺序号
	private Integer mksxh; // 模块顺序号
	private Integer gnsxh; // 功能顺序号
	private Integer czsxh; // 操作顺序号
	private String ycbz; // 隐藏标志（是、否）
	private String gnztdm; // 功能状态代码（正常、停用等）
	private Long ztbgsj; // 状态变更时间
	private String cjrbh; // 创建人编号
	private Long cjsj; // 创建时间
	private String czrbh; // 操作人编号
	private Long czsj; // 操作时间

	public XtGnxx() {
	}

	public String getGnbh() {
		return this.gnbh;
	}

	public void setGnbh(String gnbh) {
		this.gnbh = gnbh;
	}

	public String getSjgnbh() {
		return this.sjgnbh;
	}

	public void setSjgnbh(String sjgnbh) {
		this.sjgnbh = sjgnbh;
	}

	public String getGnmc() {
		return this.gnmc;
	}

	public void setGnmc(String gnmc) {
		this.gnmc = gnmc;
	}

	public String getGnms() {
		return this.gnms;
	}

	public void setGnms(String gnms) {
		this.gnms = gnms;
	}

	public String getYsid() {
		return this.ysid;
	}

	public void setYsid(String ysid) {
		this.ysid = ysid;
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

	public String getGnljfsdm() {
		return this.gnljfsdm;
	}

	public void setGnljfsdm(String gnljfsdm) {
		this.gnljfsdm = gnljfsdm;
	}

	public String getGnlxdm() {
		return this.gnlxdm;
	}

	public void setGnlxdm(String gnlxdm) {
		this.gnlxdm = gnlxdm;
	}

	public String getGnytdm() {
		return this.gnytdm;
	}

	public void setGnytdm(String gnytdm) {
		this.gnytdm = gnytdm;
	}

	public String getGntblmc() {
		return this.gntblmc;
	}

	public void setGntblmc(String gntblmc) {
		this.gntblmc = gntblmc;
	}

	public String getGntbljdz() {
		return this.gntbljdz;
	}

	public void setGntbljdz(String gntbljdz) {
		this.gntbljdz = gntbljdz;
	}

	public String getGnbbh() {
		return this.gnbbh;
	}

	public void setGnbbh(String gnbbh) {
		this.gnbbh = gnbbh;
	}

	public Integer getDjmksxh() {
		return this.djmksxh;
	}

	public void setDjmksxh(Integer djmksxh) {
		this.djmksxh = djmksxh;
	}

	public Integer getMksxh() {
		return this.mksxh;
	}

	public void setMksxh(Integer mksxh) {
		this.mksxh = mksxh;
	}

	public Integer getGnsxh() {
		return this.gnsxh;
	}

	public void setGnsxh(Integer gnsxh) {
		this.gnsxh = gnsxh;
	}

	public Integer getCzsxh() {
		return this.czsxh;
	}

	public void setCzsxh(Integer czsxh) {
		this.czsxh = czsxh;
	}

	public String getYcbz() {
		return this.ycbz;
	}

	public void setYcbz(String ycbz) {
		this.ycbz = ycbz;
	}

	public String getGnztdm() {
		return this.gnztdm;
	}

	public void setGnztdm(String gnztdm) {
		this.gnztdm = gnztdm;
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
