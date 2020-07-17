package com.lx.kdrb.kdrb.controller;
/**
 * @Author:WL
 * @Date:2020/6/19 22:40
 */


import com.lx.kdrb.kdrb.common.enetity.Result;
import com.lx.kdrb.kdrb.common.enetity.StatusCode;
import com.lx.kdrb.kdrb.domain.JsonResult;
import com.lx.kdrb.kdrb.domain.User;
import com.lx.kdrb.kdrb.feign.FeignService;
import com.lx.kdrb.kdrb.service.UserService;
import com.lx.kdrb.kdrb.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述
 *
 * @author qqg
 * @description 用户信息
 * @date 2020/6/19
 */
@RequestMapping("/user")
@ResponseBody
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Resource
	private RedisTemplate redisTemplate;
	@Autowired
	private FeignService feignService;

	/**
	 * 用户注册
	 */
	@PostMapping("/register")
	public Result register(@RequestBody User user) {
		//校验参数是否为空
		if (user==null){
			return new Result(false, StatusCode.ERROR,"参数异常");
		}

		//判断验证码是否正确
		String code = user.getCode();
		String phone = user.getPhone();
		String existCode = redisTemplate.opsForValue().get(phone + "register").toString();
		if (code==null||existCode==null||!code.equals(existCode)){
			return new Result(false,StatusCode.ERROR,"验证码不正确");
		}
		int i = userService.insertUser(user);
		if (i == -1) {
			return new Result(false, StatusCode.ERROR, "用户已存在");
		}
		return new Result(true, StatusCode.OK, "注册成功");
	}

	/**
	 * 用户登录
	 */
	@PostMapping("/login")
	public Result login(@RequestBody User user, HttpServletResponse response) {
		User existUser = userService.selectUser(user);
		if (existUser != null) {
			//登录成功
			String token = UUID.randomUUID().toString().replaceAll("-","");
			Map map =new HashMap();
			map.put("loginMessage",existUser);
			//保存到redis中
			redisTemplate.boundValueOps(token).set(map);
			Cookie loginCookie =new Cookie("token",token);
			loginCookie.setMaxAge(1000);
			loginCookie.setPath("/");
			response.addCookie(loginCookie);
			return new Result(true, StatusCode.OK, "登录成功", existUser);
		}
		return new Result(false, StatusCode.ERROR, "登录失败");
	}

	/**
	 * 获取用户信息
	 */
	@GetMapping("/getUserImformation")
	public Result getUserImformation(HttpServletRequest request, HttpServletResponse response){
		String token = CookieUtil.readCookie(request, "token").get("token");
		if (token!=null){
			Map map = (Map)redisTemplate.opsForValue().get(token);
			if (map==null){
				return new Result(false,StatusCode.ERROR,"获取信息失败,请重新登录");
			}
			if (!map.isEmpty()){
				User user = (User)map.get("loginMessage");
				User existUser = userService.selectUser(user);
				if (existUser!=null){
					return new Result(true,StatusCode.OK,"获取信息成功",existUser);
				}
			}

		}

		return new Result(false,StatusCode.ERROR,"获取信息失败,请重新登录");

	}

	/**
	 * 申请钱包
	 */
	@GetMapping("/test")
	public void test() throws Exception{
		JsonResult jsonResult = feignService.newAccount();

		System.out.println(jsonResult.getItem());

		System.out.println("111");
	}
}