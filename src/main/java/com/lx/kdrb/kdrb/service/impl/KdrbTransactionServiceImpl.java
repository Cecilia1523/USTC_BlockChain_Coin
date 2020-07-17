package com.lx.kdrb.kdrb.service.impl;/**
 * @Author:WL
 * @Date:2020/7/2 21:47
 */


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lx.kdrb.kdrb.common.enetity.PageResult;
import com.lx.kdrb.kdrb.common.enetity.QueryPageBean;
import com.lx.kdrb.kdrb.domain.JsonResult;
import com.lx.kdrb.kdrb.domain.KdrbTransaction;
import com.lx.kdrb.kdrb.feign.FeignService;
import com.lx.kdrb.kdrb.mapper.KdrbTransactionMapper;
import com.lx.kdrb.kdrb.service.KdrbTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

/**
 *功能描述 
 * @author qqg
 * @description
 * @date 2020/7/2 
 */
@SpringBootApplication
public class KdrbTransactionServiceImpl implements KdrbTransactionService {
	@Autowired
	private KdrbTransactionMapper kdrbTransactionMapper;
	@Autowired
	private FeignService feignService;
	@Override
	public void insertKdrbTransaction(KdrbTransaction kdrbTransaction) {
		kdrbTransactionMapper.insertKdrbTransaction(kdrbTransaction);
	}



	@Override
	public PageResult selectToHistory(QueryPageBean queryPageBean, String payAddress) {

		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		Page<KdrbTransaction> page = kdrbTransactionMapper.selectToHistory(payAddress);
		if (page!=null){
			List<KdrbTransaction> rows = page.getResult();
			if (rows!=null){
				for (KdrbTransaction row : rows) {
					System.out.println("+++++++++++++"+row.getTime());
					JsonResult transactionByTxHash = feignService.getTransactionByTxHash(row.getTxHash());
					Map item = (Map) transactionByTxHash.getItem();
					if (item.get("status").toString().equals("APPENDING")){
						row.setStatus("交易成功");
					}
					if (item.get("status").toString().equals("SUCCESS")){
						row.setStatus("交易待确认");
					}
					if (item.get("status").toString().equals("FAIL")){
						row.setStatus("交易确认失败");
					}
				}

				return new PageResult(page.getTotal(),rows);
			}
		}
		return new PageResult(Long.valueOf(0),null);
	}

	@Override
	public PageResult reciveHistory(QueryPageBean queryPageBean, String address) {
		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		Page<KdrbTransaction> page = kdrbTransactionMapper.reciveHistory(address);
		if (page!=null){
			List<KdrbTransaction> rows = page.getResult();
			if (rows!=null){
				for (KdrbTransaction row : rows) {
					JsonResult transactionByTxHash = feignService.getTransactionByTxHash(row.getTxHash());
					Map item = (Map) transactionByTxHash.getItem();
					if (item.get("status").toString().equals("APPENDING")){
						row.setStatus("交易成功");
					}
					if (item.get("status").toString().equals("SUCCESS")){
						row.setStatus("交易待确认");
					}
					if (item.get("status").toString().equals("FAIL")){
						row.setStatus("交易确认失败");
					}
				}
				return new PageResult(page.getTotal(),rows);
			}
		}
		return new PageResult(Long.valueOf(0),null);
	}
}
