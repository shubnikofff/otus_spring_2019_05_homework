// @flow
import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { Container } from '@material-ui/core';
import {
	AuthorList,
	BookCreate,
	BookDelete,
	BookDetails,
	BookList,
	BookUpdate,
	ExchangeForm,
	GenreList,
	Header,
	PictureDetails,
} from 'components';

type AppProps = {|
	basePath: string;
|}

function App({ basePath }: AppProps) {
	return (
		<>
			<Header basePath={basePath} />

			<Container>

				<Switch>

					<Route exact path={basePath}>
						<BookList basePath={`${basePath}/book`} />
					</Route>

					<Route exact path={`${basePath}/book/create`}>
						<BookCreate basePath={`${basePath}/book`} />
					</Route>

					<Route exact path={`${basePath}/book/:id`}>
						<BookDetails basePath={`${basePath}/book`} />
					</Route>

					<Route exact path={`${basePath}/book/:id/delete`}>
						<BookDelete basePath={`${basePath}/book`} />
					</Route>

					<Route exact path={`${basePath}/book/:id/update`}>
						<BookUpdate basePath={`${basePath}/book`} />
					</Route>

					<Route exact path={`${basePath}/book/:id/exchange`}>
						<ExchangeForm basePath={`${basePath}/book`} />
					</Route>

					<Route exact path={`${basePath}/author`}>
						<AuthorList basePath={`${basePath}/author`} />
					</Route>

					<Route exact path={`${basePath}/genre`}>
						<GenreList basePath={`${basePath}/genre`} />
					</Route>

					<Route exact path={`${basePath}/picture`}>
						<PictureDetails basePath={`${basePath}/picture`} />
					</Route>

				</Switch>

			</Container>
		</>
	);
}

export default App;
