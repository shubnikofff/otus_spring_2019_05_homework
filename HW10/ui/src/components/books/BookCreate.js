// @flow
import React from 'react';
import { RestClient } from '../../services';
import { Formik } from 'formik';
import {
	Grid,
	Box,
	Typography,
} from '@material-ui/core';
import { default as BookForm } from './BookForm';

import type { FormikBag } from 'formik';
import type { BookFormValues } from '../../types';

const INITIAL_VALUES: BookFormValues = {
	title: '',
	genre: '',
	authors: [''],
};

const handleSubmit = (values: BookFormValues, { resetForm }: FormikBag) =>
	RestClient.post('/books', values)
		.then(() => {
			resetForm();
		});

function BookCreate() {
	return (
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
	);
}

export default BookCreate;
