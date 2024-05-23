package com.example.spmall.adminService.impl;

import com.example.spmall.mapper.PermissionMapper;
import com.example.spmall.pojo.Permission;
import com.example.spmall.pojo.RPermission;
import com.example.spmall.adminService.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * PermissionServiceImpl
 *
 * @author toy aa@nfda
 * @version 2024/03/05 12:50 AM
 * @since JDK11
 **/
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    private List<Permission> menuList;
    @Override
    public List<Permission> getPermissionList() {
        menuList = permissionMapper.getPermissionList();
        List<Permission> treeMenus = new ArrayList<>();
        List<Permission> rootNodes = getRootNode();
        for(Permission rootNode: rootNodes) {
            Permission menuNode = buildChildTree(rootNode);
            treeMenus.add(menuNode);
        }

        log.info("treeMenu: {}", treeMenus);
        return treeMenus;
    }

    @Override
    public List<Integer> getPermissionByRolesId(int roleId) {
        return permissionMapper.getRPermissionById(roleId);
    }

    @Override
    public void setPermissionByIds(Integer roleId, List<RPermission> rPermissions) {
        // insert 및 update ---> 마지막 단 아이디 집합
        List<Integer> ids = new ArrayList<>();
        // 마지막 단의 추가하기
             for(RPermission item: rPermissions) {
                 ids.add(item.getId());
                 permissionMapper.insertRPermission(item);
             }

        //얻은 마지막딘의 부모 id로 상위 단 추가

        // roleId 및 ids 에 의해 부합하지않으면 삭제 실행
        Map<String, Object> params = new HashMap<>();
             params.put("rId", roleId);
             params.put("pIds", ids);
        permissionMapper.deleteRPermission(params);

    }


    @Override
    public void addPermission(Permission permission) {
        LocalDateTime createTime = LocalDateTime.now();
        permission.setCreateTime(createTime);
        permissionMapper.insertPermission(permission);
    }

    @Override
    public void updatePermission(Permission permission) {
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.updatePermission(permission);
    }

    /**
     * permission status =0 으로 업데이트 (status 0: 삭제 1: 정상 2: 미정 기본 1
     * @param permission
     */
    @Override
    public void deletePermission(Permission permission) {
        // 권한 삭제 및 해당 자삭권한도 있는지 확인하고 삭제하기 --> status = 0
        // permission status =0 으로 업데이트 (status 0: 삭제 1: 정상 2: 미정 기본 1
        permission.setStatus(0);
        permissionMapper.savePermission(permission);

    }

    public List< Permission> getRootNode() {
        List< Permission> rootMenuList = new ArrayList< Permission>();
//
        for ( Permission menuNode : menuList) {

            if (menuNode.getPid() == 0) {
                rootMenuList.add(menuNode);
            }
        }

        log.info("rootMenuList: {}", rootMenuList);
        return rootMenuList;
    }
    public Permission buildChildTree(Permission pNode) {
        List< Permission> childMenus = new ArrayList< Permission>();
        for ( Permission menuNode : menuList) {
            if (menuNode.getPid().equals(pNode.getId())) {
                childMenus.add(buildChildTree(menuNode));
            }
        }
        pNode.setChildren(childMenus);
        return pNode;
    }
}
