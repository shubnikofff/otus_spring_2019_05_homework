// @flow
import axios from 'axios';

import type { AxiosInstance, AxiosResponse } from 'axios';

const API_URL = 'http://localhost:3000';

const axiosInstance: AxiosInstance = axios.create({
	baseURL: API_URL,
});

class RestClient {
	static get<T>(url: string): Promise<T> {
		return axiosInstance.get(url).then((response: AxiosResponse<T>) => response.data);
	}

	static post<T>(url: string, data?: any): Promise<T> {
		return axiosInstance.post(url, data).then((response: AxiosResponse<T>) => response.data);
	}

	static put<T>(url: string, data?: any): Promise<T> {
		return axiosInstance.put(url, data).then((response: AxiosResponse<T>) => response.data);
	}

	static del<T>(url: string): Promise<T> {
		return axiosInstance.delete(url).then((response: AxiosResponse<T>) => response.data);
	}
}

export default RestClient;
