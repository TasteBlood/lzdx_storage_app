package com.cloudcreativity.storage.entity;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/10/18 15:27
 * e-mail: xxw0701@sina.com
 */
public class UserListWrapper {
    private List<UserEntity.Entity> info;

    public List<UserEntity.Entity> getInfo() {
        return info;
    }

    public void setInfo(List<UserEntity.Entity> info) {
        this.info = info;
    }
}
