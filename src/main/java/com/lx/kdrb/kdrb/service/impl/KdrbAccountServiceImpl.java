package com.lx.kdrb.kdrb.service.impl;


import com.lx.kdrb.kdrb.domain.KdrbAccount;
import com.lx.kdrb.kdrb.mapper.KdrbAccountMapper;
import com.lx.kdrb.kdrb.mapper.UserMapper;
import com.lx.kdrb.kdrb.service.KdrbAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KdrbAccountServiceImpl implements KdrbAccountService {
	@Autowired
	private KdrbAccountMapper kdrbAccountMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional
	public int insertAccount(KdrbAccount kdrbAccount) {
		//设置用户已经生成了钱包
		userMapper.applyAccount();
		return kdrbAccountMapper.insertAccount(kdrbAccount);
	}

	@Override
	public KdrbAccount selectAccountByUid(String uid) {
		return kdrbAccountMapper.selectAccountByUid(uid);
	}
}
