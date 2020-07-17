package com.lx.kdrb.kdrb.domain;

import java.io.Serializable;

/**
 *功能描述
 * @author wl
 * @description 用户信息表
 * @date 2020/6/19
 */
public class User  implements Serializable {

	/** id*/
	private String id;
	/** 用户名*/
	private String username;
	/** 密码*/
	private String password;
	/** 手机号*/
	private String phone;
	/** 邮箱*/
	private String email;

	/** 是否开通钱包*/
	private String isHasWallet;

	/** 验证码*/
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsHasWallet() {
		return isHasWallet;
	}

	public void setIsHasWallet(String isHasWallet) {
		this.isHasWallet = isHasWallet;
	}
}
