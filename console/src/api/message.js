import {get} from "./http";
import {stringify} from "../utils/qs";

export function listSmsMessages(params) {
  return get('/message/sms/list?' + stringify(params));
}

export function listSmsVerifications(params) {
  return get('/message/smsVerification/list?' + stringify(params));
}
