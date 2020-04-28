// @flow
import axios from 'axios';

import type { AxiosInstance, AxiosResponse } from 'axios';

const axiosInstance: AxiosInstance = axios.create({
	baseURL: `http://${process.env.REACT_APP_API_ADDRESS || 'localhost'}/api`,
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
