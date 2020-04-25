// @flow
import React, { useState, useEffect } from 'react';

import { GenreService, AuthorService } from 'services';

import {
	Box,
	Button,
	FormControl,
	Grid,
	IconButton,
	InputLabel,
	MenuItem,
	Select,
} from '@material-ui/core';
import { Remove as RemoveIcon } from '@material-ui/icons';
import {
	Field,
	FieldArray,
	Form,
} from 'formik';
import { TextField } from 'formik-material-ui';

import type { FormikProps, ArrayHelpers } from 'formik';
import type {
	Author,
	BookFormValues,
	Genre,
} from 'types';

import {
	FIELD_AUTHORS,
	FIELD_GENRE,
	FIELD_TITLE,
} from 'constants/fields';


function renderSelectField({ field, options, label }) {
	return (
		<FormControl variant="outlined" fullWidth required>
			<InputLabel id="label">{label}</InputLabel>
			<Select
				labelId="label"
				label="Genre"
				{...field}
			>
				{options.map(options => (
					<MenuItem
						key={options._links.self.href}
						value={options._links.self.href}
					>
						{options.name}
					</MenuItem>
				))}
			</Select>
		</FormControl>
	);
}

type RemoveButtonProps = {|
	arrayHelpers: ArrayHelpers,
	authorsQuantity: number,
	index: number,
|}

function RemoveButton({ arrayHelpers, index, authorsQuantity }: RemoveButtonProps) {
	if (authorsQuantity < 2) {
		return null;
	}

	return (
		<IconButton
			color="secondary"
			onClick={() => {
				arrayHelpers.remove(index);
			}}
		>
			<RemoveIcon />
		</IconButton>
	);
}

function BookForm({ values, isSubmitting }: FormikProps<BookFormValues>) {
	const [genres, setGenres] = useState<Array<Genre>>([]);
	const [authors, setAuthors] = useState<Array<Author>>([]);

	useEffect(() => {
		GenreService.fetchAllGenres().then(setGenres);
	}, []);

	useEffect(() => {
		AuthorService.fetchAllAuthors().then(setAuthors);
	}, []);

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
				<Field
					component={renderSelectField}
					fullWidth
					label="Genre"
					name={FIELD_GENRE}
					options={genres}
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
								<Box mt={2}>
									<Grid key={index} container direction="row" wrap="nowrap">
										<Field
											fullWidth
											label="Author"
											name={`${FIELD_AUTHORS}[${index}]`}
											required
											variant="outlined"
											options={authors}
											component={renderSelectField}
										/>
										<RemoveButton
											arrayHelpers={arrayHelpers}
											authorsQuantity={values.authors.length}
											index={index}
										/>
									</Grid>
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
