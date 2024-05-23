package com.example.spmall.adminService.impl;

import com.example.spmall.mapper.UserMapper;
import com.example.spmall.pojo.*;
import com.example.spmall.adminService.UserService;
import com.example.spmall.utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author toy aa@nfda
 * @version 2023/10/14 11:10 AM
 * @since JDK11
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result login(User user) {

         // 1. 받은 값이 유효한지 확인
        String username = user.getUsername();
        String password = user.getPassword();
        if(!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
             return Result.error("매개변수가 정확하지않습니다");
        }

        log.info("로그인: {}", user);
        log.info("----: {}", userMapper.getUserInfo(user.getUsername()));
        User u = userMapper.checkUser(user);
        log.info("user:{}", u);
        if(u == null) {
            return Result.error("유저아이디 또는 비밀번호 오류");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        String jwt = JwtUtils.generateJwt(claims);

        // 저장
        return Result.success(jwt);
    }

    @Override
    public Result findUserByToken(String token) {
        // 1. token이 존재하는지 확인
        if (!StringUtils.hasText(token)) {
            return Result.error("유효하지 않습니다.");
        }
        try {
            Claims claims = JwtUtils.parseJWT(token);
            String username = (String) claims.get("username");
            User userInfo = userMapper.getUserInfo(username);

            return Result.success(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("유효기간 초과");
        }
    }

    @Override
    public PageBean getUserList(Integer page, Integer pageSize, String keyword) {
        // 1.page
        PageHelper.startPage(page, pageSize);
        // 2. 리서치 시작
        List<User> userList = userMapper.getUserList(keyword);
        Page<User> pageList = (Page<User>)userList;

        PageBean pageBean = new PageBean(pageList.getTotal(), pageList.getResult());
        return pageBean;
    }

    @Override
    public void addUser(User user) {
        LocalDateTime now= LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        log.info("user: {}", user);
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        LocalDateTime now= LocalDateTime.now();
        user.setUpdateTime(now);
       userMapper.updateUser(user);
    }

    /**
     * TODO
     * delete - user_role, user_button, user_route
     * @param id
     */
    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);

    }

    @Override
    public List<Roles> getRoles() {
        return userMapper.getRoles();
    }

    @Override
    public List<Buttons> getButtons() {
        return userMapper.getButtons();
    }

    @Override
    public List<Routes> getRoutes() {
        return userMapper.getRoutes();
    }

    @Override
    public void saveUserRoles(Integer userId,List<UserRole> userRolesList) {
        log.info("userRoleList11111111:{}", userRolesList);
        // role있는지 확인 없으면 insert
        if(userRolesList.size()> 0) {
            for (UserRole userRole: userRolesList) {
               if (userMapper.getUserRole(userRole) == null) {
                   userMapper.insertUserRoles(userRole);
               }
            }
            userMapper.deleteUserRoles(userRolesList);
        } else {
            userMapper.deleteUserRolesByUserId(userId);
        }


    }

    public void saveUserButtons(List<UserRole> userRoleList) {

    }

    public void saveUserRoutes(List<UserRole> userRoleList) {

    }

    @Override
    public void deleteUsers(List<Integer> userIds) {
        userMapper.deleteUsers(userIds);
    }

    @Override
    public PageBean getAllRoles(Integer page, Integer pageSize, String keyword) {
        // page
        PageHelper.startPage(page, pageSize);

        // 리서치 시작
        List<Roles> rolesList = userMapper.getAllRoles(keyword);
        log.info("rolesList:{}", rolesList);
        Page<Roles> pageInfo =(Page<Roles>) rolesList;

        PageBean pageBean = new PageBean(pageInfo.getTotal(), pageInfo.getResult());

        return pageBean;
    }

    @Override
    public void saveBaseRoles(Roles baseRole) {
        LocalDateTime localDateTime = LocalDateTime.now();
        baseRole.setUpdateTime(localDateTime);
        baseRole.setCreateTime(localDateTime);
        userMapper.insertRoles(baseRole);
    }

    @Override
    public void updateBaseRoles(Roles baseRole) {
        LocalDateTime localDateTime = LocalDateTime.now();
        baseRole.setUpdateTime(localDateTime);
        userMapper.updateRoles(baseRole);
    }

    @Override
    public void deleteRoles(Roles baseRole) {
        userMapper.deleteRoles(baseRole);
    }

    @Override
    public List<Roles> getUserRoles(Integer userId) {
        return userMapper.getUserRoles(userId);
    }


}
