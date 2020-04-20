// @flow
import RestClient from './RestClient';

import type { Author, AuthorFormValues } from 'types';

class AuthorService {

	static fetchAllAuthors(): Promise<Array<Author>> {
		return RestClient.get('/api/author')
			.then(response => response._embedded.authors);
	}

	static updateAuthor(url: string, values: AuthorFormValues): Promise<Author> {
		return RestClient.put(new URL(url).pathname, values);
	}
}

export default AuthorService;
