package com.memo.entity;

import java.io.Serializable;

public class SmpPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    private String id;
    /**地址*/
    private String pla;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPla() {
        return pla;
    }

    public void setPla(String pla) {
        this.pla = pla;
    }

    @Override
    public String toString() {
        return "SmpPlace{" +
                "id='" + id + '\'' +
                ", pla='" + pla + '\'' +
                '}';
    }

}
