package com.lx.kdrb.kdrb.service;


import com.lx.kdrb.kdrb.domain.KdrbAccount;

/**
 * @Author:WL
 * @Date:2020/7/1 21:33
 */
public interface KdrbAccountService {

	/**
	 * 保存钱包信息
	 * @param kdrbAccount
	 * @return
	 */
	int insertAccount(KdrbAccount kdrbAccount);

	KdrbAccount selectAccountByUid(String id);


}
