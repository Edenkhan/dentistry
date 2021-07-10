
package com.youruan.dentistry.core.user.mapper;

import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.query.RegisteredUserQuery;
import com.youruan.dentistry.core.user.vo.ExtendedRegisteredUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegisteredUserMapper {


    public RegisteredUser get(Long id);

    public int update(RegisteredUser registeredUser);

    public int add(RegisteredUser registeredUser);

    public int delete(Long id);

    public int count(RegisteredUserQuery qo);

    public List<ExtendedRegisteredUser> query(RegisteredUserQuery qo);

}
