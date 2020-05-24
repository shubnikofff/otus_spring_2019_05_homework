// @flow
import React from 'react';
import { AuthService } from 'services';

import { Link } from 'react-router-dom';
import {
	AppBar,
	Button,
	Grid,
	Toolbar,
	Typography,
} from '@material-ui/core';

type HeaderProps = {|
	basePath: string
|}

function Header({ basePath }: HeaderProps) {
	return (
		<AppBar position="relative">
			<Grid container justify="space-between">
				<Grid item>
					<Toolbar>
						<Typography variant="h6" color="inherit" noWrap>
							Open Shelf
						</Typography>
						<Button to={basePath} color="inherit" component={Link}>
							Home
						</Button>
						<Button to={`${basePath}/genre`} color="inherit" component={Link}>
							Genres
						</Button>
						<Button to={`${basePath}/author`} color="inherit" component={Link}>
							Authors
						</Button>
						<Button to={`${basePath}/book/create`} color="inherit" component={Link}>
							Create book
						</Button>
					</Toolbar>
				</Grid>
				<Grid item>
					<Toolbar>
						<Button
							to="/login"
							color="inherit"
							component={Link}
							onClick={AuthService.logout}
						>
							Logout
						</Button>
					</Toolbar>
				</Grid>
			</Grid>
		</AppBar>
	);
}

export default Header;
