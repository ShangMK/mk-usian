package com.usian.pojo;

import java.io.Serializable;
import java.util.Date;


public class SearchItem implements Serializable {
    private Long id;

    private String item_title;

    private String item_sell_point;

    private Long item_price;

    private String item_image;

    private String item_category_name;

    private String item_desc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_sell_point() {
        return item_sell_point;
    }

    public void setItem_sell_point(String item_sell_point) {
        this.item_sell_point = item_sell_point;
    }

    public Long getItem_price() {
        return item_price;
    }

    public void setItem_price(Long item_price) {
        this.item_price = item_price;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_category_name() {
        return item_category_name;
    }

    public void setItem_category_name(String item_category_name) {
        this.item_category_name = item_category_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }
}
