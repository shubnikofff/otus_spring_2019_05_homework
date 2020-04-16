// @flow
import RestClient from './RestClient';

import type { Genre, GenreFormValues, Linkable } from 'types';

class GenreService {

	static fetchAllGenres(): Promise<Array<Linkable<Genre>>> {
		return RestClient.get('/api/genre')
			.then(response => response._embedded.genres);
	}

	static updateGenre(url: string, values: GenreFormValues) {
		return RestClient.put(new URL(url).pathname, values);
	}
}

export default GenreService;
