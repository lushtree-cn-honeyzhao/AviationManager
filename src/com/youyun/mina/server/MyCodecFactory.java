/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.mina.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;

/**
 * <style type="text/css">body{background:#C7EDCC;}</style>
 * 这个类集成自TextLineCodecFactory, 表示对mina的数据的解析方式.
 * 本来可以直接用TextLineCodecFactory的, 但这个类的目的是表示我们可以有自己的方式来解析.
 * MyCodecFactory.java
 *
 * @author honey.zhao@aliyun.com  
 */
public class MyCodecFactory implements ProtocolCodecFactory {
    private final MyEncoder encoder;
    private final MyDecoder decoder;

    public MyCodecFactory(Charset charset) {
        encoder = new MyEncoder(charset);
        decoder = new MyDecoder(charset);
    }

    public MyCodecFactory() {
        this(Charset.forName("UTF-8"));
        //this(Charset.forName("GBK"));
    }


    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}