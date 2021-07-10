export function maskIdCardNumber(s) {
  return s.substring(0, 6) + "********" + s.substring(14);
}

export function maskPhoneNumber(s) {
  return s.substring(0, 3) + "****" + s.substring(7);
}
