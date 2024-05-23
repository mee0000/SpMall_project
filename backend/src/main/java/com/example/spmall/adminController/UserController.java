package com.example.spmall.adminController;

import com.example.spmall.pojo.*;
import com.example.spmall.adminService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * UserCOntroller
 *
 * @author toy aa@nfda
 * @version 2024/01/24 2:28 AM
 * @since JDK11
 **/
@Slf4j
@RestController
@RequestMapping("/admin-api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * user 리스트
     * @param page
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/list/{page}/{pageSize}")
    public Result getList(@PathVariable Integer page,
                          @PathVariable Integer pageSize,
                          @RequestParam(required = false) String keyword) {
        PageBean pageBean = userService.getUserList(page, pageSize, keyword);
        return Result.success(pageBean);
    }

    /**
     * 새로 추가
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        userService.addUser(user);
        return Result.success();
    }

    /**
     * user 업데이트
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 삭제 --> 유저 한개
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 여러개 유저 삭제
     * @param userIds
     * @return
     */
    @DeleteMapping("/deleteUsers")
    public Result deleteUsers(@RequestBody List<Integer> userIds) {
        log.info("userIds: {}", userIds);
        userService.deleteUsers(userIds);
        return Result.success();
    }

    /**
     * AllRoles
     * @param page
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/getAllRoles/{page}/{pageSize}")
    public Result getAllRoles(@PathVariable Integer page,
                              @PathVariable Integer pageSize,
                              @RequestParam(required = false) String keyword
                              ) {
        return Result.success(userService.getAllRoles(page, pageSize, keyword));
    }

    /**
     * new Role 저장하기
     * @param baseRole
     * @return
     */
    @PostMapping("/saveRole")
    public Result saveBaseRole(@RequestBody Roles baseRole) {
        userService.saveBaseRoles(baseRole);
        return Result.success();
    }

    /**
     * update Role
     * @param baseRole
     * @return
     */
    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody Roles baseRole) {
        userService.updateBaseRoles(baseRole);
        return Result.success();
    }
    @DeleteMapping("/deleteRole")
    public Result deleteRoles(@RequestBody Roles baseRole) {
        userService.deleteRoles(baseRole);
        return Result.success();
    }
    /**
     * 기본 역할 리스트 가져오기
     * @return
     */
    @GetMapping("/getRoles")
    public Result getRoles() {
        return Result.success(userService.getRoles());
    }

    /**
     * 기본 버튼 리스트 가져오기
     * @return
     */
    @GetMapping("/getButtons")
    public Result getButtons() {
        return Result.success(userService.getButtons());
    }

    /**
     * 기본 경로 리스트 가져오기
     * @return
     */
    @GetMapping("/getRoutes")
    public Result getRoutes() {
        return Result.success(userService.getRoutes());
    }

    @PostMapping("/saveUserRoles/{userId}")
    public Result saveUserRoles (@PathVariable Integer userId, @RequestBody List<UserRole> userRolesList) {
        log.info("userRoleList: {}", userRolesList);
        userService.saveUserRoles(userId, userRolesList);
        return Result.success();
    }

    @GetMapping("/getUserRoles/{userId}")
    public Result getUserRoles (@PathVariable Integer userId) {
        return Result.success(userService.getUserRoles(userId));
    }

}
