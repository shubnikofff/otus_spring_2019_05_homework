// @flow
import React from 'react';

import { AuthService } from 'services';

import { Link, useLocation } from 'react-router-dom';
import { MenuItem, MenuList } from '@material-ui/core';

type LeftMenuProps = {|
	basePath: string
|}

function LeftMenu({ basePath }: LeftMenuProps) {
	const location = useLocation();

	return (
		<MenuList>
			<MenuItem
				component={Link}
				to={`${basePath}/book/create`}
				selected={location.pathname === `${basePath}/book/create`}
			>
				Add new book
			</MenuItem>
			<MenuItem
				component={Link}
				to={`${basePath}/request/incoming`}
				selected={location.pathname === `${basePath}/request/incoming`}
			>
				Incoming requests
			</MenuItem>
			<MenuItem
				component={Link}
				to={`${basePath}/request/outgoing`}
				selected={location.pathname === `${basePath}/request/outgoing`}
			>
				My requests
			</MenuItem>
			<MenuItem
				component={Link}
				to={basePath}
			>
				My books
			</MenuItem>
			<MenuItem
				component={Link}
				onClick={AuthService.logout}
				to="/login"
			>
				Logout
			</MenuItem>
		</MenuList>
	);
}

export default LeftMenu;
