
package com.youruan.dentistry.core.platform.mapper;

import java.util.List;
import com.youruan.dentistry.core.platform.domain.RolePermission;
import com.youruan.dentistry.core.platform.query.RolePermissionQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolePermissionMapper {


    public RolePermission get(Long id);

    public int update(RolePermission rolePermission);

    public int add(RolePermission rolePermission);

    public int batchAdd(
        @Param("values")
        List<RolePermission> values);

    public int delete(Long id);

    public int count(RolePermissionQuery qo);

    public List<ExtendedRolePermission> query(RolePermissionQuery qo);

}
