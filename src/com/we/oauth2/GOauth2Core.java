package com.we.oauth2;
import java.util.ArrayList;
import java.util.List;

import com.we.Util.SMessage;
import com.we.param.ParamesAPI;
import com.we.param.WeixinUtil;

import net.sf.json.JSONArray;
/** 
 * Oauth2类 
 * @author Engineer.Jsp
 * @date 2014.10.13 
 */
import net.sf.json.JSONObject;


public class GOauth2Core {
	public static String GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	/**
	 * 企业获取code地址处理
	 * @param appid 企业的CorpID
	 * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param response_type 返回类型，此时固定为：code
	 * @param scope 应用授权作用域，此时固定为：snsapi_base
	 * @param state 重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值
	 * @param #wechat_redirect 微信终端使用此参数判断是否需要带上身份信息
	 * 员工点击后，页面将跳转至 redirect_uri/?code=CODE&state=STATE，企业可根据code参数获得员工的userid
	 * */
	public static String GetCode(String state){
		String get_code_url = "";
		get_code_url = GET_CODE.replace("CORPID", ParamesAPI.corpId).replace("REDIRECT_URI", WeixinUtil.URLEncoder(ParamesAPI.REDIRECT_URI)).replace("STATE", state);
		return get_code_url;
	}
	
	
	public static String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	/**
	 * 根据code获取成员信息
	 * @param access_token 调用接口凭证
	 * @param code 通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @param agentid 跳转链接时所在的企业应用ID
	 * 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
	 * private int errcode;
	 * private String errmsg;
	 * private String userId;
	 * private String openId;
	 * private String deviceId;
	 * private String userTicket;
	 * private int expiresIn;
	 * */
	public static WeUserinfo GetUserID (String access_token,String code){
		JSONObject jsonobject = WeixinUtil.HttpRequest(CODE_TO_USERINFO.replace("ACCESS_TOKEN", access_token).replace("CODE", code), "GET", null);
		WeUserinfo weUserinfo = new WeUserinfo();
		
		int errcode = -1;
		String UserId = "";
		String errmsg = "";
		String OpenId = "";
		String DeviceId = "";
		String user_ticket = "";
		int expires_in = -1;
		
		if(null!=jsonobject){
			errcode = jsonobject.getInt("errcode");
			
			if (errcode == 0){
				UserId = jsonobject.getString("UserId");
				if(!"".equals(UserId)){
					errcode = jsonobject.getInt("errcode");
					errmsg = jsonobject.getString("errmsg");
					DeviceId = jsonobject.getString("DeviceId");
					//user_ticket = jsonobject.getString("user_ticket");
					//expires_in = jsonobject.getInt("expires_in");
				}else{
					errcode = jsonobject.getInt("errcode");  
		            errmsg = jsonobject.getString("errmsg");
		            DeviceId = jsonobject.getString("DeviceId");
		            OpenId = jsonobject.getString("OpenId");
		            
		            System.out.println("错误码："+errcode+"————"+"错误信息："+errmsg);
				}
			} else {
				errcode = jsonobject.getInt("errcode");
				errmsg = jsonobject.getString("errmsg");
				
				weUserinfo.setErrcode(errcode);
				weUserinfo.setErrmsg(errmsg);
			}
			
			weUserinfo.setErrcode(errcode);
			weUserinfo.setErrmsg(errmsg);
			weUserinfo.setDeviceId(DeviceId);
			weUserinfo.setUserTicket(user_ticket);
			weUserinfo.setUserId(UserId);
			weUserinfo.setExpiresIn(expires_in);
			weUserinfo.setOpenId(OpenId);
		}else{
			System.out.println("获取授权失败了。。。");
		}
		
		return weUserinfo;
	}
	
	
	public static String CODE_TO_USERDETAIL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";

	public static WeUserdetail GetUserDetail (String access_token,String UserId){
		WeUserdetail oneWeUserdetail = new WeUserdetail();
		//String PostData = SMessage.STicketMsg(user_ticket);
				
		JSONObject jsonobject = WeixinUtil.HttpRequest(CODE_TO_USERDETAIL.replace("ACCESS_TOKEN", access_token).replace("USERID", UserId), "GET", null);
		
		String userid = "";
		String name = "";
		String department = "";
		String position = "";
		String mobile = "";
		String gender = "";
		String email = "";
		String avatar = "";
		String telephone = "";
		
		userid = jsonobject.getString("userid");
		
		if (userid != null && !"".equals(userid)){
			name = jsonobject.getString("name");
			position = jsonobject.getString("position");
			mobile = jsonobject.getString("mobile");
			gender = jsonobject.getString("gender");
			email = jsonobject.getString("email");
			department = String.valueOf(jsonobject.getJSONArray("department").getInt(0));
			avatar = jsonobject.getString("avatar");
			telephone = jsonobject.getString("telephone");
		}
		oneWeUserdetail.setAvatar(avatar);
		oneWeUserdetail.setDepartment(department);
		oneWeUserdetail.setEmail(email);
		oneWeUserdetail.setGender(gender);;
		oneWeUserdetail.setMobile(mobile);
		oneWeUserdetail.setName(name);
		oneWeUserdetail.setPosition(position);
		oneWeUserdetail.setUserid(userid);
		oneWeUserdetail.setTelephone(telephone);
		
		return oneWeUserdetail;
	}
	
	
	public static String CODE_TO_USERLIST = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
	public static List<WeUserdetail> GetUserList(String access_token, String department, String fetch){
		List<WeUserdetail> listWeUser = new ArrayList<WeUserdetail>();
		
		JSONObject jsonobject = WeixinUtil.HttpRequest(CODE_TO_USERLIST.replace("ACCESS_TOKEN", access_token).replace("DEPARTMENT_ID", department).replace("FETCH_CHILD", fetch), "GET", null);
		
		JSONArray usrlist = jsonobject.getJSONArray("userlist");
		
		for (int i=0; i < usrlist.size(); i++){
			JSONObject user = usrlist.getJSONObject(i);
			
			WeUserdetail oneWeUserdetail = new WeUserdetail();
			oneWeUserdetail.setAvatar(user.getString("avatar"));
			oneWeUserdetail.setDepartment(String.valueOf(user.getJSONArray("department").getInt(0)));
			oneWeUserdetail.setEmail(user.getString("email"));
			oneWeUserdetail.setGender(user.getString("gender"));;
			oneWeUserdetail.setMobile(user.getString("mobile"));
			oneWeUserdetail.setName(user.getString("name"));
			oneWeUserdetail.setPosition(user.getString("position"));
			oneWeUserdetail.setUserid(user.getString("userid"));
			oneWeUserdetail.setTelephone(user.getString("telephone"));
			
			listWeUser.add(oneWeUserdetail);
		}
		
		return listWeUser;
	}
}
