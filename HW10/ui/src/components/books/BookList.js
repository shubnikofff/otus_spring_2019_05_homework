// @flow
import React from 'react';
import { Link } from 'react-router-dom';
import {
	Button,
	IconButton,
	Box,
	Card, CardActions,
	CardContent,
	Grid,
	Typography,
} from '@material-ui/core';
import {
	CreateRounded,
} from '@material-ui/icons';
import { RestClient } from '../../services';

import type { Book, Author } from '../../types';

type BookListProps = {||};

type BookListState = {|
	books: Array<Book>
|};

class BookList extends React.PureComponent<BookListProps, BookListState> {
	constructor(props: BookListProps, context: any) {
		super(props, context);
		this.state = {
			books: [],
		};
	}

	componentDidMount() {
		RestClient.get('/books').then((response: Array<Book>) => {
			this.setState({ books: response });
		});
	}

	render() {
		return (
			<Box mt={2}>
				<Grid
					container
					direction="row"
					justify="flex-end"
					alignItems="center"
				>
					<IconButton>
						<CreateRounded />
					</IconButton>
				</Grid>
				<Grid container spacing={6}>
					{this.state.books.map((book: Book) => (
						<Grid item key={book.id} xs={12} sm={6} md={3}>
							<Card>
								<CardContent>
									<Typography variant="h5" gutterBottom>
										{book.title}
									</Typography>
									<Typography variant="body2" color="textSecondary">
										{book.genre.name}
									</Typography>
									<Typography variant="body1">
										{book.authors.map((author: Author) => author.name).join(', ')}
									</Typography>
								</CardContent>
								<CardActions>
									<Button color="primary" to={`/books/${book.id}`} component={Link}>
										View
									</Button>
									<Button color="primary" to={`/books/${book.id}/edit`} component={Link}>
										Edit
									</Button>
								</CardActions>
							</Card>
						</Grid>
					))}
				</Grid>
			</Box>
		);
	}
}

export default BookList;
