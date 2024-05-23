package com.example.spmall.adminController;

import com.example.spmall.pojo.Permission;
import com.example.spmall.pojo.RPermission;
import com.example.spmall.pojo.Result;
import com.example.spmall.adminService.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Permission
 *
 * @author toy aa@nfda
 * @version 2024/03/05 12:48 AM
 * @since JDK11
 **/
@Slf4j
@RestController
@RequestMapping("/admin-api/admin/permission")
public class PermissionController {
   @Autowired
    private PermissionService permissionService;

    /**
     * 리스트 가져오기
     * @return
     */
   @GetMapping("/list")
    public Result getPermissionList() {
       return Result.success(permissionService.getPermissionList());
   }

    /**
     * roleId에 의해 권한리스트를 가져오기
     * @param roleId
     * @return
     */
   @GetMapping("/toAssign/{roleId}")
    public Result getPermissionByRoleId(@PathVariable int roleId) {
       log.info("roleId: {}", roleId);
       return Result.success(permissionService.getPermissionByRolesId(roleId));
   }

    /**
     * roleId에 의한 권한 업데이트
     * @param roleId
     * @param rPermissions
     * @return
     */
   @PostMapping("/doAssignAcl/{roleId}")
    public Result updateAssign(@PathVariable int roleId, @RequestBody List<RPermission> rPermissions) {
       log.info("roleIds:{}", rPermissions);
       permissionService.setPermissionByIds(roleId, rPermissions);
       return Result.success();
   }

    /**
     * addPermission
     * @param permission
     * @return
     */
   @PostMapping("/addPermission")
    public Result addPermission(@RequestBody Permission permission) {
       log.info("insertPermission:{}", permission);
       permissionService.addPermission(permission);
       return Result.success();
   }

    /**
     * updatePermission
     * @param permission
     * @return
     */
    @PutMapping("/savePermission")
    public Result updatePermission(@RequestBody Permission permission) {
        log.info("updatePermission:{}", permission);
        permissionService.updatePermission(permission);
        return Result.success();
    }

    /**
     * 삭제하기
     * @param permission
     * @return
     */
    @PutMapping("/deletePermission")
    public Result removePermission(@RequestBody Permission permission) {
        permissionService.deletePermission(permission);
        return Result.success();
    }
}
