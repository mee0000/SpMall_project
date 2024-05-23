package com.example.spmall.mapper;

import com.example.spmall.pojo.Permission;
import com.example.spmall.pojo.RPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * permissionMapper
 *
 * @author toy aa@nfda
 * @version 2024/03/04 2:58 PM
 * @since JDK11
 **/
@Mapper
public interface PermissionMapper {
    /**
     * 모든 permission 리스트
     * @return
     */
    List<Permission> getPermissionList ();

    /**
     * 선택된 permission
     * @param rId
     * @return
     */
    @Select("select p_id from r_permission where r_id = #{rId} and type = 2")
    List<Integer> getRPermissionById(Integer rId);

    /**
     * 새로운 permission 추
     * @param permission
     */
    void insertPermission(Permission permission);

    /**
     * permission 업데이사
     * @param permission
     */
    void updatePermission(Permission permission);

    /**
     * 삭제  ---> status를 0으로
     * @param
     */
    @Update("update permission set status = #{status} where id = #{id}")
    void savePermission(Permission permission);

    List<Permission> getPermissionListByIds(List<Integer> ids);

    void insertRPermission(RPermission rPermission);

    /**
     * roleId 및 pid에 의해 rpermission 삭제하기
     * @param params
     */
    void deleteRPermission(Map<String, Object> params);
}
