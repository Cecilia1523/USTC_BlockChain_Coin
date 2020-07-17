package com.ustc.blockchain.net.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import com.ustc.blockchain.net.conf.TioProps;

/**
 * client端对各个server连接的情况回调。</p>
 * 当某个server的心跳超时（2min）时，Aio会从group里remove掉该连接，需要在重新connect后重新加入group
 *
 * @author USTC_Group
 */
@Component
public class AppClientAioListener implements ClientAioListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TioProps tioProps;

    @Override
    public void onAfterClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) throws Exception {
        logger.info("连接关闭：server地址为-" + channelContext.getServerNode());
        Aio.unbindGroup(channelContext);
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        if (isConnected) {
            logger.info("连接成功：server地址为-" + channelContext.getServerNode());
            Aio.bindGroup(channelContext, tioProps.getClientGroupName());
        } else {
            logger.info("连接失败：server地址为-" + channelContext.getServerNode());
        }
    }

    @Override
    public void onAfterReceived(ChannelContext channelContext, Packet packet, int i) throws Exception {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {

    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) {

    }
}
