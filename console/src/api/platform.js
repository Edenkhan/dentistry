import {get, post} from "./http";
import {stringify} from "../utils/qs";

export function login(username, password) {
  return post('/platform/employee/login', stringify({
    username,
    password
  }));
}

export function logout() {
  return post('/platform/employee/logout');
}

export function profile() {
  return get('/platform/employee/profile');
}

export function listEmployees(params) {
  return get('/platform/employee/list?' + stringify(params));
}

export function addEmployee(params) {
  return post('/platform/employee/add', stringify(params));
}

export function editEmployee(params) {
  return post('/platform/employee/edit', stringify(params));
}

export function changeStatus(params) {
  return post('/platform/employee/changeStatus', stringify(params));
}

export function changePassword(params) {
  return post('/platform/employee/changePassword', stringify(params));
}

export function resetPassword(params) {
  return post('/platform/employee/resetPassword', stringify(params));
}

export function getEmployee(params) {
  return get('/platform/employee/get?' + stringify(params));
}

export function checkLogin() {
  return get('/platform/employee/checkLogin');
}

export function listPermissions(params) {
  return get('/platform/permission/list?' + stringify(params));
}

export function addPermission(params) {
  return post('/platform/permission/add', stringify(params));
}

export function editPermission(params) {
  return post('/platform/permission/edit', stringify(params));
}

export function getPermission(params) {
  return get('/platform/permission/get?' + stringify(params));
}

export function listRoles(params) {
  return get('/platform/role/list?' + stringify(params));
}

export function addRole(params) {
  return post('/platform/role/add', stringify(params));
}

export function editRole(params) {
  return post('/platform/role/edit', stringify(params));
}

export function getRole(params) {
  return get('/platform/role/get?' + stringify(params));
}

export function getAssignPermissionsOptions(params) {
  return get('/platform/role/assignPermissionsOptions?' + stringify(params));
}

export function assignPermissions(params) {
  return post('/platform/role/assignPermissions', stringify(params));
}

export function getAssignRolesOptions(params) {
  return get('/platform/employee/assignRolesOptions?' + stringify(params));
}

export function assignRoles(params) {
  return post('/platform/employee/assignRoles', stringify(params));
}
