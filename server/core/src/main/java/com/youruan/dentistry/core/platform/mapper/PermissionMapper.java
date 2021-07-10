
package com.youruan.dentistry.core.platform.mapper;

import java.util.List;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.query.PermissionQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionMapper {


    public Permission get(Long id);

    public int update(Permission permission);

    public int add(Permission permission);

    public int delete(Long id);

    public int count(PermissionQuery qo);

    public List<ExtendedPermission> query(PermissionQuery qo);

}
