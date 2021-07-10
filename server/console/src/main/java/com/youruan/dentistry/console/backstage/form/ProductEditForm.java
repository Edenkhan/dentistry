package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ProductEditForm {
    private Long id;
    private String name;
    private String intro;
    private Integer type;
    private Integer userType;
    private BigDecimal price;
    private Integer totalAppointNum;
    private Integer peopleNum;
    private String iconPath;
    private List<String> detailPaths;
    private String description;
    private Integer state;
}
