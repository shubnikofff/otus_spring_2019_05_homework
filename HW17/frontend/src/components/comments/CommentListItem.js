// @flow
import React from 'react';

import type { Comment } from 'types';
import {
	Card,
	CardContent,
	Grid,
	IconButton,
	Typography,
} from '@material-ui/core';
import { Delete as DeleteIcon } from '@material-ui/icons';

type CommentListItemProps = {|
	comment: Comment,
	onDeleteButtonClick: () => void,
|}

function CommentListItem({ comment, onDeleteButtonClick }: CommentListItemProps) {
	return (
		<Card>
			<CardContent>
				<Grid
					container
					direction="row"
					justify="space-between"
					alignItems="flex-start"
				>
					<Grid item>
						<Typography variant="body2">
							{comment.text}
						</Typography>
					</Grid>
					<Grid item>
						<IconButton
							onClick={onDeleteButtonClick}
							size="small"
						>
							<DeleteIcon fontSize="small" />
						</IconButton>
					</Grid>
				</Grid>
				<Typography variant="caption">
					{comment.user}
				</Typography>
			</CardContent>
		</Card>
	);
}

export default CommentListItem;
