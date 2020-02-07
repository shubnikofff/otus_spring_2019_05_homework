// @flow
import React from 'react';
import { Link } from 'react-router-dom';
import {
	AppBar,
	Button,
	Grid,
	Toolbar,
	Typography,
} from '@material-ui/core';

function Header() {
	return (
		<AppBar position="relative">
			<Grid container justify="space-between">
				<Grid item>
					<Toolbar>
						<Typography variant="h6" color="inherit" noWrap>
							Online library
						</Typography>
						<Button to="/" color="inherit" component={Link}>
							Home
						</Button>
						<Button to="/genres" color="inherit" component={Link}>
							Genres
						</Button>
						<Button to="/authors" color="inherit" component={Link}>
							Authors
						</Button>
					</Toolbar>
				</Grid>
				<Grid item>
					<Toolbar>
						<Button to="/book/create" color="inherit" component={Link}>
							Create book
						</Button>
					</Toolbar>
				</Grid>
			</Grid>
		</AppBar>
	);
}

export default Header;
