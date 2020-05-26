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
	Typography,
} from '@material-ui/core';
import { CommentList, PictureList } from 'components';

import type { BookCompleteData } from 'types';

type BookDetailsProps = {|
	basePath: string
|}

function BookDetails({ basePath }: BookDetailsProps) {
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
						{
							book.owned
								? <>
									<Button
										color="primary"
										component={Link}
										size="large"
										to={`${basePath}/${book.id}/update`}
										variant="outlined"
									>
										Update
									</Button>
									<Button
										color="secondary"
										component={Link}
										size="large"
										to={`${basePath}/${book.id}/delete`}
									>
										Delete
									</Button>
								</>
								: <Button
									color="primary"
									component={Link}
									size="large"
									to={`${basePath}/${book.id}/exchange`}
									variant="contained"
								>
									Offer exchange
								</Button>
						}
					</div>
				</Grid>
				<Typography variant="body1" color="textPrimary">
					{book.genre}
				</Typography>
			</Box>
			<Box mt={4}>
				<Typography variant="h6" gutterBottom>
					Authors
				</Typography>
				<Typography variant="body1">
					{book.authors.join(', ')}
				</Typography>
			</Box>
			<Box mt={4}>
				<Typography variant="h6">
					Pictures
				</Typography>
				{book ? <PictureList bookId={id} items={book.pictures} /> : <LinearProgress />}
			</Box>
			<Box mt={4}>
				<Typography variant="h6" gutterBottom>
					Owner
				</Typography>
				<Typography variant="body1" gutterBottom>
					{`${book.owner.firstName} ${book.owner.lastName}`}
				</Typography>
				<Typography variant="body2" >
					{book.owner.email}
				</Typography>
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
