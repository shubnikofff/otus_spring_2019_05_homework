// @flow
import RestClient from './RestClient';

import type { Genre, GenreFormValues } from 'types';

const BASE_PATH: string = '/book/genre';

class GenreService {

	static fetchAllGenres(): Promise<Array<Genre>> {
		return RestClient.get(BASE_PATH);
	}

	static updateGenre(name: string, values: GenreFormValues) {
		return RestClient.put(`${BASE_PATH}/${name}`, values);
	}
}

export default GenreService;
