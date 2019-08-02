package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.util.List;

public class Category extends BaseResult{

    private List<Entity> info;

    public List<Entity> getInfo() {
        return info;
    }

    public void setInfo(List<Entity> info) {
        this.info = info;
    }

    public static class Entity{
        private int id;
        private String name;
        private int pid;
        private int type;
        private List<Entity> category;

        public List<Entity> getCategory() {
            return category;
        }

        public void setCategory(List<Entity> category) {
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
