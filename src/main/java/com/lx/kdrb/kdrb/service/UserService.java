package com.lx.kdrb.kdrb.service;


import com.lx.kdrb.kdrb.domain.User;

/**
 * @Author:WL
 * @Date:2020/6/19 23:40
 */
public interface UserService {

	/**
	 * 保存注册用户
	 * @param user
	 * @return
	 */
	int insertUser(User user);

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User selectUser(User user);
}
