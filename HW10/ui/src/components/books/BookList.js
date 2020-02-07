// @flow
import React from 'react';
import { Link } from 'react-router-dom';
import {
	Box,
	Button,
	Card, CardActions,
	CardContent,
	Grid,
	Typography,
} from '@material-ui/core';
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
			<Box mt={4}>
				<Grid container spacing={4}>
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
									<Button color="primary" to={`/book/${book.id}`} component={Link} size="small">
										View
									</Button>
									<Button color="primary" to={`/book/${book.id}/edit`} component={Link} size="small">
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
