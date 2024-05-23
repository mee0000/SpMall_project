package com.example.spmall.adminService;

import com.example.spmall.pojo.Permission;
import com.example.spmall.pojo.RPermission;

import java.util.List;

/**
 * PermissionService
 *
 * @author toy aa@nfda
 * @version 2024/03/05 12:49 AM
 * @since JDK11
 **/

public interface PermissionService {
    List<Permission> getPermissionList();

    /**
     * 선택된 roleId들의 집합
     * @param roleId
     */
    List<Integer> getPermissionByRolesId(int roleId);

    /**
     * setPermission-by-roleIds
     * @param permissionList
     */
    void setPermissionByIds(Integer roleId, List<RPermission> permissionList);

    /**
     * addPermission
     * @param permission
     */
    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    void deletePermission(Permission permission);
}
