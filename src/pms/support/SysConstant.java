package pms.support;

public class SysConstant {
	
	/*短信类型:-1_欠费短信,1_催费短信,2_停电短信,3_复电短信*/
	final static public int DXLX_QF = -1;
	final static public int DXLX_CF = 1;
	final static public int DXLX_TD = 2;
	final static public int DXLX_FD = 3;
	
	/*用户欠费类型:1_已经欠费,2_1到5天内欠费,3_5天后欠费*/
	final static public int YH_QF = 1;
	final static public int YH_KQF = 2;
	final static public int YH_MQF = 3;
	
	/*停复电启动标志:0_自动启动,1_手动启动*/
	final static public String YH_TFD_ZQD = "0";
	final static public String YH_TFD_RGQD = "1";

	/*短信发送状态:0_待发送,1_已发送状态未知,2_发送成功,3_发送失败*/
	final static public int SMS_NOSEND = 0;
	final static public int SMS_UNKNOW = 1;
	final static public int SMS_OK = 2;
	final static public int SMS_FALSE = 3;
	
	/*停电审批状态:0_待审批,1_审批通过,2_审批不通过*/
	final static public int TD_SP_UNCHECK = 0;
	final static public int TD_SP_OK = 1;
	final static public int TD_SP_FALSE = 2;
	
	/*复电审批状态:0_待审批,1_审批通过,2_审批不通过*/
	final static public int FD_SP_UNCHECK = 0;
	final static public int FD_SP_OK = 1;
	final static public int FD_SP_FALSE = 2;
	
	/*停电执行结果:0_未发送指令,1_已发送指令未知状态,2_停电成功,3_停电失败*/
	final static public int TD_NOSEND = 0;
	final static public int TD_UNKNOW = 1;
	final static public int TD_OK = 2;
	final static public int TD_FALSE = 3;
	
	/*复电执行结果:0_未发送指令,1_已发送指令未知状态,2_复电成功,3_复电失败*/
	final static public int FD_NOSEND = 0;
	final static public int FD_UNKNOW = 1;
	final static public int FD_OK = 2;
	final static public int FD_FALSE = 3;
	
	/*催缴异常类型:1_电费计算异常,2_表计异常,3_停电异常,4_复电异常*/
	final static public int YH_YCSTATE_DFJS = 1;
	final static public int YH_YCSTATE_BJYC = 2;
	final static public int YH_YCSTATE_TDYC = 3;
	final static public int YH_YCSTATE_FDYC = 4;
	
	/*异常子类型:3_停电异常子类型*/
	final static public int YH_TD_ERROR = 3000; /*停电没有成功执行*/
	final static public int YH_TD_RWBH = 3001; /*无催费任务来源*/
	final static public int YH_TD_BJYC = 3002; /*量测信息有误*/
	final static public int YH_TD_ZCBH = 3003; /*停电用户的表计资产号有误*/
	
	/*异常子类型:3_复电异常子类型*/
	final static public int YH_FD_ERROR = 4000; /*复电没有成功执行*/
	final static public int YH_FD_JSJE = 4001;/*查询复电用户结算金额有误*/
	
	/*任务类型*/
	final static public int RWLX_SF = 1;/*电费计算任务*/ 
	final static public int RWLX_FX = 2;/*欠费分析任务*/ 
	final static public int RWLX_DX = 3;/*短信任务*/ 
	final static public int RWLX_TD = 4;/*停电任务*/ 
	final static public int RWLX_FD = 5;/*复电任务*/ 
	final static public int RWLX_TDCX = 6;/*停电查询任务*/ 
	final static public int RWLX_FDCX = 7;/*复电查询任务*/ 
	final static public int RWLX_SJQL =8;/*数据清理任务*/
	
	

	

	
	
}
