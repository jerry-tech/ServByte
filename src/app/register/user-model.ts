export class UserModel {
    constructor(
        public accountType: string,
        public emailAddress: string,
        public name: string,
        public password: string,
        public phoneNumber: String
    ) {

    }
}