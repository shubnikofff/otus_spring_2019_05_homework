// @flow
import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { Container } from '@material-ui/core';
import {
	Authors,
	BookCreate,
	BookDetails,
	BookList,
	BookUpdate,
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
					<Route exact path="/authors" component={Authors} />
					<Route exact path="/book/:id" component={BookDetails} />
					<Route exact path="/book/:id/edit" component={BookUpdate} />
					<Route exact path="/create-book" component={BookCreate} />
					<Route exact path="/genres" component={Genres} />
				</Container>
			</div>
		</BrowserRouter>
	);
}

export default App;
