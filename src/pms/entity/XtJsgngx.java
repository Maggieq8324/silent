package pms.entity;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.Table;
import com.platform.dataaccess.support.PrimaryKey;

@Table(name = "xt_jsgngx") // 角色功能关系
public class XtJsgngx implements Entity {

	@PrimaryKey
	private String jsbh; // 角色编号
	@PrimaryKey
	private String gnbh; // 功能编号
	private String cjrbh; // 创建人编号
	private Long cjsj; // 创建时间
	private String czrbh; // 操作人编号
	private Long czsj; // 操作时间

	public XtJsgngx() {
	}

	public String getJsbh() {
		return this.jsbh;
	}

	public void setJsbh(String jsbh) {
		this.jsbh = jsbh;
	}

	public String getGnbh() {
		return this.gnbh;
	}

	public void setGnbh(String gnbh) {
		this.gnbh = gnbh;
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
