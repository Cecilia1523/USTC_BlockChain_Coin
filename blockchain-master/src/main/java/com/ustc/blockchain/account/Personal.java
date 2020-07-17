package com.ustc.blockchain.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ustc.blockchain.crypto.ECKeyPair;
import com.ustc.blockchain.crypto.Keys;
import com.ustc.blockchain.db.DBAccess;

import java.math.BigDecimal;

/**
 * 账户控制工具类, 锁定，解锁等操作
 * 
 * @author USTC_Group
 * @since 19-4-6
 */
@Component
public class Personal {

	@Autowired
	private DBAccess dbAccess;

	/**
	 * 创建一个默认账户
	 * 
	 * @return
	 */
	public Account newAccount() throws Exception {
		ECKeyPair keyPair = Keys.createEcKeyPair();
		return newAccount(keyPair);
	}

	/**
	 * 使用指定的秘钥创建一个默认账户
	 * 
	 * @param keyPair
	 * @return
	 */
	public Account newAccount(ECKeyPair keyPair) {
		Account account = new Account(keyPair.getAddress(), keyPair.exportPrivateKey(), BigDecimal.ZERO);
		dbAccess.putAccount(account); // 存储账户
		return account;
	}
}
