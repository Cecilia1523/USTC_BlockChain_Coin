package com.ustc.blockchain.event;

import org.springframework.context.ApplicationEvent;

import com.ustc.blockchain.core.Transaction;

/**
 * 发送交易事件
 * @author USTC_Group
 */
public class NewTransactionEvent extends ApplicationEvent {

    public NewTransactionEvent(Transaction transaction) {
        super(transaction);
    }

}
