// @flow
import type { UserProfile } from 'types';

import { KEY_JWT_TOKEN, KEY_USER_PROFILE, KEY_TOKEN_TYPE } from 'constants/localStorage';

import RestClient from './RestClient';

type LoginResponse = {|
	access_token: string,
	token_type: string,
	profile: UserProfile,
|}

const CLIENT_ID: string = 'open-shelf';
const GRANT_TYPE: string = 'password';
const SECRET: string = 'secret';

class AuthService {

	static login(username: string, password: string): Promise<void> {
		return RestClient.post(`/auth/oauth/token`, null, {
			auth: {
				username: CLIENT_ID,
				password: SECRET,
			},
			params: {
				client_id: CLIENT_ID,
				grant_type: GRANT_TYPE,
				username,
				password,
			},
		}).then((response: LoginResponse) => {
			window.localStorage.setItem(KEY_JWT_TOKEN, response.access_token);
			window.localStorage.setItem(KEY_TOKEN_TYPE, response.token_type);
			window.localStorage.setItem(KEY_USER_PROFILE, JSON.stringify(response.profile));
		});
	}

	static logout(): void {
		window.localStorage.clear();
	}

	static isUserLoggedIn(): boolean {
		return !!window.localStorage.getItem(KEY_JWT_TOKEN)
	}

	static getJwtToken(): string {
		return window.localStorage.getItem(KEY_JWT_TOKEN);
	}

	static getUserProfile(): UserProfile {
		return JSON.parse(window.localStorage.getItem(KEY_USER_PROFILE));
	}

	static getTokenType(): string {
		return window.localStorage.getItem(KEY_TOKEN_TYPE);
	}
}

export default AuthService;
