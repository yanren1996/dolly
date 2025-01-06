package com.example.dolly.model.vo;

/**
 * 用於取得使用者列表
 * orm會幫我們去除password欄位
 */
public interface UserVo {
    String getUsername();
}
