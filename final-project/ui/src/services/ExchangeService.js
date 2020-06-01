// @flow
import RestClient from './RestClient';

import type { RequestFormValues, ExchangeRequest } from 'types';

class ExchangeService {

	static createRequest(requestBody: RequestFormValues): Promise<string> {
		return RestClient.post('/exchange/request', requestBody);
	}

	static getIncomingRequests(): Promise<Array<ExchangeRequest>> {
		return RestClient.get('/cs/request/incoming');
	}

	static getOutgoingRequests(): Promise<Array<ExchangeRequest>> {
		return RestClient.get('/cs/request/outgoing');
	}

	static acceptRequest(id: string): Promise<void> {
		return RestClient.put(`/cs/request/${id}`);
	}

	static deleteRequest(id: string): Promise<void> {
		return RestClient.del(`/exchange/request/${id}`);
	}
}

export default ExchangeService;
