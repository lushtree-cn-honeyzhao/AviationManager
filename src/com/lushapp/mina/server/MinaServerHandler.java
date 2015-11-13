/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.mina.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lushapp.mina.server.service.MinaSomeServer;

import java.net.InetSocketAddress;

/**
 * mina服务端的的事件处理类
 *
 * @author honey.zhao@aliyun.com  
 */
public class MinaServerHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

    private MinaSomeServer minaSomeServer;

    public void setMinaSomeServer(MinaSomeServer minaSomeServer) {
        this.minaSomeServer = minaSomeServer;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        try {
            logger.info("Cilent: " + session.getRemoteAddress() + " is close the connection.");
        } catch (Exception e) {
        }
    }

    /**
     * 服务端接收消息
     */
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {

        //byte[] data = ObjectAndByte.toByteArray(message);
        String msg = message.toString();
        System.out.println("msg:\n" + msg);
        /*IoBuffer ioBuffer = (IoBuffer)message;
        byte[] b = new byte[ioBuffer.limit()];
        ioBuffer.get(b);
        String msg2=new String(b);
        ioBuffer.wrap(msg2.getBytes("GBK"));*/

//        String result = minaSomeServer.doSome(JsDes.s52d(msg));
//        session.write(JsDes.s52e(result));
        IoBuffer ioBuffer = IoBuffer.wrap(minaSomeServer.doSome(msg).getBytes("UTF-8"));
        //session.write(minaSomeServer.doSome(msg).getBytes("GBK"));
        session.write(ioBuffer);

//        if ("quit".equals(msg)) {
//            session.close(false);
//            return;
//        }
        //对客户端做出的响应
//        for (Iterator<String> it = sessions.keySet().iterator(); it.hasNext();) {
//            IoSession ss = sessions.get(it.next());
//            ss.write("message from server:: " + session.getRemoteAddress() + " say: " + msg);//返回客户端发送过来的消息
//        }
    }

    /**
     * 客户端连接的会话创建
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        InetSocketAddress isa = (InetSocketAddress) session.getRemoteAddress();
        logger.info("Cilent: " + isa.getAddress().getHostAddress() + "" + isa.getPort() + " is connected to the server.");
//        sessions.put(session.getRemoteAddress().toString(), session);
    }

//    private ConcurrentMap<String, IoSession> sessions = new ConcurrentHashMap<String, IoSession>();
}
