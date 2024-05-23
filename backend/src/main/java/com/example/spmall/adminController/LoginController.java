package com.example.spmall.adminController;

import com.example.spmall.pojo.Result;
import com.example.spmall.pojo.User;
import com.example.spmall.adminService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * LoginController
 *
 * @author toy aa@nfda
 * @version 2023/10/14 11:08 AM
 * @since JDK11
 **/
@Slf4j
@RestController
@RequestMapping("/admin-api/admin")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/acl/index/login")
    public Result login(@RequestBody User user) {
       return userService.login(user);
    }

    /**
     * 로그인후 token을 이용해 유저 정보 가져오기
     * @param
     * @return
     */
    @GetMapping("/acl/index/info")
    public Result userInfo(@RequestHeader String token){
       return  userService.findUserByToken(token);

    }

}
