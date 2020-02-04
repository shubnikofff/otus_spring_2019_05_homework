import React from 'react';
import { Link } from 'react-router-dom';
import {
	AppBar,
	Toolbar,
	Typography,
	Button,
} from '@material-ui/core';

function Header() {
	return (
		<AppBar position="relative">
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
		</AppBar>
	);
}

export default Header;
