package com.memo.controller;

import com.memo.entity.SmpMemo;
import com.memo.entity.SmpPlace;
import com.memo.service.SmpMemoService;
import com.memo.service.SmpPlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/funAPI")
public class FunAPIController {

    @Resource
    private SmpMemoService smpMemoService;

    @Resource
    private SmpPlaceService smpPlaceService;

    @ResponseBody
    @RequestMapping("/str")
    public Map<String, Object> str(@RequestParam Map<String, Object> map) throws Exception {
        if(map.get("str") == null) {
            return null;
        }
        String str = map.get("str").toString();
        if(str.contains("天气")) {
            //查询
            List<SmpPlace> smpPlaceList = smpPlaceService.getByStr(map);
            //将数据写入response
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("funAPI", "weather");
            result.put("rows", smpPlaceList);
            return result;
        }
        else if(str.contains("闹钟")) {
            String[] hhs = {"零点","一点","二点","三点","四点","五点","六点","七点","八点","九点","十点","十一点","十二点","十三点","十四点","十五点","十六点","十七点","十八点","十九点","二十点","二十一点","二十二点","二十三点","二十四点"};
            String[] hhs0 = {"0点","1点","2点","3点","4点","5点","6点","7点","8点","9点","10点","11点","12点","13点","14点","15点","16点","17点","18点","19点","20点","21点","22点","23点","24点"};
            int hh = -1;
            for (int i = 0; i < hhs.length; i++) {
                if(str.contains(hhs[i])) hh = i;
            }
            for (int i = 0; i < hhs0.length; i++) {
                if(str.contains(hhs0[i])) hh = i;
            }
            if(hh == -1) return null;

            String[] mms = {"点零","点一","点二","点三","点四","点五","点六","点七","点八","点九","点十",
                    "点十一","点十二","点十三","点十四","点十五","点十六","点十七","点十八","点十九","点二十",
                    "点二十一","点二十二","点二十三","点二十四","点二十五","点二十六","点二十七","点二十八","点二十九","点三十",
                    "点三十一","点三十二","点三十三","点三十四","点三十五","点三十六","点三十七","点三十八","点三十九","点四十",
                    "点四十一","点四十二","点四十三","点四十四","点四十五","点四十六","点四十七","点四十八","点四十九","点五十",
                    "点五十一","点五十二","点五十三","点五十四","点五十五","点五十六","点五十七","点五十八","点五十九"};
            String[] mms0 = {"点0","点1","点2","点3","点4","点5","点6","点7","点8","点9","点10",
                    "点11","点12","点13","点14","点15","点16","点17","点18","点19","点20",
                    "点21","点22","点23","点24","点25","点26","点27","点28","点29","点30",
                    "点31","点32","点33","点34","点35","点36","点37","点38","点39","点40",
                    "点41","点42","点43","点44","点45","点46","点47","点48","点49","点50",
                    "点51","点52","点53","点54","点55","点56","点57","点58","点59"};
            int mm = 0;
            for (int i = 0; i < mms.length; i++) {
                if(str.contains(mms[i])) mm = i;
            }
            for (int i = 0; i < mms0.length; i++) {
                if(str.contains(mms0[i])) mm = i;
            }

            Date setDate = new Date();
            if(setDate.getHours() >= hh && setDate.getMinutes() >= mm) setDate.setDate(setDate.getDate()+1);
            setDate.setHours(hh);
            setDate.setMinutes(mm);
            setDate.setSeconds(0);

            SmpMemo smpMemo = new SmpMemo();
            smpMemo.setContent("闹钟");
            smpMemo.setIsRemind(1);
            smpMemo.setRemindDate(setDate);

            smpMemoService.add(smpMemo);

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("funAPI", "alarm");
            return result;
        }
        return null;
    }

}
