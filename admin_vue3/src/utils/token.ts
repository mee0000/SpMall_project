const TokenKey = 'vue_admin_token'

export const GET_TOKEN = () => {
    return localStorage.getItem(TokenKey)
}

export const SET_TOKEN = (token: string) => {
    return localStorage.setItem(TokenKey, token)
}

export const REMOVE_TOKEN = () => {
    return localStorage.removeItem(TokenKey)
}