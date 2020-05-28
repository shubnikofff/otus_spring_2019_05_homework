// @flow
import React from 'react';
import { AuthService } from 'services';

import { Link } from 'react-router-dom';
import {
	AppBar,
	Box,
	Button,
	Grid,
	Toolbar,
	Typography,
} from '@material-ui/core';

type HeaderProps = {|
	basePath: string
|}

function Header({ basePath }: HeaderProps) {
	const userProfile = AuthService.getUserProfile();

	return (
		<AppBar position="relative">
			<Grid container justify="space-between">

				<Grid item>
					<Toolbar>
						<Box pr={3}>
							<Typography variant="h5" color="inherit">
								Open Shelf
							</Typography>
						</Box>
						<Button to={basePath} color="inherit" component={Link}>
							Books
						</Button>
						<Button to={`${basePath}/genre`} color="inherit" component={Link}>
							Genres
						</Button>
						<Button to={`${basePath}/author`} color="inherit" component={Link}>
							Authors
						</Button>
					</Toolbar>
				</Grid>

				<Grid item>
					<Toolbar>
						<Typography variant="subtitle1">
							Welcome, {`${userProfile.firstName} ${userProfile.lastName}`}
						</Typography>
					</Toolbar>
				</Grid>

			</Grid>
		</AppBar>
	);
}

export default Header;
