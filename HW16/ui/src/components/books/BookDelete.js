// @flow
import React, { useState } from 'react';
import { BookService } from 'services';
import { Link, useHistory, useLocation } from 'react-router-dom';
import {
	Box,
	Button,
	Grid,
	Snackbar,
	Typography,
} from '@material-ui/core';
import { Alert } from '@material-ui/lab';

import type { AxiosError } from 'axios';

const MESSAGE_DELAY_TIME = 6000;

function BookDelete() {
	const { state: { href } } = useLocation();
	const history = useHistory();
	const [operationError, setOperationError] = useState<?string>(null);

	const handleYesButtonClick = () => {
		BookService.deleteBook(href)
			.then(() => {
				history.push('/');
			})
			.catch((error: AxiosError) => {
				setOperationError(error.message);
			});
	};

	return (
		<>
			<Box mt="25%">
				<Typography variant="h4" align="center" color="textPrimary" gutterBottom>
					Are you sure you want to delete this book ?
				</Typography>
				<Grid
					container
					justify="center"
					alignItems="center"
				>
					<Button
						color="secondary"
						onClick={handleYesButtonClick}
						size="large"
						variant="contained"
					>
						Yes
					</Button>
					<Button
						color="primary"
						component={Link}
						size="large"
						to="/"
					>
						No
					</Button>
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

export default BookDelete;
