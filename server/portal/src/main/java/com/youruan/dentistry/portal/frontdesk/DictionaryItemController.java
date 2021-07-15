package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryItemService;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionaryItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontdesk/dictionaryItem")
public class DictionaryItemController {

    private final DictionaryItemService dictionaryItemService;

    public DictionaryItemController(DictionaryItemService dictionaryItemService) {
        this.dictionaryItemService = dictionaryItemService;
    }

    @GetMapping("/getDoctor")
    public ResponseEntity<?> getDoctor() {
        DictionaryItemQuery qo = new DictionaryItemQuery();
        qo.setMark("DOCTOR");
        List<ExtendedDictionaryItem> voList = dictionaryItemService.listAll(qo);
        return ResponseEntity.ok(ImmutableMap.builder().put("data",voList).build());
    }
}
