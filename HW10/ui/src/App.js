// @flow
import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { Container } from '@material-ui/core';
import {
	Authors,
	BookList,
	BookDetails,
	Genres,
	Header,
} from './components';

function App() {
	return (
		<BrowserRouter>
			<div>
				<Header />
				<Container>
					<Route exact path="/" component={BookList} />
					<Route exact path="/book/:id" component={BookDetails} />
					<Route exact path="/genres" component={Genres} />
					<Route exact path="/authors" component={Authors} />
				</Container>
			</div>
		</BrowserRouter>
	);
}

export default App;
