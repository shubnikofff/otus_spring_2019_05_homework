// @flow
import RestClient from './RestClient';

import type { Genre, GenreFormValues } from 'types';

class GenreService {

	static fetchAllGenres(): Promise<Array<Genre>> {
		return RestClient.get('/api/genre')
			.then(response => response._embedded.genres);
	}

	static updateGenre(url: string, values: GenreFormValues): Promise<Genre> {
		return RestClient.put(new URL(url).pathname, values);
	}
}

export default GenreService;
