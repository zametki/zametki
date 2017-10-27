export function viewportWidth(): number {
    return window.innerWidth || document.documentElement.clientWidth || document.getElementsByTagName('body')[0].clientWidth
}

// noinspection JSUnusedGlobalSymbols
export function viewportHeight() {
    return window.innerHeight || document.documentElement.clientHeight || document.getElementsByTagName('body')[0].clientHeight
}

export function isDockedSidebarMode(): boolean {
    return viewportWidth() >= 700
}