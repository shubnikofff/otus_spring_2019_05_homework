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
import type { Book, BookFormValues, Linkable } from 'types';

const MESSAGE_DELAY_TIME = 6000;

function BookUpdate() {
	const [initialValues, setInitialValues] = useState<?BookFormValues>(null);
	const [operationError, setOperationError] = useState<?string>(null);
	const [successMsgOpen, setSuccessMsgOpen] = useState<boolean>(false);
	const { state: { href } } = useLocation();

	const handleSubmit = (values: BookFormValues) =>
		BookService.updateBook(href, values)
			.then(() => {
				setInitialValues(values);
				setSuccessMsgOpen(true);
			})
			.catch((error: AxiosError) => {
				setOperationError(error.message);
			});

	useEffect(() => {
		BookService.fetchBook(href)
			.then((book: Linkable<Book>) => {
				setInitialValues({
					title: book.title,
					genre: book.genre.name,
					authors: book.authors.map(author => author.name),
				});
			})
			.catch((error: AxiosError) => {
				setOperationError(error.message);
			});
	}, [href]);

	const Form = (
		<Grid container>
			<Grid item xs={6}>
				<Formik
					component={BookForm}
					initialValues={initialValues}
					onSubmit={handleSubmit}
				/>
			</Grid>
		</Grid>
	);

	return (
		<>
			<Box mt={4}>
				<Typography variant="h5">
					Update book
				</Typography>
				{initialValues ? Form : <LinearProgress />}
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
