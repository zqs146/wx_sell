package com.zqs.dao;


import java.util.List;

/**
 * 执行批量操作
 */
public interface BatchDao<T> {

    void batchInsert(List<T> list);

}