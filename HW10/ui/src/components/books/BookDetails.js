// @flow
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router';
import { RestClient } from 'services';

import {
	Box,
	Button,
	Grid,
	LinearProgress,
	Link as MuiLink,
	List,
	ListItem,
	makeStyles,
	Typography,
} from '@material-ui/core';
import { CommentList } from 'components';

import type { Book } from 'types';

const useStyles = makeStyles(theme => ({
	authors: {
		backgroundColor: theme.palette.background.paper,
		maxWidth: 360,
		width: '100%',
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
				<Grid container justify="space-between">
					<div>
						<Typography variant="h4">
							{book.title}
						</Typography>
						<MuiLink component={Link} to={`/genre/${book.genre.name}`} color="inherit">
							{book.genre.name}
						</MuiLink>
					</div>
					<div>
						<Button
							color="primary"
							component={Link}
							size="large"
							to={`/book/${book.id}/edit`}
							variant="outlined"
						>
							Update
						</Button>
						<Button
							color="secondary"
							component={Link}
							size="large"
							to={`/book/${book.id}/delete`}
						>
							Delete
						</Button>
					</div>
				</Grid>

			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Authors
				</Typography>
				<List className={classes.authors}>
					{book.authors.map(author => (
						<ListItem
							button
							component={Link}
							key={author.name}
							to={`/author/${author.name}`}
						>
							{author.name}
						</ListItem>
					))}
				</List>
			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Comments
				</Typography>
				<CommentList bookId={id} />
			</Box>
		</>
	);
}

export default BookDetails;
