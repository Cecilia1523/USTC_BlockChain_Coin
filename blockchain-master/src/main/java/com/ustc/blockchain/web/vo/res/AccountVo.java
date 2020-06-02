package com.ustc.blockchain.web.vo.res;

import com.ustc.blockchain.account.Account;

/**
 * account VO
 * @author USTC_Group
 * @since 19-7-14
 */
public class AccountVo extends Account {

	@Override
	public String toString() {
		return "AccountVo{" +
				"address='" + address + '\'' +
				", priKey='" + priKey + '\'' +
				'}';
	}
}
