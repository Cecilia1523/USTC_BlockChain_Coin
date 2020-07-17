package com.lx.kdrb.kdrb.controller;/**
 * @Author:WL
 * @Date:2020/6/30 22:20
 */

import com.aliyuncs.exceptions.ClientException;
import com.lx.kdrb.kdrb.common.enetity.Result;
import com.lx.kdrb.kdrb.common.enetity.StatusCode;
import com.lx.kdrb.kdrb.utils.SMSUtils;
import com.lx.kdrb.kdrb.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *功能描述
 * @author qqg
 * @description 短信发送
 * @date 2020/6/30
 */
@RequestMapping("/sendMessage")
@RestController
public class MessageController {
	@Autowired
	private RedisTemplate redisTemplate;

	//注册短信发送
	@GetMapping("/register/{phone}")
	public Result sendRegisterMessage(@PathVariable("phone") String phone) throws ClientException {
		Integer code = ValidateCodeUtils.generateValidateCode(6);
		System.out.println(code.toString());
		boolean result = SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, phone, code.toString());
		if (result){
			//短信保存到redis
			redisTemplate.boundValueOps(phone+"register").set(code.toString());
			return new Result(true, StatusCode.OK,"短信发送成功");
		}
		return new Result(false,StatusCode.ERROR,"短信发送失败");
	}

	//申请钱包短信发送
	@GetMapping("/sendApplyWalletMessage/{phone}")
	public Result sendApplyWalletMessage(@PathVariable("phone") String phone) throws ClientException {
		String code = ValidateCodeUtils.generateValidateCode(6).toString();
		System.out.println(code);
		boolean result = SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, phone, code);
		if (result){
			//短信保存到redis
			redisTemplate.boundValueOps(phone+"applyWallet").set(code);
			return new Result(true, StatusCode.OK,"短信发送成功");
		}
		return new Result(false,StatusCode.ERROR,"短信发送失败");
	}
}
