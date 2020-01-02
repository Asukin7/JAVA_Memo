package com.memo.service;

import com.memo.entity.SmpPlace;

import java.util.List;
import java.util.Map;

public interface SmpPlaceService {

    /**查询一句话中的地名*/
    public List<SmpPlace> getByStr(Map<String, Object> map);

}
