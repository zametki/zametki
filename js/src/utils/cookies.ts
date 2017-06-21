function set(key: string, value: string) {
    document.cookie = key + '=' + value + ';expires=Fri, 31 Dec 9999 23:59:59 GMT';
}

function get(key: string): string {
    const keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return keyValue ? keyValue[2] : "{}";
}

export default {
    set: set,
    get: get
}