// @flow
import React, { useEffect, useState } from 'react';
import { ExchangeService } from 'services';

import { Link } from 'react-router-dom';
import {
	Box,
	Button,
	Card,
	CardActions,
	CardContent,
	CardHeader,
	Grid,
	LinearProgress,
	List,
	ListItem,
	Typography,
} from '@material-ui/core';
import { ArrowBack } from '@material-ui/icons';

import type { Book, ExchangeRequest } from 'types';
import type { Element } from 'react';

type IncomingRequestsProps = {|
	basePath: string,
|}

function IncomingRequests({ basePath }: IncomingRequestsProps) {
	const [requests, setRequests] = useState<Array<ExchangeRequest> | null>(null);

	useEffect(() => {
		ExchangeService.getIncomingRequests().then(setRequests);
	}, []);

	if (requests === null) {
		return (<LinearProgress variant="query" />);
	}

	return (
		<Box mt={3}>
			{requests.map((request: ExchangeRequest, index: number): Element<typeof React.Fragment> => (
				<Box mt={3} key={index}>
					<Card>
						<CardHeader
							title={`${request.user.firstName} ${request.user.lastName}`}
							subheader={request.requestedDate}
						/>
						<CardContent>
							<Grid container alignItems="center">
								<Grid item xs={5}>
									<List>
										<ListItem disableGutters>
											<Typography variant="subtitle1" color="textSecondary">
												Requested book:
											</Typography>
										</ListItem>
										<ListItem
											component={Link}
											disableGutters
											to={`${basePath}/book/${request.requestedBook.id}`}
										>
											{`${request.requestedBook.title} - ${request.requestedBook.authors.join(', ')}`}
										</ListItem>
									</List>
								</Grid>
								<Grid item xs>
									<ArrowBack />
								</Grid>
								<Grid item xs={5}>
									<List>
										<ListItem>
											<Typography variant="subtitle1" color="textSecondary">
												Offered book:
											</Typography>
										</ListItem>
										{request.offeredBooks.map((book: Book, index: number) => (
											<ListItem
												key={index}
												component={Link}
												to={`${basePath}/book/${book.id}`}
											>
												{`${(book.title)} - ${book.authors.join(', ')}`}
											</ListItem>
										))}
									</List>
								</Grid>
							</Grid>
							<Typography variant="body2">
								<span><b>Additional info:</b> {request.additionalInfo}</span>
							</Typography>
						</CardContent>
						<CardActions>
							<Grid container justify="flex-end">
								<Button
									color="primary"
									onClick={() => {
										ExchangeService.acceptRequest(request.id)
											.then(setRequests(requests.filter(r => r.id !== request.id)));
									}}
								>
									Accept
								</Button>
								<Button
									color="secondary"
									onClick={() => {
										ExchangeService.deleteRequest(request.id)
											.then(setRequests(requests.filter(r => r.id !== request.id)));
									}}
								>
									Refuse
								</Button>
							</Grid>
						</CardActions>
					</Card>
				</Box>
			))}
		</Box>
	);
}

export default React.memo<IncomingRequestsProps>(IncomingRequests);
