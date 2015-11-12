/**
 *  Copyright (c) 2014-2013 http://www.lushapplication.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.youyun.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.internal.NotNull;
import com.youyun.common.orm.PropertyType;
import com.youyun.common.orm.annotation.Delete;
import com.youyun.common.orm.entity.BaseEntity;
import com.youyun.modules.sys._enum.MessageType;

/**
 * 通知管理-反馈消息Entity
 * @author : honey.zhao@aliyun.com  
 * @date: 13-11-27 下午9:18
 */
@Entity
@Table(name = "T_SYS_MESSAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class Message extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
	/**
     * 站内消息类型
     * @see com.youyun.modules.sys._enum.MessageType
     */
    private Integer msgType;
    /**
     * 标题
     */
    private String msgTitle;
    
    /**
     * 内容
     */
    private String msgContent;
    /**
     * 操作开始时间
     */
    private Date sendTime;
    /**
     * 发送人
     */
    private User msgSendUser;
    /**
     * 接收人
     */
    private User msgReceiverUser;
    /**
     * 发送状态0: 未读 1：已读
     */
    private Integer msgStatus;
    /**
     * 消息信箱  0收件箱 1发件箱 2草稿箱 3垃圾箱
     */
    private Integer msgBox;
    

    public Message() {
    }

    @Column(name="msg_type")
    @NotNull
	public Integer getMsgType() {
		return msgType;
	}


	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	@Column(name="msg_title",length=255)
	@NotNull
	public String getMsgTitle() {
		return msgTitle;
	}


	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	@Column(name="msg_content",length=2000)
	public String getMsgContent() {
		return msgContent;
	}


	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Column(name="send_time")
	public Date getSendTime() {
		return sendTime;
	}


	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msg_send_user")
	public User getMsgSendUser() {
		return msgSendUser;
	}

	public void setMsgSendUser(User msgSendUser) {
		this.msgSendUser = msgSendUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msg_receiver_user")
	public User getMsgReceiverUser() {
		return msgReceiverUser;
	}


	public void setMsgReceiverUser(User msgReceiverUser) {
		this.msgReceiverUser = msgReceiverUser;
	}

	@Column(name="msg_status")
	public Integer getMsgStatus() {
		return msgStatus;
	}


	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}

	@Column(name="msg_box")
	public Integer getMsgBox() {
		return msgBox;
	}


	public void setMsgBox(Integer msgBox) {
		this.msgBox = msgBox;
	}
	
	/**
	 * 状态描述
	 */
	@Transient
	public String getMsgTypeView() {
		MessageType s = MessageType.getMessageType(msgType);
		String str = "";
		if(s != null){
			str =  s.getDescription();
		}
		return str;
	}
    
}