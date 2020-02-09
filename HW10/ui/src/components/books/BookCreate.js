// @flow
import React, { useState } from 'react';
import { RestClient } from '../../services';
import { Formik } from 'formik';
import {
	Box,
	Grid,
	Snackbar,
	Typography,
} from '@material-ui/core';
import { Alert } from '@material-ui/lab';

import { default as BookForm } from './BookForm';

import type { FormikBag } from 'formik';
import type { BookFormValues } from '../../types';
import type { AxiosError } from 'axios';

const MESSAGE_DELAY_TIME = 6000;
const INITIAL_VALUES: BookFormValues = {
	title: '',
	genre: '',
	authors: [''],
};

function BookCreate() {
	const [successMsgOpen, setSuccessMsgOpen] = useState<boolean>(false);
	const [operationError, setOperationError] = useState<?string>(null);

	const handleSubmit = (values: BookFormValues, { resetForm }: FormikBag) =>
		RestClient.post('/books', values)
			.then(() => {
				resetForm();
				setSuccessMsgOpen(true);
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

export default BookCreate;
