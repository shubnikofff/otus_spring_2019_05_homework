// @flow
import React from 'react';
import { Route, Switch } from 'react-router-dom';
import {
	Box,
	Container,
	Grid,
} from '@material-ui/core';
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
	IncomingRequests,
	LeftMenu,
	OutgoingRequests,
	PictureDetails,
} from 'components';

type AppProps = {|
	basePath: string;
|}

function App({ basePath }: AppProps) {
	return (
		<>
			<Header basePath={basePath} />

			<Container disableGutters>
				<Grid container>

					<Grid item xs={2}>
						<Box m={3} bgcolor="">
							<LeftMenu basePath={basePath} />
						</Box>
					</Grid>


					<Grid item xs={10}>
						<Box ml={3}>
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

								<Route exact path={`${basePath}/request/incoming`}>
									<IncomingRequests basePath={basePath} />
								</Route>

								<Route exact path={`${basePath}/request/outgoing`}>
									<OutgoingRequests basePath={basePath} />
								</Route>

							</Switch>
						</Box>
					</Grid>

				</Grid>
			</Container>
		</>
	);
}

export default App;
