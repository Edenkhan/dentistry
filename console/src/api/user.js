import {get, post} from "./http";
import {stringify} from "../utils/qs";

export function listUsers(params) {
  return get('/user/list?' + stringify(params));
}

export function addUser(params) {
  return post('/user/add', stringify(params));
}

export function editUser(params) {
  return post('/user/edit', stringify(params));
}

export function getUser(id) {
  return post('/user/get', stringify({
    id
  }));
}

//用户已购买记录
export function listBought(params) {
  return get('/user/bought?' + stringify(params));
}

//查询用户 可生成报告的订单
export function listReportable(params) {
  return get('/user/reportable?' + stringify(params));
}
