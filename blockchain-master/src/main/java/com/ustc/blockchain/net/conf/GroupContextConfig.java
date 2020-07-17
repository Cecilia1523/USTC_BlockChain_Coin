package com.ustc.blockchain.net.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.server.ServerGroupContext;

import com.ustc.blockchain.net.client.AppClientAioHandler;
import com.ustc.blockchain.net.client.AppClientAioListener;
import com.ustc.blockchain.net.server.AppServerAioHandler;
import com.ustc.blockchain.net.server.AppServerAioListener;

/**
 * Group context 配置
 * @author USTC_Group
 * @since 19-4-18
 */
@Configuration
public class GroupContextConfig {

	@Autowired
	TioProps tioProps;

	/**
	 * 客户端消息 handler, 包括编码、解码、消息处理
	 */
	@Autowired
	AppClientAioHandler clientAioHandler;

	/**
	 * 客户端事件监听器
	 */
	@Autowired
	AppClientAioListener clientAioListener;

	/**
	 * 服务端消息 handler, 包括编码、解码、消息处理
	 */
	@Autowired
	AppServerAioHandler serverAioHandler;

	/**
	 * 服务端事件监听器
	 */
	@Autowired
	AppServerAioListener serverAioListener;

	/**
	 * 客户端一组连接共用的上下文对象
	 * @return
	 */
	@Bean
	public ClientGroupContext clientGroupContext() {

		//断链后自动连接
		ReconnConf reconnConf = new ReconnConf(5000L, 20);
		ClientGroupContext clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener, reconnConf);
		//设置心跳包时间间隔
		clientGroupContext.setHeartbeatTimeout(tioProps.getHeartTimeout());
		return clientGroupContext;
	}

	/**
	 * 服务端一组连接共用的上下文对象
	 * @return
	 */
	@Bean
	public ServerGroupContext serverGroupContext() {

		ServerGroupContext serverGroupContext = new ServerGroupContext(
				tioProps.getServerGroupContextName(),
				serverAioHandler,
				serverAioListener);
		serverGroupContext.setHeartbeatTimeout(tioProps.getHeartTimeout());

		return serverGroupContext;
	}

}
