package com.ustc.blockchain.web.controller.api;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.ustc.blockchain.account.Account;
import com.ustc.blockchain.conf.AppConfig;
import com.ustc.blockchain.core.BlockChain;
import com.ustc.blockchain.core.Transaction;
import com.ustc.blockchain.core.TransactionPool;
import com.ustc.blockchain.crypto.Credentials;
import com.ustc.blockchain.db.DBAccess;
import com.ustc.blockchain.utils.JsonVo;
import com.ustc.blockchain.web.vo.req.TransactionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author USTC_Group
 */
@RestController
@RequestMapping("/api/transaction")
@Api(tags = "Transaction API", description = "交易相关 API")
public class TransactionController {

	@Autowired
	private DBAccess dbAccess;
	@Autowired
	private BlockChain blockChain;
	@Autowired
	private AppConfig appConfig;
	@Autowired
	private TransactionPool transactionPool;

	/**
	 * 发送交易
	 * @param txVo
	 * @return
	 */
	@ApiOperation(value="发送交易", notes="发起一笔资金交易")
	@ApiImplicitParam(name = "txVo", required = true, dataType = "TransactionVo")
	@PostMapping("/send_transaction")
	public JsonVo sendTransaction(@RequestBody TransactionVo txVo) throws Exception {
		Preconditions.checkNotNull(txVo.getTo(), "Recipient is needed.");
		Preconditions.checkNotNull(txVo.getAmount(), "Amount is needed.");
		Preconditions.checkNotNull(txVo.getPriKey(), "Private Key is needed.");
		Credentials credentials = Credentials.create(txVo.getPriKey());
		// 验证余额
		Optional<Account> account = dbAccess.getAccount(credentials.getAddress());
		if (!account.isPresent()) {
			return JsonVo.instance(JsonVo.CODE_FAIL, "账户余额不足");
		}
		if (account.get().getBalance().compareTo(txVo.getAmount()) < 0) {
			return JsonVo.instance(JsonVo.CODE_FAIL, "账户余额不足");
		}
		Transaction transaction = blockChain.sendTransaction(
				credentials,
				txVo.getTo(),
				txVo.getAmount(),
				txVo.getData());

		//如果开启了自动挖矿，则直接自动挖矿
		if (appConfig.isAutoMining()) {
			blockChain.mining();
		}
		JsonVo success = JsonVo.success();
		success.setItem(transaction.getTxHash());
		return success;
	}

	/**
	 * 根据交易哈希查询交易状态
	 * @param txHash
	 * @return
	 */
	@ApiOperation(value="查询交易状态", notes="根据交易哈希查询交易状态")
	@ApiImplicitParam(name = "txHash", value = "交易哈希", required = true, dataType = "String")
	@GetMapping("/get_transaction")
	public JsonVo getTransactionByTxHash(String txHash)
	{
		
		System.out.println("交易的hash\t" + txHash);
		Preconditions.checkNotNull(txHash, "txHash is needed.");
		JsonVo success = JsonVo.success();
		// 查询交易池
		System.out.println("查询交易池");
		for (Transaction tx: transactionPool.getTransactions()) {
			System.out.println("查询\t" + txHash + "\t" + tx.getTxHash());
			if (txHash.equals(tx.getTxHash())) {
				success.setItem(tx);
				return success;
			}
		}
		// 查询区块
		Transaction transaction = dbAccess.getTransactionByTxHash(txHash);
		if (null != transaction) {
			success.setItem(transaction);
			return success;
		}

		System.out.println("查询失败");
		success.setCode(JsonVo.CODE_FAIL);
		return success;
	}

}
