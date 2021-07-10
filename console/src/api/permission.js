import {profile} from "./platform";

let permissionsCache = [];

export async function loadPermissions() {
  if (permissionsCache.length === 0) {
    const {permissions} = await profile();
    permissionsCache = permissions;
  }
  return permissionsCache;
}

export function storePermissions(permissions) {
  permissionsCache = permissions;
}

export function everyPermissions(names) {
  if (permissionsCache.indexOf('*') !== -1) {
    return true;
  }
  if (!Array.isArray(names)) {
    names = [names];
  }
  return names.every(name => permissionsCache.indexOf(name) !== -1);
}

export function somePermissions(names) {
  if (permissionsCache.indexOf('*') !== -1) {
    return true;
  }
  if (!Array.isArray(names)) {
    names = [names];
  }
  return names.some(name => permissionsCache.indexOf(name) !== -1);
}
