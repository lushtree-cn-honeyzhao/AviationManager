/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.mina.cilent;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lushapp.mina.server.MyCodecFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * mina向中心服务器发送并返回接收数据
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-15 下午5:26:58
 */
@Service
public class MinaCilentFactory {
    private static Logger logger = LoggerFactory.getLogger(MinaCilentFactory.class);

	private String host;//ip
    private int port; //端口
    private long timeOut;//连接超时设置

    /**
     * 交互xml实体数据
     * @param requestXml 请求xml实体
     * @return 响应xml数据
     * @throws Exception    
     * @date：    2011-12-15 下午5:30:26
     */
    public String doTask(String requestXml) throws Exception {
    	requestXml.replaceAll("\\r\n", "");//去除内容中的换行回车符\r\n 减少网络流量
        String result = null;
        NioSocketConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(timeOut);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new MyCodecFactory()));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        SocketSessionConfig cfg = connector.getSessionConfig();
        cfg.setUseReadOperation(true);
        IoSession session = null;
        try {
            session = connector.connect(new InetSocketAddress(host, port)).awaitUninterruptibly().getSession();
            // 发送
//            session.write(JsDes.s52e(content)).awaitUninterruptibly();
            // 接收
            ReadFuture readFuture = session.read();
            if (readFuture.awaitUninterruptibly(timeOut, TimeUnit.MILLISECONDS)) {
                result = (String) readFuture.getMessage();
            } else {
                logger.warn("接收数据连接超时!");
            	result = "TIMEOUT";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        	result = "NOSERVER";
        	logger.error("连接服务器失败!");
        } finally {
            // 断开
            try {
            	if(session != null){
            		session.close(true);
                    session.getService().dispose();
            	}
            } catch (Exception e) {
                logger.error(e.getMessage());
            	e.printStackTrace();
            }
        }
//        if(!"NOSERVER".equals(result) && !"TOMEOUT".equals(result) && result != null){
//        	return JsDes.s52d(result);//返回解码后数据
//        }
        return result;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }



    
}
