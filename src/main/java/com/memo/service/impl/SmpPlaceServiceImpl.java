package com.memo.service.impl;

import com.memo.dao.SmpPlaceDao;
import com.memo.entity.SmpPlace;
import com.memo.service.SmpPlaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("smpPlaceService")
public class SmpPlaceServiceImpl implements SmpPlaceService {

    @Resource
    private SmpPlaceDao smpPlaceDao;

    @Override
    public List<SmpPlace> getByStr(Map<String, Object> map) {
        return smpPlaceDao.getByStr(map);
    }

}

