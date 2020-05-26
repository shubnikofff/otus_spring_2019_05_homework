// @flow
import RestClient from './RestClient';

import type { RequestFormValues } from 'types';

const BASE_PATH = '/exchange';

class ExchangeService {

	static createRequest(requestBody: RequestFormValues): Promise<string> {
		return RestClient.post(`${BASE_PATH}/request`, requestBody);
	}

}

export default ExchangeService;
