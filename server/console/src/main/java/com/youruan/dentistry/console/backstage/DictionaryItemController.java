package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.DictionaryItemAddForm;
import com.youruan.dentistry.console.backstage.form.DictionaryItemEditForm;
import com.youruan.dentistry.console.backstage.form.DictionaryItemListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.domain.DictionaryItem;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryItemService;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionaryItem;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 字典详情管理
 */
@RestController
@RequestMapping("/backstage/dictionaryItem")
public class DictionaryItemController {

    private final DictionaryItemService dictionaryItemService;
    public DictionaryItemController(DictionaryItemService dictionaryItemService) {
        this.dictionaryItemService = dictionaryItemService;
    }



    @GetMapping("/list")
    @RequiresPermission(value = "backstage.dictionaryItem.list", description = "字典详情-列表")
    public ResponseEntity<?> list(DictionaryItemListForm form) {
        DictionaryItemQuery qo = form.buildQuery();
        Pagination<ExtendedDictionaryItem> pagination = dictionaryItemService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", pagination.getData())
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "backstage.dictionaryItem.get", description = "字典详情-获取")
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        DictionaryItem dictionaryItem = dictionaryItemService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(dictionaryItem, "id", "name", "enabled", "dictionaryId"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "backstage.dictionaryItem.add", description = "字典详情-添加")
    public ResponseEntity<?> add(DictionaryItemAddForm form) {
        DictionaryItem dictionaryItem = dictionaryItemService.create(
                form.getName(),
                form.getEnabled(),
                form.getDictionaryId());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", dictionaryItem.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "backstage.dictionaryItem.edit", description = "字典详情-修改")
    public ResponseEntity<?> edit(DictionaryItemEditForm form) {
        DictionaryItem dictionaryItem = dictionaryItemService.get(form.getId());
        dictionaryItemService.update(
                dictionaryItem,
                form.getName(),
                form.getEnabled(),
                form.getDictionaryId());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", dictionaryItem.getId())
                .build());
    }


    @PostMapping("/changeStatus")
    @RequiresPermission(value = "backstage.dictionaryItem.changeStatus", description = "字典详情-修改状态")
    public ResponseEntity<?> changeStatus(DictionaryItemEditForm form) {
        DictionaryItem dictionaryItem = dictionaryItemService.get(form.getId());
        dictionaryItemService.changeEnabled(dictionaryItem);
        return ResponseEntity.ok().build();
    }
}
