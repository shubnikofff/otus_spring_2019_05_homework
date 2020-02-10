// @flow
import React, { useState, useEffect } from 'react';
import { RestClient } from '../../services';
import { Formik } from 'formik';
import {
	Box,
	Card,
	CardContent,
	Grid,
	IconButton,
	LinearProgress,
	Typography,
} from '@material-ui/core';
import { Delete as DeleteIcon } from '@material-ui/icons';
import CommentForm from './CommentForm';

import type { Comment, CommentFormValues } from '../../types';
import type { FormikBag } from 'formik';

type CreateCommentResponse = {|
	id: string,
|}

type CommentListProps = {|
	bookId: string,
|}

function CommentList({ bookId }: CommentListProps) {
	const [comments, setComments] = useState<Array<Comment> | null>(null);

	function fetchComments() {
		RestClient.get(`/comments?bookId=${bookId}`).then(setComments);
	}

	function getDeleteBtnClickHandler(id: string) {
		return () => {
			if (comments === null) {
				return;
			}

			RestClient.del(`/comments/${id}`)
				.then(setComments(comments.filter(comment => comment.id !== id)));
		};
	}

	useEffect(fetchComments, [bookId]);

	if (comments === null) {
		return (<LinearProgress />);
	}

	const initialValues: CommentFormValues = {
		bookId,
		text: '',
		user: '',
	};

	const handleSubmit = (values: CommentFormValues, { resetForm }: FormikBag) =>
		RestClient.post<CreateCommentResponse>(`/comments`, values)
			.then(({ id }: CreateCommentResponse) => {
				resetForm();
				setComments([{ id, ...values }, ...comments]);
			});

	return (
		<>
			<Box my={2}>
				<Card>
					<CardContent>
						<Formik
							component={CommentForm}
							initialValues={initialValues}
							onSubmit={handleSubmit}
						/>
					</CardContent>
				</Card>
			</Box>
			{comments.map(comment => (
				<Box mb={2} key={comment.id}>
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
									<IconButton size="small" onClick={getDeleteBtnClickHandler(comment.id)}>
										<DeleteIcon fontSize="small" />
									</IconButton>
								</Grid>
							</Grid>
							<Typography variant="caption">
								{comment.user}
							</Typography>
						</CardContent>
					</Card>
				</Box>
			))}
		</>
	);
}

export default CommentList;
