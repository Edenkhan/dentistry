import qs from "query-string";

export function stringify(o) {
  return qs.stringify(Object.keys(o).reduce((prev, key) => {
    const value = o[key];
    if (value != null && value !== '') {
      prev[key] = value;
    }
    return prev;
  }, {}));
}
