package com.youruan.dentistry.core.message.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.message.SmsVerificationProperties;
import org.apache.commons.lang3.time.DateUtils;

import java.io.StringWriter;
import java.util.Date;

public abstract class AbstractSmsVerificationType implements SmsVerificationType {

    SmsVerificationProperties smsVerificationProperties;

    public AbstractSmsVerificationType(SmsVerificationProperties smsVerificationProperties) {
        this.smsVerificationProperties = smsVerificationProperties;
    }

    @Override
    public String getContent(String code) {
        String templateId = getTemplateId();
        if (templateId != null) {
            try {
                return new ObjectMapper().writeValueAsString(ImmutableMap.of("code", code));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        StringWriter writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        String template = getTemplate();
        Mustache mustache = mf.compile(template);
        mustache.execute(writer, ImmutableMap.builder()
                .put("code", code)
                .put("validityInMinutes", getValidityInMinutes())
                .build());
        writer.flush();
        return writer.toString();
    }

    private Integer getValidityInMinutes() {
        return smsVerificationProperties.getDefaultValidityInMinutes();
    }

    @Override
    public Date getExpirationDate(Date now) {
        return DateUtils.addMinutes(now, getValidityInMinutes());
    }

    public String getTemplate() {
        return smsVerificationProperties.getDefaultTemplate();
    }
}
