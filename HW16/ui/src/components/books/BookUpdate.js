// @flow
import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router';
import { BookService } from 'services';
import { Formik } from 'formik';
import {
	Box,
	Grid,
	LinearProgress,
	Snackbar,
	Typography,
} from '@material-ui/core';
import { Alert } from '@material-ui/lab';

import { default as BookForm } from './BookForm';

import type { AxiosError } from 'axios';
import type {
	Author,
	Book,
	BookFormValues,
	Genre,
} from 'types';

const MESSAGE_DELAY_TIME = 6000;

function BookUpdate() {
	const [book, setBook] = useState<Book | null>(null);
	const [genre, setGenre] = useState<Genre | null>(null);
	const [authors, setAuthors] = useState<Array<Author> | null>(null);
	const [operationError, setOperationError] = useState<?string>(null);
	const [successMsgOpen, setSuccessMsgOpen] = useState<boolean>(false);
	const { state: { href } } = useLocation();

	const handleSubmit = (values: BookFormValues) =>
		BookService.updateBook(href, values)
			.then(() => {
				setSuccessMsgOpen(true);
			})
			.catch((error: AxiosError) => {
				setOperationError(error.message);
			});

	useEffect(() => {
		BookService.fetchBook(href)
			.then(setBook)
			.catch((error: AxiosError) => {
				setOperationError(error.message);
			});
	}, [href]);

	useEffect(() => {
		if (book) {
			BookService.fetchGenre(book._links.genre.href)
				.then(setGenre)
				.catch((error: AxiosError) => {
					setOperationError(error.message);
				});
		}
	}, [book]);

	useEffect(() => {
		if (book) {
			BookService.fetchAuthors(book._links.authors.href)
				.then(setAuthors)
				.catch((error: AxiosError) => {
					setOperationError(error.message);
				});
		}
	}, [book]);

	if (!book || !genre || !authors) {
		return (<LinearProgress />);
	}

	return (
		<>
			<Box mt={4}>
				<Typography variant="h5">
					Update book
				</Typography>
				<Grid container>
					<Grid item xs={6}>
						<Formik
							component={BookForm}
							initialValues={{
								title: book.title,
								genre: genre._links.self.href,
								authors: authors.map(author => author._links.self.href),
							}}
							onSubmit={handleSubmit}
						/>
					</Grid>
				</Grid>
			</Box>
			<Snackbar open={successMsgOpen} autoHideDuration={MESSAGE_DELAY_TIME}>
				<Alert severity="success" onClose={() => setSuccessMsgOpen(false)}>
					Book saved successfully
				</Alert>
			</Snackbar>
			<Snackbar open={operationError} autoHideDuration={MESSAGE_DELAY_TIME}>
				<Alert severity="error" onClose={() => {
					setOperationError(null);
				}}>
					{operationError}
				</Alert>
			</Snackbar>
		</>
	);
}

export default BookUpdate;
