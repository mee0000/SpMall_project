package com.example.spmall;

import com.example.spmall.pojo.Result;
import com.example.spmall.adminService.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpMallApplicationTests {
    @Autowired
    private PermissionService permissionService;

    public Result getPermissionList() {
        System.out.print(permissionService.getPermissionList());
        return Result.success(permissionService.getPermissionList());
    }

}
