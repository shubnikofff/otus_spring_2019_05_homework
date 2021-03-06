// @flow
import React from 'react';
import { Link } from 'react-router-dom';
import { BookService } from 'services';
import {
	Box,
	Card,
	CardMedia,
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
	SwapHoriz as SwapIcon,
} from '@material-ui/icons';

import type { Book } from 'types';

type BookListProps = {|
	basePath: string,
|};

type BookListState = {|
	books: Array<Book> | null,
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
			.then((response: Array<Book>) => {
				this.setState({ books: response });
			});
	}

	render() {
		if (this.state.books === null) {
			return (<LinearProgress />);
		}

		const { basePath } = this.props;

		return (
			<Box mt={4}>
				<Grid container spacing={4}>
					{this.state.books.map((book: Book) => (
						<Grid item key={book.id} xs={12} sm={6} md={3}>
							<Card>
								<CardActionArea component={Link} to={`${basePath}/${book.id}`}>
									{
										Array.isArray(book.pictures)
											? <CardMedia
												component="img"
												alt="Cover"
												height="140"
												image={`/api/picture/${book.pictures[book.pictures.length - 1].id}`}
											/>
											: null
									}
									<CardContent>
										<Typography variant="h5" gutterBottom>
											{book.title}
										</Typography>
										<Typography variant="body2" color="textSecondary">
											{book.genre}
										</Typography>
										<Typography variant="body1">
											{book.authors.join(', ')}
										</Typography>
									</CardContent>
								</CardActionArea>
								<CardActions disableSpacing>
									<Grid container justify="flex-end" alignItems="center">
										{
											book.owned
												? <>
													<IconButton
														to={`${basePath}/${book.id}/update`}
														component={Link}
													>
														<EditIcon fontSize="small" />
													</IconButton>
													<IconButton
														to={`${basePath}/${book.id}/delete`}
														component={Link}
													>
														<DeleteIcon fontSize="small" />
													</IconButton>
												</>
												: <IconButton
													to={`${basePath}/${book.id}/exchange`}
													component={Link}
												>
													<SwapIcon />
												</IconButton>
										}
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
