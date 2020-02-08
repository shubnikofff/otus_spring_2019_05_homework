// @flow
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router';
import { RestClient } from '../../services';

import {
	Box,
	LinearProgress,
	Link as MuiLink,
	List,
	ListItem,
	makeStyles,
	Typography,
} from '@material-ui/core';

import type { Book } from '../../types';

const useStyles = makeStyles(theme => ({
	authors: {
		width: '100%',
		maxWidth: 360,
		backgroundColor: theme.palette.background.paper,
	},
}));

function BookDetails() {
	const { id } = useParams();
	const [book, setBook] = useState<?Book>(null);
	const classes = useStyles();

	function fetchBook() {
		RestClient.get(`/books/${id}`).then(setBook);
	}

	useEffect(fetchBook, []);

	if (!book) {
		return (<LinearProgress variant="query" />);
	}

	return (
		<>
			<Box mt={4}>
				<Typography variant="h4">
					{book.title}
				</Typography>
				<MuiLink component={Link} to={`/genre/${book.genre.name}`} color="inherit">
					{book.genre.name}
				</MuiLink>
			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Authors
				</Typography>
				<List className={classes.authors}>
					{book.authors.map(author => (
						<ListItem component={Link} to={`/author/${author.name}`} button>
							{author.name}
						</ListItem>
					))}
				</List>
			</Box>
		</>
	);
}

export default BookDetails;
