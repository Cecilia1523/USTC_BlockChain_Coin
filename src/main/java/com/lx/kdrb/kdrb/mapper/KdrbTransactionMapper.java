package com.lx.kdrb.kdrb.mapper;


import com.github.pagehelper.Page;
import com.lx.kdrb.kdrb.domain.KdrbTransaction;

/**
 * @Author:WL
 * @Date:2020/7/2 21:50
 */
public interface KdrbTransactionMapper {
	void insertKdrbTransaction(KdrbTransaction kdrbTransaction);

	Page<KdrbTransaction> selectToHistory(String payAddress);

	Page<KdrbTransaction> reciveHistory(String address);
}
