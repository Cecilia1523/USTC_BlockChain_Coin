package com.ustc.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ustc.blockchain.account.Account;
import com.ustc.blockchain.account.Personal;
import com.ustc.blockchain.conf.AppConfig;
import com.ustc.blockchain.db.DBAccess;

import java.io.File;

/**
 * @author USTC_Group
 * @since 2019-06-03 下午5:18.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

	static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);
	@Autowired
	private DBAccess dbAccess;

	@Autowired
	private Personal personal;

	@Autowired
	private AppConfig appConfig;

	@Override
	public void run(ApplicationArguments arguments) throws Exception{

		// 首次运行，执行一些初始化的工作
		File lockFile = new File(System.getProperty("user.dir")+"/"+ appConfig.getDataDir()+"/node.lock");
		if (!lockFile.exists()) {
			lockFile.createNewFile();
			// 创建默认钱包地址（挖矿地址）
			Account minerAccount = personal.newAccount();
			dbAccess.setMinerAccount(minerAccount);
			logger.info("Create miner account : {}", minerAccount);
		}

	}

}
