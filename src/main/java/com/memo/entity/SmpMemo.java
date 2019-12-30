package com.memo.entity;

import java.io.Serializable;
import java.util.Date;

public class SmpMemo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    private Integer id;
    /**内容*/
    private String content;
    /**更新日期*/
    private Date updateDate;
    /**是否提醒*/
    private Integer isRemind;
    /**提醒日期*/
    private Date remindDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    @Override
    public String toString() {
        return "SmpMemo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", updateDate=" + updateDate +
                ", isRemind=" + isRemind +
                ", remindDate=" + remindDate +
                '}';
    }

}
