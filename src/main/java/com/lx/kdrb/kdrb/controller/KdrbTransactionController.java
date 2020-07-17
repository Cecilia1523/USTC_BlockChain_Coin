package com.lx.kdrb.kdrb.controller;/**
 * @Author:WL
 * @Date:2020/7/2 22:54
 */


import com.lx.kdrb.kdrb.common.enetity.PageResult;
import com.lx.kdrb.kdrb.common.enetity.QueryPageBean;
import com.lx.kdrb.kdrb.common.enetity.Result;
import com.lx.kdrb.kdrb.common.enetity.StatusCode;
import com.lx.kdrb.kdrb.domain.*;
import com.lx.kdrb.kdrb.feign.FeignService;
import com.lx.kdrb.kdrb.service.KdrbAccountService;
import com.lx.kdrb.kdrb.service.KdrbTransactionService;
import com.lx.kdrb.kdrb.service.UserService;
import com.lx.kdrb.kdrb.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述
 *
 * @author qqg
 * @description
 * @date 2020/7/2
 */
@RequestMapping("/kdrbTransaction")
@RestController
public class KdrbTransactionController {
	@Autowired
	private KdrbTransactionService kdrbTransactionService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private KdrbAccountService kdrbAccountService;
	@Autowired
	private FeignService feignService;

	/**
	 * 查询付款记录
	 * @param queryPageBean
	 * @param request
	 * @return
	 */
	@PostMapping("/toHistory")
	public Result toHistory(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request) {
		String token = CookieUtil.readCookie(request, "token").get("token");
		if (token != null) {
			Map mapToken = (Map) redisTemplate.opsForValue().get(token);
			if (mapToken != null) {
				User user = (User) mapToken.get("loginMessage");
				if (user != null) {
					User existUser = userService.selectUser(user);
					if (existUser != null) {
						KdrbAccount kdrbAccount = kdrbAccountService.selectAccountByUid(user.getId());
						PageResult pageResult = kdrbTransactionService.selectToHistory(queryPageBean, kdrbAccount.getAddress());
						return new Result(true,StatusCode.OK,"查询成功",pageResult);
					}
				}
			}
		}
		return new Result(false,StatusCode.ERROR,"登录失效,请重新登录");
	}


	@PostMapping("/payTo")
	public Result payTo(@RequestBody KdrbTransaction kdrbTransaction, HttpServletRequest request) {
		String token = CookieUtil.readCookie(request, "token").get("token");
		if (token != null) {
			Map mapToken = (Map) redisTemplate.opsForValue().get(token);
			if (mapToken != null) {
				User user = (User) mapToken.get("loginMessage");
				if (user != null) {
					User existUser = userService.selectUser(user);
					if (existUser != null) {
						if (kdrbTransaction == null) {
							return new Result(false, StatusCode.ERROR, "参数异常");
						}
						TransactionVo to = new TransactionVo();
						to.setTo(kdrbTransaction.getTo());
						to.setAmount(kdrbTransaction.getAmount());
						KdrbAccount kdrbAccount = kdrbAccountService.selectAccountByUid(existUser.getId());
						to.setPriKey(kdrbAccount.getPriKey());
						JsonResult jsonResult = feignService.sendTransaction(to);
						if (jsonResult.getCode() == 200) {
							kdrbTransaction.setFrom(kdrbAccount.getAddress());
							kdrbTransaction.setTxHash(jsonResult.getItem().toString());
							kdrbTransaction.setId(UUID.randomUUID().toString());
							kdrbTransaction.setTime(new Date());
							//保存交易信息
							kdrbTransactionService.insertKdrbTransaction(kdrbTransaction);
							return new Result(true, StatusCode.OK, "转账成功");
						}
						return new Result(false, StatusCode.ERROR, jsonResult.getMessage());
					}
				}
			}
		}
		return new Result(false, StatusCode.ERROR, "登录失效,请重新登录");
	}

	/**
	 * 查询收款记录
	 */
	@PostMapping("/reciveHistory")
	public Result reciveHistory(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request){
		String token = CookieUtil.readCookie(request, "token").get("token");
		if (token != null) {
			Map mapToken = (Map) redisTemplate.opsForValue().get(token);
			if (mapToken != null) {
				User user = (User) mapToken.get("loginMessage");
				if (user != null) {
					User existUser = userService.selectUser(user);
					if (existUser != null) {
						KdrbAccount kdrbAccount = kdrbAccountService.selectAccountByUid(user.getId());
						PageResult pageResult = kdrbTransactionService.reciveHistory(queryPageBean, kdrbAccount.getAddress());
						return new Result(true,StatusCode.OK,"查询成功",pageResult);
					}
				}
			}
		}
		return new Result(false,StatusCode.ERROR,"登录失效,请重新登录");
	}


	/**
	 * 付款
	 */
	@GetMapping("/recive")
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
					String address = kdrbAccount.getAddress();
					return new Result(true, StatusCode.OK, "地址显示成功", address);
				}
			}
		}

		return new Result(false, StatusCode.ERROR, "余额查询失败");
	}
}
