package com.lx.kdrb.kdrb.domain;/**
 * @Author:WL
 * @Date:2020/7/1 23:56
 */

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 钱包账户
 *
 * @author yangjian
 * @since 18-4-6
 */
public class KdrbAccount implements Serializable {

	/**
	 * id
	 */
	private String id;
	/**
	 * 用户id
	 */
	private String uid;
	/**
	 * 钱包地址
	 */
	protected String address;

	/**
	 * 钱包私钥
	 */
	protected String priKey;

	/**
	 * 账户余额
	 */
	protected BigDecimal balance;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPriKey() {
		return priKey;
	}

	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}


