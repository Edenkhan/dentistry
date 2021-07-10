
package com.youruan.dentistry.core.platform.mapper;

import java.util.List;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.query.EmployeeQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmployeeMapper {


    public Employee get(Long id);

    public int update(Employee employee);

    public int add(Employee employee);

    public int delete(Long id);

    public int count(EmployeeQuery qo);

    public List<ExtendedEmployee> query(EmployeeQuery qo);

}
