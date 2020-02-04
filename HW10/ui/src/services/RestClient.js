// @flow
import axios from 'axios';

import type { AxiosInstance, AxiosResponse } from 'axios';

const BASE_URL = 'http://localhost:8080';

const axiosInstance: AxiosInstance = axios.create({
	baseURL: BASE_URL,
});

export function get<T>(url: string): Promise<T> {
	return axiosInstance.get(url).then((response: AxiosResponse<T>) => response.data)
}

export function post<T>(url: string, data?: any): Promise<T> {
	return axiosInstance.post(url, data).then((response: AxiosResponse<T>) => response.data)
}

export function put<T>(url: string, data?: any): Promise<T> {
	return axiosInstance.put(url, data).then((response: AxiosResponse<T>) => response.data)
}

export function del<T>(url: string): Promise<T> {
	return axiosInstance.delete(url).then((response: AxiosResponse<T>) => response.data)
}
