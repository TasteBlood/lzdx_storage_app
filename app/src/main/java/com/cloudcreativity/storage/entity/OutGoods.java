package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 * @author : created by Xu Xiwu
 * date-time: 2019/8/7 18:04
 * e-mail: xxw0701@sina.com
 */
public class OutGoods extends BaseResult {

    private List<OutOrder.OutGoods> info;

    public List<OutOrder.OutGoods> getInfo() {
        return info;
    }

    public void setInfo(List<OutOrder.OutGoods> info) {
        this.info = info;
    }
}
