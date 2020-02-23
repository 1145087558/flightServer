package com.example.flight.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName(value="flight_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type= IdType.AUTO)
	private Integer id;
	@TableField(value="user_name")
	private String userName;
	@TableField(value="user_password")
	private String password;
	@TableField(value="user_sex")
	private String sex;
	@TableField(value="user_age")
	private Integer age;
	@TableField(value="user_phone")
	private String phone;
	@TableField(value="user_email")
	private String email;
	@TableField(value="user_status")
	private Integer status;
	
}
