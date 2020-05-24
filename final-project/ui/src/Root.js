// @flow
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { Route, Redirect, Switch } from 'react-router';

import { Login } from 'components';

import { AuthService } from 'services';

import App from './App';

function Root() {
	return (
		<BrowserRouter>

			<Switch>

				<Route exact path="/">
					<Redirect
						from="/"
						to={AuthService.isUserLoggedIn() ? '/app' : '/login'}
					/>
				</Route>

				<Route path="/app">
					<App basePath="/app" />
				</Route>

				<Route
					exact
					path="/login"
					component={Login}
				/>

			</Switch>

		</BrowserRouter>
	);
}

export default Root;
