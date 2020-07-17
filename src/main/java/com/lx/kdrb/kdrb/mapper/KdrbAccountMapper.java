package com.lx.kdrb.kdrb.mapper;


import com.lx.kdrb.kdrb.domain.KdrbAccount;

/**
 * @Author:WL
 * @Date:2020/7/1 21:41
 */
public interface KdrbAccountMapper {

	int insertAccount(KdrbAccount account);

	KdrbAccount selectAccountByUid(String uid);
}
