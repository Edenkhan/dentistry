
package com.youruan.dentistry.core.platform.mapper;

import java.util.List;
import com.youruan.dentistry.core.platform.domain.EmployeeRole;
import com.youruan.dentistry.core.platform.query.EmployeeRoleQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployeeRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmployeeRoleMapper {


    public EmployeeRole get(Long id);

    public int update(EmployeeRole employeeRole);

    public int add(EmployeeRole employeeRole);

    public int batchAdd(
        @Param("values")
        List<EmployeeRole> values);

    public int delete(Long id);

    public int count(EmployeeRoleQuery qo);

    public List<ExtendedEmployeeRole> query(EmployeeRoleQuery qo);

}
