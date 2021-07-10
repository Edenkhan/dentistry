
package com.youruan.dentistry.core.message.mapper;

import java.util.List;
import com.youruan.dentistry.core.message.domain.SmsVerification;
import com.youruan.dentistry.core.message.query.SmsVerificationQuery;
import com.youruan.dentistry.core.message.vo.ExtendedSmsVerification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SmsVerificationMapper {


    public SmsVerification get(Long id);

    public int update(SmsVerification smsVerification);

    public int add(SmsVerification smsVerification);

    public int delete(Long id);

    public int count(SmsVerificationQuery qo);

    public List<ExtendedSmsVerification> query(SmsVerificationQuery qo);

}
