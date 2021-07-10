package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DictionaryItemEditForm {
    private Long id;
    private String name;
    private Boolean enabled;
    private Long dictionaryId;
}
