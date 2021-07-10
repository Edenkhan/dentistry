package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.backstage.form.DictionaryAddForm;
import com.youruan.dentistry.console.backstage.form.DictionaryEditForm;
import com.youruan.dentistry.console.backstage.form.DictionaryListForm;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.backstage.domain.Dictionary;
import com.youruan.dentistry.core.backstage.query.DictionaryQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryService;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理
 */
@RestController
@RequestMapping("/backstage/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/listAll")
    @RequiresPermission(value = "backstage.dictionary.listAll", description = "字典-全部")
    public ResponseEntity<?> listAll() {
        List<ExtendedDictionary> extendedDictionaryList = dictionaryService.listAll();
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(extendedDictionaryList,
                        "id", "name"))
                .build());
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.dictionary.list", description = "字典-列表")
    public ResponseEntity<?> list(DictionaryListForm form) {
        DictionaryQuery qo = form.buildQuery();
        Pagination<ExtendedDictionary> pagination = dictionaryService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "lastModifiedDate", "name", "mark"))
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "backstage.dictionary.get", description = "字典-获取")
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        Dictionary dictionary = dictionaryService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(dictionary, "id", "name", "mark"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "backstage.dictionary.add", description = "字典-添加")
    public ResponseEntity<?> add(DictionaryAddForm form) {
        Dictionary dictionary = dictionaryService.create(
                form.getName(),
                form.getMark());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", dictionary.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "backstage.dictionary.edit", description = "字典-修改")
    public ResponseEntity<?> edit(DictionaryEditForm form) {
        Dictionary dictionary = dictionaryService.get(form.getId());
        dictionaryService.update(
                dictionary,
                form.getName(),
                form.getMark());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", dictionary.getId())
                .build());
    }

}
