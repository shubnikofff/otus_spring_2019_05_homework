// @flow
import React from 'react';
import { Link } from 'react-router-dom';
import { BookService } from 'services';
import {
	Box,
	Card,
	CardActionArea,
	CardActions,
	CardContent,
	Grid,
	IconButton,
	LinearProgress,
	Typography,
} from '@material-ui/core';
import {
	Delete as DeleteIcon,
	Edit as EditIcon,
} from '@material-ui/icons';

import type { Book, Author, Linkable } from 'types';

type BookListProps = {||};

type BookListState = {|
	books: Array<Linkable<Book>> | null,
|};

class BookList extends React.PureComponent<BookListProps, BookListState> {
	constructor(props: BookListProps, context: any) {
		super(props, context);
		this.state = {
			books: null,
		};
	}

	componentDidMount() {
		BookService.fetchAllBooks()
			.then((response: Array<Linkable<Book>>) => {
				this.setState({ books: response });
			});
	}

	render() {
		if (this.state.books === null) {
			return (<LinearProgress />);
		}

		return (
			<Box mt={4}>
				<Grid container spacing={4}>
					{this.state.books.map((book: Linkable<Book>) => (
						<Grid item key={book.id} xs={12} sm={6} md={3}>
							<Card>
								<CardActionArea
									component={Link}
									to={{
										pathname: '/book/details',
										state: {
											href: book._links.self.href
										}
									}}
								>
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
								<CardActions disableSpacing>
									<Grid container justify="flex-end" alignItems="center">
										<IconButton
											component={Link}
											to={{
												pathname: '/book/update',
												state: {
													href: book._links.self.href
												}
											}}
										>
											<EditIcon fontSize="small" />
										</IconButton>
										<IconButton
											component={Link}
											to={{
												pathname: '/book/delete',
												state: {
													href: book._links.self.href
												}
											}}
										>
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
