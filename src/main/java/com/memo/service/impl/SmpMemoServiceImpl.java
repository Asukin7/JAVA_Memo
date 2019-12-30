package com.memo.service.impl;

import com.memo.dao.SmpMemoDao;
import com.memo.entity.SmpMemo;
import com.memo.service.SmpMemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("smpMemoService")
public class SmpMemoServiceImpl implements SmpMemoService {

    @Resource
    private SmpMemoDao smpMemoDao;

    @Override
    public List<SmpMemo> list(Map<String, Object> map) {
        return smpMemoDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return smpMemoDao.getTotal(map);
    }

    @Override
    public SmpMemo getById(Integer id) {
        return smpMemoDao.getById(id);
    }

    @Override
    public Integer add(SmpMemo smpMemo) {
        return smpMemoDao.add(smpMemo);
    }

    @Override
    public Integer update(SmpMemo smpMemo) {
        return smpMemoDao.update(smpMemo);
    }

    @Override
    public Integer delete(Integer id) {
        return smpMemoDao.delete(id);
    }

}
