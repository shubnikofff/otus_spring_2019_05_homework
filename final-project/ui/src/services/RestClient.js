// @flow
import axios from 'axios';

import { AuthService } from 'services';

import type {
	AxiosError,
	AxiosInstance,
	AxiosRequestConfig,
	AxiosResponse,
} from 'axios';

const axiosInstance: AxiosInstance = axios.create({
	baseURL: 'http://localhost:3000/api',
});

const UNAUTHORIZED_STATUS_CODE: number = 401;

axiosInstance.interceptors.request.use((config: AxiosRequestConfig) => {
	if(AuthService.getJwtToken()) {
		config.headers.Authorization = `${AuthService.getTokenType()} ${AuthService.getJwtToken()}`;
	}

	return config;
}, error => Promise.reject(error));

axiosInstance.interceptors.response.use((response: AxiosResponse) => {
	return response.data;
}, (error: AxiosError) => {
	if (error.response.status === UNAUTHORIZED_STATUS_CODE) {
		window.location = '/login';
	}
});

class RestClient {
	static get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
		return axiosInstance.get(url, config);
	}

	static post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
		return axiosInstance.post(url, data, config);
	}

	static put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
		return axiosInstance.put(url, data, config);
	}

	static del<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
		return axiosInstance.delete(url, config);
	}
}

export default RestClient;
