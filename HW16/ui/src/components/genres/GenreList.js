// @flow
import React, { useState, useEffect } from 'react';
import { GenreService } from 'services';

import {
	Box,
	LinearProgress,
	List,
} from '@material-ui/core';
import { default as GenreListItem } from './GenreListItem';

import type { Genre, Linkable } from 'types';

function GenreList() {
	const [genres, setGenres] = useState<Array<Linkable<Genre>> | null>(null);

	useEffect(() => {
		GenreService.fetchAllGenres().then(setGenres);
	}, []);

	if (genres === null) {
		return (<LinearProgress />);
	}

	return (
		<Box mt={4}>
			<List>
				{genres.map(genre => (
					<GenreListItem
						genre={genre}
						key={genre.name}
					/>
				))}
			</List>
		</Box>
	);
}

export default GenreList;
