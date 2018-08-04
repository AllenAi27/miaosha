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
	public static CodeMsg NOT_LOGIN = new CodeMsg(500103, "用户尚未登录,请登录!");
	
	//登录模块 5002XX
	
	//商品模块 5003XX
	
	//订单模块 5004XX
	public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500401, "订单不存在!"); 
	
	//秒杀模块 5005XX
	public static CodeMsg MIAO_SHA_STOCK_EMPTY = new CodeMsg(500500, "秒杀商品已卖完!");
	public static CodeMsg MIAO_SHA_REPEAT_ACTION = new CodeMsg(500500, "您已经秒杀过该商品了,不可重复秒杀!");
	
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
