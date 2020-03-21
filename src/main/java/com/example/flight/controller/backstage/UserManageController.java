package com.example.flight.controller.backstage;

import com.example.flight.model.user.UserInfo;
import com.example.flight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserManageController {

    @Autowired
    private UserService userService;

    @PostMapping("manageLogin.form")
    public String userLogin(UserInfo user, HttpServletRequest request){

        user = userService.manageLogin(user);
        if(user!=null) {
            request.getSession().setAttribute("user", user);
            return "success";
        }else {
            return "fail";
        }
    }

    @GetMapping("getUserName.form")
    public String getUserName(HttpServletRequest request){

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        return userInfo.getUserName();
    }

    @GetMapping("getUserById.form/userId/{userId}")
    public UserInfo getUserById(@PathVariable int userId){
        return userService.getUserById(userId);
    }

    @GetMapping("getUserAllList.form")
    public List<UserInfo> getUserAllList(){

        return userService.getUserAllList();
    }

    @PostMapping("addUser.form")
    public void addUser(UserInfo userInfo){

       userService.register(userInfo);
    }

    @PutMapping("modifyStatus.form/userId/{userId}/{status}")
    public void modifyStatus(@PathVariable("userId")Integer userId,@PathVariable("status")int status){
     userService.modifyStatus(userId,status);
    }


    @PutMapping("memberPwd.form/userId/{userId}")
    public void memberPwd(@PathVariable("userId")Integer userId){
        UserInfo user = new UserInfo().setId(userId).setPassword("123456");
        userService.modifyUser(user);
    }

    @RequestMapping("modifyUser.form")
    public void modifyUser(UserInfo userInfo){
        userService.modifyUser(userInfo);
    }

    @GetMapping("getUserDeleteList.form")
    public List<UserInfo> getUserDeleteList(){

        return  userService.getUserDeleteList();
    }

    @DeleteMapping("deleteUser.form/userId/{userId}")
    public void deleteUser(@PathVariable("userId")Integer userId){
        userService.deleteUser(userId);
    }
}
