// @flow
import React from 'react';
import { useFormik } from 'formik';
import { useHistory } from 'react-router';
import { Grid, TextField, Button, Box } from '@material-ui/core';

import { AuthService } from 'services';

import { FIELD_USERNAME, FIELD_PASSWORD } from 'constants/fields';

type FormValues = {|
	username: string,
	password: string,
|}

function Login() {

	const history = useHistory();

	const formik = useFormik({
		initialValues: {
			[FIELD_USERNAME]: "",
			[FIELD_PASSWORD]: "",
		},
		onSubmit: ({ username, password }: FormValues) => {
			return AuthService.login(username, password).then(() => {
				history.push('/app');
			});
		},
	});

	return (

		<form onSubmit={formik.handleSubmit}>
			<Grid
				container
				direction="column"
				justify="center"
				alignContent="center"
				style={{ minHeight: '100vh' }}
			>
				<Box>
					<TextField
						fullWidth
						label="Username"
						name={FIELD_USERNAME}
						onChange={formik.handleChange}
						required
						variant="outlined"
					/>
				</Box>
				<Box mt={2}>
					<TextField
						fullWidth
						label="Password"
						name={FIELD_PASSWORD}
						onChange={formik.handleChange}
						required
						variant="outlined"
						type="password"
					/>
				</Box>
				<Box mt={4}>
					<Button
						fullWidth
						color="primary"
						disabled={formik.isSubmitting}
						type="submit"
						variant="contained"
						size="large"
					>
						Login
					</Button>
				</Box>
			</Grid>
		</form>
	);
}

export default Login;
