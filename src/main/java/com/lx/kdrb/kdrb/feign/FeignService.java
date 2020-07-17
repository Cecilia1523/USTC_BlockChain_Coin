package com.lx.kdrb.kdrb.feign;

import com.lx.kdrb.kdrb.domain.Account;
import com.lx.kdrb.kdrb.domain.JsonResult;
import com.lx.kdrb.kdrb.domain.TransactionVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:WL
 * @Date:2020/7/3 1:51
 */
@FeignClient(value = "user",url =  "${addr.url}")
public interface FeignService {

	/**
	 * 注册钱包
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/account/new_account",method = RequestMethod.GET)
	JsonResult newAccount() throws Exception;

	/**
	 * 开始挖矿
	 */
	@RequestMapping(value = "/api/chain/activeMining",method = RequestMethod.POST)
	JsonResult mining(@RequestBody Account account);

	/**
	 * 查询所有节点信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/account/list",method = RequestMethod.GET)
	JsonResult  list() throws Exception;

	/**
	 * 转账
	 */
	@RequestMapping(value = "/api/transaction/send_transaction",method = RequestMethod.POST)
	JsonResult sendTransaction(@RequestBody TransactionVo to);

	/**
	 * 根据hash查询交易状态
	 */
	@RequestMapping(value = "/api/transaction/get_transaction",method = RequestMethod.GET)
	JsonResult getTransactionByTxHash(@RequestParam("txHash") String txHash);
}
