package com.yuwenxin.blog.model.enumpkg;

public enum  Gender {
    /**
     * 使用枚举类来表达性别
     */

    MALE("man"),FEMALE("women"),UNKNOWN("unknown");

    private String gender;
    Gender(String gender){
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.gender;
    }
}
