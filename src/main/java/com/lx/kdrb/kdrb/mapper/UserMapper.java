package com.lx.kdrb.kdrb.mapper; /**
 * @Author:WL
 * @Date:2020/6/21 12:21
 */


import com.lx.kdrb.kdrb.domain.User;

/**
 *功能描述 
 * @author qqg
 * @description
 * @date 2020/6/21 
 */

public interface UserMapper {
	public User selectUserByUserName(String username);

	int insertUser(User user);

	/**
	 * 查找用户
	 * @param user
	 * @return
	 */
	User selectUser(User user);


	void applyAccount();


}
