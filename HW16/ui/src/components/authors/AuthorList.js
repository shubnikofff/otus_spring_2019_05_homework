// @flow
import React, { useState, useEffect } from 'react';
import { AuthorService } from 'services';

import {
	Box,
	LinearProgress,
	List,
} from '@material-ui/core';
import { default as AuthorListItem } from './AuthorListItem';

import type { Author } from 'types';

function AuthorList() {
	const [authors, setAuthors] = useState<Array<Author> | null>(null);

	useEffect(() => {
		AuthorService.fetchAllAuthors().then(setAuthors);
	}, []);

	if (authors === null) {
		return (<LinearProgress />);
	}

	return (
		<Box mt={4}>
			<List>
				{authors.map(author => (
					<AuthorListItem
						author={author}
						key={author.name}
					/>
				))}
			</List>
		</Box>
	);
}

export default AuthorList;
