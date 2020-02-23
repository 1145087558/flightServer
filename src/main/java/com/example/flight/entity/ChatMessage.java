package com.example.flight.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class ChatMessage implements Serializable {

    private Integer toId;//接收者用户ID
    private Integer fromId;//发送者用户ID
    private String content;//发给某个用户的信息
    private String userName;//接收者的名称
    private Integer status;//发送者状态（0为用户，1为客服）
    private Integer type;
}
