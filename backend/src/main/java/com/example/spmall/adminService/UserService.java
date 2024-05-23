package com.example.spmall.adminService;

import com.example.spmall.pojo.*;

import java.util.List;

/**
 * UserService
 *
 * @author toy aa@nfda
 * @version 2023/10/14 11:09 AM
 * @since JDK11
 **/
public interface UserService {
    Result login(User user);

    Result findUserByToken(String token);

    /**
     * userList 가져오기
     * @param page
     * @param pageSize
     * @param userName
     * @return
     */
   PageBean getUserList(Integer page, Integer pageSize, String userName);

    /**
     * 유저 추가
     * @param user
     */
    void addUser(User user);

    /**
     * 유저 업데이트
     * @param user
     */
    void updateUser(User user);

    /**
     * 삭제
     * @param id
     */
    void deleteUser(Integer id);

    /**
     * 모든 역할
     * @return
     */
    List<Roles> getRoles();

    /**
     * 모든 버튼
     * @return
     */
    List<Buttons> getButtons();

    /**
     * 모든 경로
     * @return
     */
    List<Routes> getRoutes();

    /**
     * user Role 저장하기
     * @param
     */
    void saveUserRoles(Integer userId, List<UserRole> userRolesList);

    /**
     * 여러개 유저 삭제
     * @param userIds
     */
    void deleteUsers(List<Integer> userIds);

 /**
  *
  * @param page
  * @param pageSize
  * @param keyword
  * @return
  */
 PageBean getAllRoles(Integer page, Integer pageSize, String keyword);

 /**
  * 저장하기
  * @param baseRole
  */
 void saveBaseRoles(Roles baseRole);

 /**
  * 업데이트 하기
  * @param baseRole
  */
 void updateBaseRoles(Roles baseRole);

 /**
  * 삭제하기
  * @param baseRole
  */
 void deleteRoles(Roles baseRole);

 /**
  *
  * @param userId
  * @return
  */
 List<Roles> getUserRoles(Integer userId);
}
