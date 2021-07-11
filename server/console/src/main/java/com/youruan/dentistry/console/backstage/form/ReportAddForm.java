package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportAddForm {
    private Long userId;
    private Long appointId;
    private List<String> pathList;
}
