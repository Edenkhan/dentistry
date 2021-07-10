package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictionaryItemAddForm {
    private String name;
    private Boolean enabled;
    private Long dictionaryId;
}
