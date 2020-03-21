package com.example.flight.model.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class PhoneInfo implements Serializable {

    private String phone;
    private String phoneCode;

    public PhoneInfo(String phone, String phoneCode) {
        this.phone = phone;
        this.phoneCode = phoneCode;
    }
}
