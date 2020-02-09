// @flow
import React from 'react';
import {
	Box,
	Button,
	IconButton,
	InputAdornment,
} from '@material-ui/core';
import { Remove as RemoveIcon } from '@material-ui/icons';
import {
	FieldArray,
	Form,
} from 'formik';
import { TextField } from 'formik-material-ui';

import type { FormikProps, ArrayHelpers } from 'formik';
import type { BookFormValues } from '../../types';

import {
	FIELD_AUTHORS,
	FIELD_GENRE,
	FIELD_TITLE,
} from '../../constants';

function BookForm({ values, isSubmitting }: FormikProps<BookFormValues>) {
	return (
		<Form>
			<Box mt={2}>
				<TextField
					fullWidth
					label="Title"
					name={FIELD_TITLE}
					required
					variant="outlined"
				/>
			</Box>
			<Box mt={2}>
				<TextField
					fullWidth
					label="Genre"
					name={FIELD_GENRE}
					required
					variant="outlined"
				/>
			</Box>
			<Box mt={2}>
				<FieldArray
					name="authors"
					render={(arrayHelpers: ArrayHelpers) => (
						<>
							{values.authors.map((author, index) => (
								<Box mt={2} key={index}>
									<TextField
										fullWidth
										label="Author"
										name={`${FIELD_AUTHORS}[${index}]`}
										required
										variant="outlined"
										InputProps={{
											endAdornment: values.authors.length > 1 ?
												<InputAdornment position="end">
													<IconButton
														edge="end"
														color="secondary"
														onClick={() => {
															arrayHelpers.remove(index);
														}}
													>
														<RemoveIcon />
													</IconButton>
												</InputAdornment> : null,
										}}
									/>
								</Box>
							))}
							<Button onClick={() => {
								arrayHelpers.push('');
							}}>
								Add author
							</Button>
						</>
					)}
				/>
			</Box>
			<Box mt={2}>
				<Button
					type="submit"
					disabled={isSubmitting}
					variant="contained"
					color="primary"
				>
					Apply
				</Button>
			</Box>
		</Form>
	);
}

export default BookForm;
