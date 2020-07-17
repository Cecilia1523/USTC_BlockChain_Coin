package com.lx.kdrb.kdrb.controller;/**
 * @Author:WL
 * @Date:2020/6/30 23:34
 */


import com.lx.kdrb.kdrb.common.enetity.Result;
import com.lx.kdrb.kdrb.common.enetity.StatusCode;
import com.lx.kdrb.kdrb.domain.Account;
import com.lx.kdrb.kdrb.domain.JsonResult;
import com.lx.kdrb.kdrb.domain.KdrbAccount;
import com.lx.kdrb.kdrb.domain.User;
import com.lx.kdrb.kdrb.feign.FeignService;
import com.lx.kdrb.kdrb.service.KdrbAccountService;
import com.lx.kdrb.kdrb.service.UserService;
import com.lx.kdrb.kdrb.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述
 *
 * @author qqg
 * @description 钱包
 * @date 2020/6/30
 */
@RequestMapping("/wallet")
@RestController
public class KdrbAccountController {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private KdrbAccountService kdrbAccountService;
	@Autowired
	private UserService userService;
	@Autowired
	private FeignService feignService;

	//申请钱包
	@PostMapping("/applyWallet")
	public Result applyWallet(@RequestBody Map<String, Object> map, HttpServletRequest request) throws Exception {
		//判断用户是否登录
		String token = CookieUtil.readCookie(request, "token").get("token");
		if (token != null) {
			Map mapToken = (Map) redisTemplate.opsForValue().get(token);
			if (mapToken != null) {
				User user = (User) mapToken.get("loginMessage");
				if (user != null) {
					User existUser = userService.selectUser(user);
					if (existUser != null) {
						if (map == null) {
							return new Result(false, StatusCode.ERROR, "参数异常");
						}

						String phone = map.get("phone").toString();
						String code = map.get("code").toString();
						String existCode = redisTemplate.opsForValue().get(phone + "applyWallet").toString();
						if (code == null || existCode == null || !code.equals(existCode)) {
							return new Result(false, StatusCode.ERROR, "验证码不正确");
						}
						//申请钱包
						JsonResult jsonResult = feignService.newAccount();
						if (jsonResult.getCode() == 200) {
							Map item = (Map) jsonResult.getItem();
							KdrbAccount kdrbAccount = new KdrbAccount();
							kdrbAccount.setAddress(item.get("address").toString());
							kdrbAccount.setPriKey(item.get("priKey").toString());
							kdrbAccount.setBalance(BigDecimal.valueOf(Integer.valueOf(item.get("balance").toString())));
							kdrbAccount.setId(UUID.randomUUID().toString());
							kdrbAccount.setUid(existUser.getId());
							//保存钱包信息
							kdrbAccountService.insertAccount(kdrbAccount);
							return new Result(true, StatusCode.OK, "钱包申请成功");
						}
						return new Result(false, StatusCode.ERROR, "申请钱包失败");
					}
				}
			}

		}
		return new Result(false, StatusCode.ERROR, "登录失效,请重新登录");

	}

	//查询钱包信息
	@GetMapping("/getAccountMessage")
	public Result getAccountMessage(HttpServletRequest request) throws Exception {

		String token = CookieUtil.readCookie(request, "token").get("token");
		Map map = (Map) redisTemplate.opsForValue().get(token);
		if (map != null) {
			User loginMessage = (User) map.get("loginMessage");
			if (loginMessage != null) {
				User user = userService.selectUser(loginMessage);
				if (user != null) {
					JsonResult jsonResult = feignService.list();

					System.out.println(jsonResult);
					//查询用户的钱包信息
					KdrbAccount kdrbAccount = kdrbAccountService.selectAccountByUid(user.getId());
					List<Map> item = (List<Map>) jsonResult.getItem();
					item.forEach(info -> {
						if (info.get("address").equals(kdrbAccount.getAddress())) {
							kdrbAccount.setBalance(BigDecimal.valueOf(Integer.valueOf(info.get("balance").toString())));
						}
					});
					return new Result(true, StatusCode.OK, "余额查询成功", kdrbAccount);
				}
			}
		}

		return new Result(false, StatusCode.ERROR, "余额查询失败");
	}


	/***
	 * 开始挖矿
	 */
	@GetMapping("/mining")
	public Result mining(HttpServletRequest request) {
		String token = CookieUtil.readCookie(request, "token").get("token");
		Map map = (Map) redisTemplate.opsForValue().get(token);
		if (map != null) {
			User loginMessage = (User) map.get("loginMessage");
			if (loginMessage != null) {
				User user = userService.selectUser(loginMessage);
				if (user != null) {
					KdrbAccount kdrbAccount = kdrbAccountService.selectAccountByUid(user.getId());
					Account account =new Account();
					account.setAddress(kdrbAccount.getAddress());
					account.setPriKey(kdrbAccount.getPriKey());
					account.setBalance(kdrbAccount.getBalance());
					JsonResult mining = feignService.mining(account);
					if (mining.getCode()==200){
						return new Result(true,StatusCode.OK,"开始挖矿");
					}
					return new Result(false,StatusCode.ERROR,"挖矿失败");
				}
			}
		}
		return new Result(false, StatusCode.ERROR, "请重新登录");
	}
}
