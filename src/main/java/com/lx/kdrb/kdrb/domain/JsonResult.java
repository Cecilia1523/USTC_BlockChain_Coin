package com.lx.kdrb.kdrb.domain;/**
 * @Author:WL
 * @Date:2020/7/3 22:06
 */

/**
 *功能描述 
 * @author qqg
 * @description 访问区块链返回数据
 * @date 2020/7/3 
 */
public class JsonResult {

	private int code;
	private String message;
	private Object item;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
}
