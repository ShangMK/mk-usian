package com.usian.pojo;

import java.io.Serializable;

public class OrderINfo implements Serializable {
    private String v;
    private TbOrder tbOrder;
    private TbOrderShipping tbOrderShipping;

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public TbOrder getTbOrder() {
        return tbOrder;
    }

    public void setTbOrder(TbOrder tbOrder) {
        this.tbOrder = tbOrder;
    }

    public TbOrderShipping getTbOrderShipping() {
        return tbOrderShipping;
    }

    public void setTbOrderShipping(TbOrderShipping tbOrderShipping) {
        this.tbOrderShipping = tbOrderShipping;
    }
}
