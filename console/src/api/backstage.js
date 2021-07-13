//字典
import {get, post} from "./http";
import {stringify} from "../utils/qs";

export function listDictionarys(params) {
  return get('/backstage/dictionary/list?' + stringify(params));
}

export function addDictionary(params) {
  return post('/backstage/dictionary/add', stringify(params));
}

export function editDictionary(params) {
  return post('/backstage/dictionary/edit', stringify(params));
}

export function getDictionary(params) {
  return get('/backstage/dictionary/get?' + stringify(params));
}

export function listAllDictionarys() {
  return get('/backstage/dictionary/listAll');
}

//字典详情
export function listDictionaryItems(params) {
  return get('/backstage/dictionaryItem/list?' + stringify(params));
}

export function addDictionaryItem(params) {
  return post('/backstage/dictionaryItem/add', stringify(params));
}

export function editDictionaryItem(params) {
  return post('/backstage/dictionaryItem/edit', stringify(params));
}

export function getDictionaryItem(params) {
  return get('/backstage/dictionaryItem/get?' + stringify(params));
}

//产品
export function listProducts(params) {
  return get('/backstage/product/list?' + stringify(params));
}

export function addProduct(params) {
  return post('/backstage/product/add', stringify(params));
}

export function editProduct(params) {
  return post('/backstage/product/edit', stringify(params));
}

export function getProduct(params) {
  return get('/backstage/product/get?' + stringify(params));
}

export function uploadDetails(params) {
  return post('/backstage/product/uploadDetail', params);
}

//门店
export function listShops(params) {
  return get('/backstage/shop/list?' + stringify(params));
}

export function addShop(params) {
  return post('/backstage/shop/add', stringify(params));
}

export function editShop(params) {
  return post('/backstage/shop/edit', stringify(params));
}

export function getShop(params) {
  return get('/backstage/shop/get?' + stringify(params));
}

export function addValid(params) {
  return post('/backstage/shop/valid', stringify(params));
}

// 预约管理
export function listAppointManages(params) {
  return get('/backstage/appointManage/list?' + stringify(params));
}

export function addAppointManage(params) {
  return post('/backstage/appointManage/add', stringify(params));
}

export function editAppointManage(params) {
  return post('/backstage/appointManage/edit', stringify(params));
}

export function getAppointManage(params) {
  return get('/backstage/appointManage/get?' + stringify(params));
}

export function getOneByShopId(params) {
  return get('/backstage/appointManage/getOneByShopId?' + stringify(params));
}

export function editAllTopLimit(params) {
  return post('/backstage/appointManage/editAllTopLimit', stringify(params));
}

export function checkDataSource(params) {
  return post('/backstage/appointManage/checkDataSource' , stringify(params));
}

// 订单记录
export function listOrderRecord(params) {
  return get('/backstage/orderRecord/list?' + stringify(params));
}

// 预约记录
export function listAppointRecord(params) {
  return get('/backstage/appointRecord/list?' + stringify(params));
}

export function appointComplete(params) {
  return post('/backstage/appointRecord/appointComplete?' + stringify(params));
}

// 报告列表
export function listReport(params) {
  return get('/backstage/report/list?' + stringify(params));
}

// 同步报告
export function editReport(params) {
  return post('/backstage/report/edit?' + stringify(params));
}

// 查询报告对应的预约信息
export function getAppoint(params) {
  return get('/backstage/report/getAppoint?' + stringify(params));
}

// 添加报告
export function addReport(params) {
  return post('/backstage/report/add?' + stringify(params));
}

// 重新上传
export function resetReport(params) {
  return post('/backstage/report/reset?' + stringify(params));
}

