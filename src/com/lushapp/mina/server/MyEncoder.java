/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.mina.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * 编码
 * @author honey.zhao@aliyun.com  
 * @date 2014-7-4 下午12:44:34 
 * @version 1.0
 */
public class MyEncoder extends ProtocolEncoderAdapter {
    private final AttributeKey ENCODER = new AttributeKey(getClass(), "encoder");
    private final Charset charset;

    public MyEncoder(Charset charset) {
        this.charset = charset;
    }

    public MyEncoder() {
        this(Charset.defaultCharset());
    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);
        if (encoder == null) {
            encoder = charset.newEncoder();
            session.setAttribute(ENCODER, encoder);
        }

        String value = message.toString();
        IoBuffer buf = IoBuffer.allocate(value.length())
                .setAutoExpand(true);
        buf.putString(value, encoder);
        if (buf.position() > 9999) {
            throw new IllegalArgumentException("Line length: " + buf.position());
        }
        //buf.putString(delimiter.getValue(), encoder);
        buf.flip();
        out.write(buf);
    }

    public void dispose() throws Exception {
    }
}
