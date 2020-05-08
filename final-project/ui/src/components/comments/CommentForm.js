// @flow
import React from 'react';
import {
	Box,
	Button,
} from '@material-ui/core';
import { Form } from 'formik';
import { TextField } from 'formik-material-ui';

import type { FormikProps } from 'formik';
import type { CommentFormValues } from 'types';

import {
	FIELD_TEXT,
	FIELD_USER,
} from 'constants/fields';

function CommentForm({ isSubmitting }: FormikProps<CommentFormValues>) {
	return (
		<Form>
			<Box mt={2}>
				<TextField
					fullWidth
					label="Name"
					name={FIELD_USER}
					required
					variant="outlined"
				/>
			</Box>
			<Box mt={2}>
				<TextField
					fullWidth
					label="Comment"
					name={FIELD_TEXT}
					required
					variant="outlined"
				/>
			</Box>
			<Box mt={2}>
				<Button
					type="submit"
					disabled={isSubmitting}
					variant="contained"
					color="primary"
				>
					Publish
				</Button>
			</Box>
		</Form>
	);
}

export default CommentForm;
