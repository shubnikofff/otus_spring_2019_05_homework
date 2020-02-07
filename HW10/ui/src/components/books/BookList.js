// @flow
import React from 'react';
import { Link } from 'react-router-dom';
import {
	Box,
	IconButton,
	Card,
	CardActions,
	CardActionArea,
	CardContent,
	Grid,
	Typography,
} from '@material-ui/core';
import {
	Delete as DeleteIcon,
	Edit as EditIcon,
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
			<Box mt={4}>
				<Grid container spacing={4}>
					{this.state.books.map((book: Book) => (
						<Grid item key={book.id} xs={12} sm={6} md={3}>
							<Card>
								<CardActionArea component={Link} to={`/book/${book.id}`}>
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
								</CardActionArea>
								<CardActions>
									<Grid container justify="flex-end" alignItems="center">
										<IconButton to={`/book/${book.id}/edit`} component={Link}>
											<EditIcon fontSize="small" />
										</IconButton>
										<IconButton to={`/book/${book.id}/delete`} component={Link}>
											<DeleteIcon fontSize="small" />
										</IconButton>
									</Grid>
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
