/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.mina.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 解码
 * @author honey.zhao@aliyun.com  
 * @date 2014-7-4 下午12:44:45 
 * @version 1.0
 */
public class MyDecoder implements ProtocolDecoder {
    private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
    private final Charset charset;
    private int maxPackLength = 80;
    private boolean flag = false;
    private long lastrevtime = 0;
    private long waitTime = 50 * 1000;
    int dataLen = 0;


    public MyDecoder(Charset charset) {
        this.charset = charset;
    }

    public int getMaxLineLength() {
        return maxPackLength;
    }

    public void setMaxLineLength(int maxLineLength) {
        if (maxLineLength <= 0) {
            throw new IllegalArgumentException("maxLineLength: " + maxLineLength);
        }
        this.maxPackLength = maxLineLength;
    }

    private Context getContext(IoSession session) {
        Context ctx;
        ctx = (Context) session.getAttribute(CONTEXT);
        if (ctx == null) {
            ctx = new Context();
            session.setAttribute(CONTEXT, ctx);
        }
        return ctx;
    }

//    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
////        System.out.println(in.getPrefixedString(1, charset.newDecoder()));
//        if (in.prefixedDataAvailable(prefixLength, prefixLength)) {
//            String msg = in.getPrefixedString(prefixLength, charset.newDecoder());
//            out.write(msg);
//            return true;
//        }
////        out.write("11111111");
//
//        return false;
//    }

    /**
     * Decodes binary or protocol-specific content into higher-level message objects.
     * MINA invokes {@link #decode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)}
     * method with read data, and then the decoder implementation puts decoded
     * messages into {@link org.apache.mina.filter.codec.ProtocolDecoderOutput}.
     *
     * @throws Exception if the read data violated protocol specification
     */

    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int packHeadLength = 4;
        Context ctx = getContext(session);
        ctx.append(in);
        IoBuffer buf = ctx.getBuffer();
        if (!flag) {
            if (buf.position() >= packHeadLength) {
                int oldPos = buf.position();
                int oldLimit = buf.limit();
                buf.flip();
                buf.limit(packHeadLength);
                String len = buf.getString(ctx.getDecoder());
                buf.position(oldPos);
                buf.limit(oldLimit);
                dataLen = Integer.parseInt(len);
                flag = true;
            }
        }
        if (buf.position() >= (dataLen + packHeadLength) && dataLen != 0) {
            buf.flip();
            buf.limit(dataLen + packHeadLength);
            try {
                String text = buf.getString(ctx.getDecoder());
                out.write(text);
            } finally {
                flag = false;
                buf.clear();
            }
        }
    }

    public void decode_old(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int packHeadLength = 1;
        Context ctx = getContext(session);
        int lenlen = 0;
        ctx.append(in);
        IoBuffer buf = ctx.getBuffer();
        if (!flag) {
            if (buf.position() >= packHeadLength) {
                int oldPos = buf.position();
                int oldLimit = buf.limit();
                buf.flip();
                buf.limit(packHeadLength);
                String len = buf.getString(ctx.getDecoder());
                buf.position(oldPos);
                buf.limit(oldLimit);
                lenlen = Integer.parseInt(len);
                buf.flip();
                buf.limit(lenlen + packHeadLength);
                len = buf.getString(ctx.getDecoder());
                buf.position(oldPos);
                buf.limit(oldLimit);
                dataLen = Integer.parseInt(len.substring(1));
                flag = true;
            }
        }
        if (buf.position() >= (dataLen + packHeadLength + lenlen) && dataLen != 0) {
            buf.flip();
            buf.limit(dataLen + packHeadLength + lenlen);
            try {
                String text = buf.getString(ctx.getDecoder());
                out.write(text);
            } finally {
                flag = false;
                buf.clear();
            }
        }
    }


    /**
     * Invoked when the specified <tt>session</tt> is closed.  This method is useful
     * when you deal with the protocol which doesn't specify the length of a message
     * such as HTTP response without <tt>content-length</tt> header. Implement this
     * method to process the remaining data that {@link #decode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)}
     * method didn't process completely.
     *
     * @throws Exception if the read data violated protocol specification
     */
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Releases all resources related with this decoder.
     *
     * @throws Exception if failed to dispose all resources
     */
    public void dispose(IoSession session) throws Exception {
        Context ctx = (Context) session.getAttribute(CONTEXT);
        if (ctx != null) {
            session.removeAttribute(CONTEXT);
        }
    }

    private class Context {
        private final CharsetDecoder decoder;
        private final IoBuffer buf;

        public int getDataLen() {
            return dataLen;
        }

        private int dataLen;
        private int lenPosition = 0;

        private Context() {
            decoder = charset.newDecoder();
            buf = IoBuffer.allocate(maxPackLength).setAutoExpand(true);
        }

        public CharsetDecoder getDecoder() {
            return decoder;
        }

        public IoBuffer getBuffer() {
            return buf;
        }

        public int getOverflowPosition() {
            return lenPosition;
        }

        public void reset() {
            lenPosition = 0;
            dataLen = 0;
            decoder.reset();
        }

        public void append(IoBuffer in) {
            getBuffer().put(in);
        }
    }
}
