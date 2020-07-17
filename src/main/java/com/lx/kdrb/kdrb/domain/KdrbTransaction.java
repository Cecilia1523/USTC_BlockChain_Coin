package com.lx.kdrb.kdrb.domain;/**
 * @Author:WL
 * @Date:2020/7/2 21:35
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 *功能描述 
 * @author qqg
 * @description 交易信息
 * @date 2020/7/2 
 */
public class KdrbTransaction {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 付款人地址
	 */
	private String from;



	/**
	 * 收款人地址
	 */
	private String to;


	/**
	 * 交易金额
	 */
	private BigDecimal amount;

	/**
	 * 交易 Hash 值
	 */
	private String txHash;

	/**
	 * 交易状态
	 */
	private String status;
	/**
	 * 交易时间
	 */
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
