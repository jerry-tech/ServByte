export interface IRest {
    data: IRest;
    restId: number,
    city: string,
    logo: string,
    account: {
        accountId: number,
        accountType: string,
        emailAdress: string,
        name: string,
        phoneNumber: string
    }
}
