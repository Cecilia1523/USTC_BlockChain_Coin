package com.lx.kdrb.kdrb.domain;/**
 * @Author:WL
 * @Date:2020/7/2 20:52
 */

import java.math.BigDecimal;

/**
 *功能描述 
 * @author qqg
 * @description 交易信息
 * @date 2020/7/2 
 */
public class PayMent {

	/**
	 * 收款人地址
	 */
	private String to;

	/**
	 * 交易金额
	 */
	private BigDecimal amount;

	/**
	 * 验证码
	 */
	private String code;

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
