package com.ustc.blockchain.db;

import com.google.common.base.Optional;
import com.ustc.blockchain.account.Account;
import com.ustc.blockchain.core.Block;
import com.ustc.blockchain.core.Transaction;
import com.ustc.blockchain.net.base.Node;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据库操作接口
 * @author USTC_Group
 * @since 19-4-10
 */
//@Component
public interface DBAccess {

	/**
	 * 区块数据存储 hash 桶前缀
	 */
	String BLOCKS_BUCKET_PREFIX = "blocks_";
	/**
	 * 钱包数据存储 hash 桶前缀
	 */
	String WALLETS_BUCKET_PREFIX = "wallets_";

	/**
	 * 挖矿账户
	 */
	String MINER_ACCOUNT = "miner_account";

	/**
	 * 最后一个区块的区块高度
	 */
	String LAST_BLOCK_INDEX = BLOCKS_BUCKET_PREFIX+"last_block";

	/**
	 * 客户端节点列表存储 key
	 */
	String CLIENT_NODES_LIST_KEY = "client-node-list";

	/**
	 * 更新最新一个区块的Hash值
	 * @param lastBlock
	 * @return
	 */
	boolean putLastBlockIndex(Object lastBlock);

	/**
	 * 获取最新一个区块的Hash值
	 * @return
	 */
	Optional<Object> getLastBlockIndex();

	/**
	 * 保存区块
	 * @param block
	 * @return
	 */
	boolean putBlock(Block block);

	/**
	 * 获取指定的区块, 根据区块高度去获取
	 * @param blockIndex
	 * @return
	 */
	Optional<Block> getBlock(Object blockIndex);

	/**
	 * 获取最后（最大高度）一个区块
	 * @return
	 */
	Optional<Block> getLastBlock();

	/**
	 * 添加一个钱包账户
	 * @param account
	 * @return
	 */
	boolean putAccount(Account account);

	/**
	 * 获取指定的钱包账户
	 * @param address
	 * @return
	 */
	Optional<Account> getAccount(String address);

	/**
	 * 获取所有账户列表
	 * @return
	 */
	List<Account> getAllAccounts();

	/**
	 * 获取挖矿账户
	 * @return
	 */
	Optional<Account> getMinerAccount();

	/**
	 * 设置挖矿账户
	 * @param account
	 * @return
	 */
	boolean setMinerAccount(Account account);

	/**
	 * 获取客户端节点列表
	 * @return
	 */
	Optional<List<Node>> getNodeList();

	/**
	 * 添加一个客户端节点
	 * @param node
	 * @return
	 */
	boolean addNode(Node node);

	/**
	 * 往数据库添加|更新一条数据
	 * @param key
	 * @param value
	 * @return
	 */
	boolean put(String key, Object value);

	/**
	 * 获取某一条指定的数据
	 * @param key
	 * @return
	 */
	Optional<Object> get(String key);

	/**
	 * 删除一条数据
	 * @param key
	 * @return
	 */
	boolean delete(String key);

	/**
	 * 根据前缀搜索
	 * @param keyPrefix
	 * @return
	 */
	<T> List<T> seekByKey(String keyPrefix);

	/**
	 * 根据交易哈希搜索交易
	 * @param txHash
	 * @return
	 */
	Transaction getTransactionByTxHash(String txHash);

	/**
	 * 关闭数据库
	 */
	void closeDB();
}
