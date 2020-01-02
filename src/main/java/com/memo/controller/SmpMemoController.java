package com.memo.controller;

import com.memo.entity.PageBean;
import com.memo.entity.SmpMemo;
import com.memo.service.SmpMemoService;
import com.memo.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/smpMemo")
public class SmpMemoController {

    @Resource
    private SmpMemoService smpMemoService;

    @ResponseBody
    @RequestMapping("/now")
    public Map<String, Object> now(@RequestParam Map<String, Object> map) throws Exception {
        map.put("isRemind", 1);
        if(map.get("remindDateStr") == null) {
            return null;
        }
        map.put("remindDateStr", StringUtil.formatLike(map.get("remindDateStr").toString()));
        //查询
        List<SmpMemo> smpMemoList = smpMemoService.list(map);
        Long total = smpMemoService.getTotal(map);
        //更新为已提醒
//        for (SmpMemo smpMemo : smpMemoList) {
//            smpMemo.setIsRemind(0);
//            smpMemoService.update(smpMemo);
//        }
        //将数据写入response
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", smpMemoList);
        result.put("total", total);
        return result;
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> map) throws Exception {
        if(map.get("page") != null && map.get("page") != "" && map.get("rows") != null && map.get("rows") != "") {
            PageBean pageBean = new PageBean(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()));
            map.put("start", pageBean.getStart());
            map.put("size", pageBean.getPageSize());
        }
        if(map.get("content") != null) {
            map.put("content", StringUtil.formatLike(map.get("content").toString()));
        }
        //查询
        List<SmpMemo> smpMemoList = smpMemoService.list(map);
        Long total = smpMemoService.getTotal(map);
        //将数据写入response
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", smpMemoList);
        result.put("total", total);
        return result;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Map<String, Object> save(@RequestBody SmpMemo smpMemo) throws Exception {
        Integer num = 0;
        if (smpMemo.getId() != null) {
            //修改
            num = smpMemoService.update(smpMemo);
        } else {
            //添加
            num = smpMemoService.add(smpMemo);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (num != null && num != 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> map) throws Exception {
        Integer num = 0;
        Integer id = Integer.parseInt(map.get("id").toString());
        if (id != null) {
            num = smpMemoService.delete(id);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (num != null && num != 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

}
