// @flow
import RestClient from './RestClient';

import type { Author, AuthorFormValues, Linkable } from 'types';

class AuthorService {

	static fetchAllAuthors(): Promise<Array<Linkable<Author>>> {
		return RestClient.get('/api/author')
			.then(response => response._embedded.authors);
	}

	static updateAuthor(url: string, values: AuthorFormValues): Promise<Linkable<Author>> {
		return RestClient.put(new URL(url).pathname, values);
	}
}

export default AuthorService;
