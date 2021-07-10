
package com.youruan.dentistry.core.platform.mapper;

import java.util.List;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.query.RoleQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {


    public Role get(Long id);

    public int update(Role role);

    public int add(Role role);

    public int delete(Long id);

    public int count(RoleQuery qo);

    public List<ExtendedRole> query(RoleQuery qo);

}
