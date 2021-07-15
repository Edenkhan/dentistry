
package com.youruan.dentistry.core.backstage.vo;

import com.youruan.dentistry.core.backstage.domain.Dictionary;
import com.youruan.dentistry.core.backstage.domain.DictionaryItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtendedDictionaryItem
    extends DictionaryItem
{

    private final static long serialVersionUID = 1L;
    /**
     * 字典
     */
    private Dictionary dictionary;

}
