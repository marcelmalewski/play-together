export interface LoginFormValues {
  loginOrEmail: string;
  password: string;
}

export interface LoginBody {
  username: string;
  password: string;
}

export interface RegisterFormValues {
  login: string;
  password: string;
  email: string;
  birthDate: Date;
  playingTimeStart: Date;
  playingTimeEnd: Date;
}

export interface RegisterBody {}
