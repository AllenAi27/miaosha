package com.imooc.miaosha.result;

public class CodeMsg {
	private int code;
	
	private String msg;
	
	//通用异常
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端发生异常!");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验出错:%s");
	public static CodeMsg USER_NOT_EXIST = new CodeMsg(500101, "用户不存在!");
	public static CodeMsg LOGIN_ERROR = new CodeMsg(500102, "用户名或密码错误!");
	

	//登录模块 5002XX
	
	//商品模块 5003XX
	
	//订单模块 5004XX
	
	//秒杀模块 5005XX
	
	public CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = this.msg;
		String msg = String.format(message, args);
		return new CodeMsg(code,msg);
	}
	
}
