// @flow
import RestClient from './RestClient';

import type { Genre, GenreFormValues } from 'types';

class GenreService {

	static fetchAllGenres(): Promise<Array<Genre>> {
		return RestClient.get('/genres');
	}

	static updateGenre(name: string, values: GenreFormValues) {
		return RestClient.put(`/genres/${name}`, values);
	}
}

export default GenreService;
