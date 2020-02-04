import React from 'react';
import { AppBar, Toolbar, Typography } from '@material-ui/core';

function App() {
	return (
		<div className="App">
			<AppBar position="relative" >
				<Toolbar>
					<Typography variant="h6" color="inherit" noWrap>
						Online library
					</Typography>
				</Toolbar>
			</AppBar>
		</div>
	);
}

export default App;
