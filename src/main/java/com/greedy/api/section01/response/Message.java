package com.greedy.api.section01.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter
@ToString
public class Message {

    private int httpStatusCode;
    private String message;
}
