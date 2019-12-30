package com.memo.dao;

import com.memo.entity.SmpMemo;

import java.util.List;
import java.util.Map;

public interface SmpMemoDao {

    /**带参数查找所有*/
    public List<SmpMemo> list(Map<String, Object> map);

    /**带参数查找总数*/
    public Long getTotal(Map<String, Object> map);

    /**根据ID查找*/
    public SmpMemo getById(Integer id);

    /**添加一条*/
    public Integer add(SmpMemo smpMemo);

    /**修改一条*/
    public Integer update(SmpMemo smpMemo);

    /**根据ID删除一条*/
    public Integer delete(Integer id);

}
