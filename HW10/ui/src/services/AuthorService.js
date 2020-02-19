// @flow
import RestClient from './RestClient';

import type { Author, AuthorFormValues } from 'types';

class AuthorService {

	static fetchAllAuthors(): Promise<Array<Author>> {
		return RestClient.get('/authors');
	}

	static updateAuthor(name: string, values: AuthorFormValues): Promise<void> {
		return RestClient.put(`/authors/${name}`, values)
	}
}

export default AuthorService;
