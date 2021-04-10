package com.usian.pojo;

import java.io.Serializable;

public class ItemPreUpdate implements Serializable {
    private String itemCat;
    private TbItem item;
    private String itemDesc;
    private String itemParamItem;

    public String getItemCat() {
        return itemCat;
    }

    public void setItemCat(String itemCat) {
        this.itemCat = itemCat;
    }

    public TbItem getItem() {
        return item;
    }

    public void setItem(TbItem item) {
        this.item = item;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;

    }

    public String getItemParamItem() {
        return itemParamItem;
    }

    public void setItemParamItem(String itemParamItem) {
        this.itemParamItem = itemParamItem;
    }
}
