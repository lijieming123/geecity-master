package net.bluemap.geecitypoperty.common.model;

/**
 * 用于简单的key-value值对应的实体
 * Created by LiuPeng on 2015/10/24.
 */
public class KeyValueBean {
    private String key;
    private String Value;

    public KeyValueBean() {
    }

    public KeyValueBean(String key, String value) {
        this.key = key;
        Value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
