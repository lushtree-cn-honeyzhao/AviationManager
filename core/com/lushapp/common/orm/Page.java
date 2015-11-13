/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.orm;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.lushapp.common.utils.StringUtils;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 * 
 * @author honey.zhao@aliyun.com  
 */
public class Page<T> {
	//-- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	public static final int DEFAULT_PAGESIZE = 15;

	//-- 分页参数 --//
	protected int pageNo = 1;
	protected int pageSize = -1;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;

	//-- 返回结果 --//
	protected List<T> result = Lists.newArrayList();
	protected long totalCount = -1;

    private int totalPage = -1; // 总页数

    private int first;// 首页索引
    private int last;// 尾页索引
    private int prev;// 上一页索引
    private int next;// 下一页索引

    private boolean firstPage;//是否是第一页
    private boolean lastPage;//是否是最后一页


	//-- 构造函数 --//
	public Page() {
	}

	public Page(int pageSize) {
        this(0, pageSize);
	}

    /**
     * 构造方法
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     */
    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, 0);
    }

    /**
     * 构造方法
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     * @param count 数据条数
     */
    public Page(int pageNo, int pageSize, long count) {
        this(pageNo, pageSize, count, new ArrayList<T>());
    }

    /**
     * 构造方法
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     * @param count 数据条数
     * @param list 本页数据对象列表
     */
    public Page(int pageNo, int pageSize, long count, List<T> list) {
        this.setTotalCount(count);
        this.setPageNo(pageNo);
        this.pageSize = pageSize;
        this.setResult(list);
//        initialize();
    }


    /**
     * 初始化参数
     */
    public void initialize(){

        //1
        this.first = 1;

        this.last = (int)(totalCount / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);

        if (this.totalCount % this.pageSize != 0 || this.last == 0) {
            this.last++;
        }

        if (this.last < this.first) {
            this.last = this.first;
        }

        if (this.pageNo <= 1) {
            this.pageNo = this.first;
            this.firstPage=true;
        }

        if (this.pageNo >= this.last) {
            this.pageNo = this.last;
            this.lastPage=true;
        }

        if (this.pageNo < this.last - 1) {
            this.next = this.pageNo + 1;
        } else {
            this.next = this.last;
        }

        if (this.pageNo > 1) {
            this.prev = this.pageNo - 1;
        } else {
            this.prev = this.first;
        }

        //2
        if (this.pageNo < this.first) {// 如果当前页小于首页
            this.pageNo = this.first;
        }

        if (this.pageNo > this.last) {// 如果当前页大于尾页
            this.pageNo = this.last;
        }

    }


	//-- 分页参数访问函数 --//
	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 返回Page对象自身的setPageNo函数,可用于连续设置。
	 */
	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量, 默认为-1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回Page对象自身的setPageSize函数,可用于连续设置。
	 */
	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * 获得排序字段,无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 返回Page对象自身的setOrderBy函数,可用于连续设置。
	 */
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	/**
	 * 获得排序方向, 无默认值.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		String lowcaseOrder = StringUtils.lowerCase(order);

		//检查order字符串的合法值
		String[] orders = StringUtils.split(lowcaseOrder, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = lowcaseOrder;
	}

	/**
	 * 返回Page对象自身的setOrder函数,可用于连续设置。
	 */
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * 获得查询对象时是否先自动执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 设置查询对象时是否自动先执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * 返回Page对象自身的setAutoCount函数,可用于连续设置。
	 */
	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	//-- 访问查询结果函数 --//

	/**
	 * 获得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 获得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}


    /**
     * 设置总页数
     * @param totalPage
     * @author honey.zhao@aliyun.com  
     * @date   2014-7-29 上午3:46:25
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}


    /**
     * 尾页索引
     * @return
     */
    @JsonIgnore
    public int getLast() {
        return last;
    }

    /**
     * 获取页面总数
     * @return getLast();
     */
    @JsonIgnore
    public int getTotalPage() {
        return getLast();
    }

    /**
     * 是否为第一页
     * @return
     */
    @JsonIgnore
    public boolean isFirstPage() {
        return firstPage;
    }

    /**
     * 是否为最后一页
     * @return
     */
    @JsonIgnore
    public boolean isLastPage() {
        return lastPage;
    }

    /**
     * 上一页索引值
     * @return
     */
    @JsonIgnore
    public int getPrev() {
        if (isFirstPage()) {
            return pageNo;
        } else {
            return pageNo - 1;
        }
    }

    /**
     * 下一页索引值
     * @return
     */
    @JsonIgnore
    public int getNext() {
        if (isLastPage()) {
            return pageNo;
        } else {
            return pageNo + 1;
        }
    }

    /**
     * 获取 Hibernate FirstResult
     */
    public int getFirstResult(){
        int firstResult = (getPageNo() - 1) * getPageSize();
        if (firstResult >= getTotalCount()) {
            firstResult = 0;
        }
        return firstResult;
    }

    public int getLastResult(){
        int lastResult = getFirstResult()+getMaxResults();
        if(lastResult>getTotalCount()) {
            lastResult =(int) getTotalCount();
        }
        return lastResult;
    }
    /**
     * 获取 Hibernate MaxResults
     */
    public int getMaxResults(){
        return getPageSize();
    }

    public static int countOffset(final int pageSize, final int pageNo) {
        final int offset = pageSize * (pageNo - 1);
        return offset;
    }

    public static int countTotalPage(final int pageSize, final long totalCount) {
        int pages = (int) (totalCount % pageSize == 0 ? totalCount / pageSize : totalCount
                / pageSize + 1);
        return pages;
    }
}
