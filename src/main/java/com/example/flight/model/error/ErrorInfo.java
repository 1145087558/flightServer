package com.example.flight.model.error;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private Integer code;
    private String message;
    private String url;
    private String data;

}
