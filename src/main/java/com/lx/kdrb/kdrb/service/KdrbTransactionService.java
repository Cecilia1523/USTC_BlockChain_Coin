package com.lx.kdrb.kdrb.service;

import com.lx.kdrb.kdrb.common.enetity.PageResult;
import com.lx.kdrb.kdrb.common.enetity.QueryPageBean;
import com.lx.kdrb.kdrb.domain.KdrbTransaction;

/**
 * @Author:WL
 * @Date:2020/7/2 21:47
 */
public interface KdrbTransactionService {

	void insertKdrbTransaction(KdrbTransaction kdrbTransaction);

	PageResult selectToHistory(QueryPageBean queryPageBean, String payAddress);

	PageResult reciveHistory(QueryPageBean queryPageBean, String address);
}
