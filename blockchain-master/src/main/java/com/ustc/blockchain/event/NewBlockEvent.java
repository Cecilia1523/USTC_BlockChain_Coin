package com.ustc.blockchain.event;

import org.springframework.context.ApplicationEvent;

import com.ustc.blockchain.core.Block;

/**
 * 挖矿事件，当挖到一个新的区块将会触发该事件
 * @author USTC_Group
 */
public class NewBlockEvent extends ApplicationEvent {

    public NewBlockEvent(Block block) {
        super(block);
    }
}
