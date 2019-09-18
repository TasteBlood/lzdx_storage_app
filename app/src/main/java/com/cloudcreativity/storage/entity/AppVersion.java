package com.cloudcreativity.storage.entity;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/16 14:52
 * e-mail: xxw0701@sina.com
 */
public class AppVersion {

    private Entity info;

    public Entity getInfo() {
        return info;
    }

    public void setInfo(Entity info) {
        this.info = info;
    }

    public class Entity{
        private int id;
        private String num;
        private int mark;
        private String content;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
