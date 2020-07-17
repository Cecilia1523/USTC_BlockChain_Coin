package com.lx.kdrb.kdrb.service.impl;


import com.lx.kdrb.kdrb.domain.User;
import com.lx.kdrb.kdrb.mapper.UserMapper;
import com.lx.kdrb.kdrb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

;
/**
 * @Author:WL
 * @Date:2020/6/19 23:41
 */

/**
 *功能描述 
 * @author qqg
 * @description
 * @date 2020/6/19 
 */
@Service
public class UserServiceImpl  implements UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 保存注册用户
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public int insertUser(User user) {
		//判断用户是否存在
		User existUser =userMapper.selectUserByUserName(user.getUsername());
		if (existUser==null){
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			user.setId(uuid);
			user.setIsHasWallet("1");
			int i =userMapper.insertUser(user);
			return i;
		}
		return -1;
	}

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	@Override
	public User selectUser(User user) {
		return userMapper.selectUser(user);
	}
}
