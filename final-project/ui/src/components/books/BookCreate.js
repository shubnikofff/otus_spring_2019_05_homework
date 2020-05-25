// @flow
import React, { useState } from 'react';
import { BookService } from 'services';
import { Formik } from 'formik';

import {
	Box,
	Grid,
	Snackbar,
	Typography,
} from '@material-ui/core';
import { Alert } from '@material-ui/lab';
import { default as BookForm } from './BookForm';

import type { BookFormValues } from 'types';
import type { AxiosError } from 'axios';
import { useHistory } from 'react-router';

const MESSAGE_DELAY_TIME = 6000;
const INITIAL_VALUES: BookFormValues = {
	title: '',
	genre: '',
	authors: [''],
};

type BookCreateProps = {|
	basePath: string
|}

function BookCreate({ basePath }: BookCreateProps) {
	const [operationError, setOperationError] = useState<?string>(null);
	const history = useHistory();

	const handleSubmit = (values: BookFormValues) =>
		BookService.createBook(values)
			.then((bookId: string) => {
				history.push(`${basePath}/${bookId}`)
			})
			.catch((error: AxiosError) => {
				setOperationError(error.message);
			});

	return (
		<>
			<Box mt={4}>
				<Typography variant="h5">
					New book
				</Typography>
				<Grid container>
					<Grid item xs={6}>
						<Formik
							component={BookForm}
							initialValues={INITIAL_VALUES}
							onSubmit={handleSubmit}
						/>
					</Grid>
				</Grid>
			</Box>
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

export default BookCreate;
