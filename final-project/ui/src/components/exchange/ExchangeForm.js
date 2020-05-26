// @flow
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { BookService } from 'services';
import { useFormik } from 'formik';

import {
	Box,
	Button,
	Checkbox,
	FormControl,
	FormControlLabel,
	FormGroup,
	FormLabel,
	TextField,
	LinearProgress,
	Typography,
	FormHelperText,
} from '@material-ui/core';

import type { Book } from 'types';

import {
	FIELD_ADDITIONAL_INFO,
	FIELD_OFFERED_BOOK_IDS,
	FIELD_REQUESTED_BOOK_ID,
} from 'constants/fields';

type ExchangeFormProps = {|
	basePath: string
|}

function ExchangeForm({ basePath }: ExchangeFormProps) {
	const { id: bookId } = useParams();
	const formik = useFormik({
		initialValues: {
			[FIELD_REQUESTED_BOOK_ID]: bookId,
			[FIELD_OFFERED_BOOK_IDS]: [],
			[FIELD_ADDITIONAL_INFO]: '',
		},
		onSubmit: values => {
			console.log(values);
		},
		validate: (values) => {
			const errors = {};
			const offeredBookIds = values[FIELD_OFFERED_BOOK_IDS];

			if (!Array.isArray(offeredBookIds) || !offeredBookIds.length) {
				errors[FIELD_OFFERED_BOOK_IDS] = 'You have to offer something';
			}

			return errors;
		},
	});

	const [requestedBook, setRequestedBook] = useState<Book | null>(null);
	const [ownBooks, setOwnBooks] = useState<Array<Book> | null>(null);

	useEffect(() => {
		BookService.fetchBook(bookId).then(setRequestedBook);
	}, [bookId]);

	useEffect(() => {
		BookService.fetchOwnBooks().then(setOwnBooks);
	}, []);

	if (!requestedBook) {
		return (<LinearProgress variant="query" />);
	}

	return (
		<Box mt={4}>
			<Typography variant="h4">
				Book Exchange Request
			</Typography>
			<Box mt={3}>
				<Typography variant="h6" gutterBottom>
					Book for exchange
				</Typography>
				<Typography variant="body1">
					{`${requestedBook.title} (${requestedBook.genre}) - ${requestedBook.authors.join(', ')}`}
				</Typography>
			</Box>
			<Box mt={3}>
				<form onSubmit={formik.handleSubmit}>
					<Typography variant="h6" gutterBottom>
						My books
					</Typography>
					{Array.isArray(ownBooks)
						? <FormControl
							required
							component="fieldset"
							error={!!Object.keys(formik.errors).length}
						>
							<FormLabel component="legend">Choose what exchange</FormLabel>
							<FormGroup>
								{ownBooks.map((book: Book, index: number) => (
									<FormControlLabel
										key={index}
										control={<Checkbox
											onChange={formik.handleChange}
											color="primary"
											name={`${FIELD_OFFERED_BOOK_IDS}`}
											value={book.id}
										/>}
										label={book.title}
									/>
								))}
							</FormGroup>
							<FormHelperText>{formik.errors[FIELD_OFFERED_BOOK_IDS]}</FormHelperText>
						</FormControl>
						: <LinearProgress variant="query" />
					}
					<TextField
						fullWidth
						margin="normal"
						multiline
						name={FIELD_ADDITIONAL_INFO}
						onChange={formik.handleChange}
						placeholder="Write something to the owner"
						rows={3}
						rowsMax={6}
						variant="outlined"
					/>
					<Box mt={2}>
						<Button
							color="primary"
							margin="normal"
							type="submit"
							variant="contained"
						>
							Apply
						</Button>
					</Box>
				</form>
			</Box>
		</Box>
	);
}

export default React.memo(ExchangeForm);

