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
import { ArrowForward } from '@material-ui/icons';

import type { Book, ExchangeRequest } from 'types';
import type { Element } from 'react';

type OutgoingRequestsProps = {|
	basePath: string,
|}

function OutgoingRequests({ basePath }: OutgoingRequestsProps) {
	const [requests, setRequests] = useState<Array<ExchangeRequest> | null>(null);

	useEffect(() => {
		ExchangeService.getOutgoingRequests().then(setRequests);
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
							title={request.requestedDate}
						/>
						<CardContent>
							<Grid container alignItems="center">
								<Grid item xs={5}>
									<List>
										<ListItem disableGutters>
											<Typography
												variant="subtitle1"
												color="textSecondary"
											>
												Offered book:
											</Typography>
										</ListItem>
										{request.offeredBooks.map((book: Book, index: number) => (
											<ListItem
												disableGutters
												key={index}
												component={Link}
												to={`${basePath}/book/${book.id}`}
											>
												{`${(book.title)} - ${book.authors.join(', ')}`}
											</ListItem>
										))}
									</List>
								</Grid>
								<Grid item xs>
									<ArrowForward />
								</Grid>
								<Grid item xs={5}>
									<ListItem>
										<Typography variant="subtitle1" color="textSecondary">
											Requested book:
										</Typography>
									</ListItem>
									<List>
										<ListItem
											component={Link}
											to={`${basePath}/book/${request.requestedBook.id}`}
										>
											{`${request.requestedBook.title} - ${request.requestedBook.authors.join(', ')}`}
										</ListItem>
										<ListItem>
											<Typography variant="body2" color="textSecondary">
												<span>Owner: <b>{`${request.requestedBook.owner.firstName} ${request.requestedBook.owner.lastName}`}</b></span>
											</Typography>
										</ListItem>
										<ListItem>
											<Typography variant="body2" color="textSecondary">
												{`Email: ${request.requestedBook.owner.email}`}
											</Typography>
										</ListItem>
									</List>
								</Grid>
							</Grid>
							<Typography variant="body2">
								<span><b>My message:</b> {request.additionalInfo}</span>
							</Typography>
						</CardContent>
						<CardActions>
							<Grid container justify="flex-end">
								<Button
									color="secondary"
								>
									Cancel
								</Button>
							</Grid>
						</CardActions>
					</Card>
				</Box>
			))}
		</Box>
	);
}

export default React.memo<OutgoingRequestsProps>(OutgoingRequests);
