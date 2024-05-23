export interface ResponseData {
    code: number,
    message: string
}

export interface User {
    id: string | number,
    userName: string,
    firstName: string,
    lastName: string,
    password: string,
    address: string,
    zipCode: string,
    phoneNumber: string,
    createDate: string
}

export interface UserResponseData extends ResponseData {
    data: User
}