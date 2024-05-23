package com.example.spmall.mapper;

import com.example.spmall.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * UserMapper
 *
 * @author toy aa@nfda
 * @version 2023/10/14 12:29 PM
 * @since JDK11
 **/
@Mapper
public interface UserMapper {
    @Select("select id, username, password from user where username = #{username} and password = SHA2(#{password}, 256)")
    User checkUser(User user);

    User getUserInfo(String username);

    List<User> getUserList(String keyword);
    void insertUser(User user);

    void updateUser(User user);
    @Delete("delete from user where  id = #{id}")
    void deleteUser(Integer id);
    @Select("select id, name from roles")
    List<Routes> getRoutes();

    @Select("select id, name from buttons")
    List<Buttons> getButtons();

    @Select("select id, name, create_time, update_time from roles")
    List<Roles> getRoles();

    void insertUserRoles(UserRole userRole);

    @Select("select id, user_id, role_id, role_name from user_role where user_id = #{userId} and role_id = #{roleId}")
    UserRole getUserRole(UserRole userRole);

    void deleteUserRoles(List<UserRole> userRoleList);
    @Delete("delete from user_role where user_id = #{userId}")
    void deleteUserRolesByUserId(Integer userId);

    /**
     * 여러개 유저 삭제
     * @param userIds
     */
    void deleteUsers(List<Integer> userIds);

    List<Roles> getAllRoles(String keyword);
    /**
     * new insert to roles
     **/
    @Insert("insert into roles (name, create_time, update_time) values (#{name}, #{createTime},#{updateTime})")
    void insertRoles(Roles baseRole);

    /**
     * update roles
     * @param baseRole
     */
    @Update("update roles set name= #{name}, update_time = #{updateTime} where id = #{id}")
    void updateRoles(Roles baseRole);
    @Delete("delete from roles where id = #{id}")
    void deleteRoles(Roles baseRole);
    @Select("select * from roles where id in (select role_id from user_role where user_id = #{userId})")
    List<Roles> getUserRoles(Integer userId);
}
