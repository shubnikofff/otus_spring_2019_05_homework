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
}

export default ExchangeService;
