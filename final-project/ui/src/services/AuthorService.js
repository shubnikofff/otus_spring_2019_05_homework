// @flow
import RestClient from './RestClient';

import type { Author, AuthorFormValues } from 'types';

const BASE_PATH: string = '/book/author';

class AuthorService {

	static fetchAllAuthors(): Promise<Array<Author>> {
		return RestClient.get(BASE_PATH);
	}

	static updateAuthor(name: string, values: AuthorFormValues): Promise<void> {
		return RestClient.put(`${BASE_PATH}/${name}`, values)
	}
}

export default AuthorService;
