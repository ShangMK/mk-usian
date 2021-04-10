package com.usian.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ItemCatId implements Serializable {
    private Integer id;
    private Integer itemCatId;
    private String created;
    private String updated;
    private List<HashMap<String,String>> paramData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(Integer itemCatId) {
        this.itemCatId = itemCatId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<HashMap<String, String>> getParamData() {
        return paramData;
    }

    public void setParamData(List<HashMap<String, String>> paramData) {
        this.paramData = paramData;
    }
}
