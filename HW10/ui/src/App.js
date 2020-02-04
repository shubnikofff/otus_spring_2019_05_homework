// @flow
import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import {
	Authors,
	Books,
	Genres,
	Header,
} from './components';

function App() {
	return (
		<BrowserRouter>
			<div>
				<Header />
				<Route exact path="/" component={Books} />
				<Route exact path="/genres" component={Genres} />
				<Route exact path="/authors" component={Authors} />
			</div>
		</BrowserRouter>
	);
}

export default App;
