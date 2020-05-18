// @flow
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router';
import { BookService } from 'services';

import {
	Box,
	Button,
	Grid,
	LinearProgress,
	List,
	ListItem,
	ListItemText,
	Typography,
} from '@material-ui/core';
import { CommentList, PictureList } from 'components';

import type { BookCompleteData } from 'types';

function BookDetails() {
	const { id } = useParams();
	const [book, setBook] = useState<BookCompleteData | null>(null);

	useEffect(() => {
		BookService.fetchBookCompleteData(id).then(setBook);
	}, [id]);

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
				<Typography variant="body1" color="textPrimary">
					{book.genre}
				</Typography>
			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Authors
				</Typography>
				<List>
					{book.authors.map(author => (
						<ListItem key={author}>
							<ListItemText
								primary={author}
								color="textPrimary"
							/>
						</ListItem>
					))}
				</List>
			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Pictures
				</Typography>
				{book ? <PictureList bookId={id} items={book.pictures} /> : <LinearProgress />}
			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Comments
				</Typography>
				{book ? <CommentList bookId={id} items={book.comments} /> : <LinearProgress />}
			</Box>
		</>
	);
}

export default BookDetails;
