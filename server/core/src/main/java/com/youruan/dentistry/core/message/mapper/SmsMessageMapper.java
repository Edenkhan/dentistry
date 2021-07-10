
package com.youruan.dentistry.core.message.mapper;

import java.util.List;
import com.youruan.dentistry.core.message.domain.SmsMessage;
import com.youruan.dentistry.core.message.query.SmsMessageQuery;
import com.youruan.dentistry.core.message.vo.ExtendedSmsMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SmsMessageMapper {


    public SmsMessage get(Long id);

    public int update(SmsMessage smsMessage);

    public int add(SmsMessage smsMessage);

    public int delete(Long id);

    public int count(SmsMessageQuery qo);

    public List<ExtendedSmsMessage> query(SmsMessageQuery qo);

}
