// @flow
import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { Container } from '@material-ui/core';
import {
	AuthorList,
	BookCreate,
	BookDelete,
	BookDetails,
	BookList,
	BookUpdate,
	GenreList,
	Header,
} from 'components';

function App() {
	return (
		<BrowserRouter>
			<div>
				<Header />
				<Container>
					<Route exact path="/" component={BookList} />
					<Route exact path="/authors" component={AuthorList} />
					<Route exact path="/book/:id" component={BookDetails} />
					<Route exact path="/book/:id/delete" component={BookDelete} />
					<Route exact path="/book/:id/edit" component={BookUpdate} />
					<Route exact path="/create-book" component={BookCreate} />
					<Route exact path="/genres" component={GenreList} />
				</Container>
			</div>
		</BrowserRouter>
	);
}

export default App;
