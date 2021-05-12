package pms.model;

import java.io.Serializable;

public class LoginInfo implements Serializable{

	private String hhid;
	private String czybh;
	private String sjhm;
	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	private String dlzh;
	private String czymc;
	private String clientid;
	private String token;
	private Integer zdbs;

	public LoginInfo() {
	}

	public String getHhid() {
		return hhid;
	}

	public void setHhid(String hhid) {
		this.hhid = hhid;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getDlzh() {
		return dlzh;
	}

	public void setDlzh(String dlzh) {
		this.dlzh = dlzh;
	}

	public String getCzymc() {
		return czymc;
	}

	public void setCzymc(String czymc) {
		this.czymc = czymc;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getZdbs() {
		return zdbs;
	}

	public void setZdbs(Integer zdbs) {
		this.zdbs = zdbs;
	}



}
