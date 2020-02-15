// @flow
import React, { useState, useEffect } from 'react';
import { RestClient } from 'services';
import { Formik } from 'formik';
import {
	Box,
	Card,
	CardContent,
	LinearProgress,
} from '@material-ui/core';
import CommentForm from './CommentForm';
import CommentListItem from './CommentListItem';

import type { Comment, CommentFormValues } from 'types';
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
			{comments.map(comment => {
				const { id } = comment;
				const handleDeleteButtonClick = () => {
					RestClient.del(`/comments/${id}`)
						.then(setComments(comments.filter(comment => comment.id !== id)));
				};

				return (
					<Box mb={2} key={id}>
						<CommentListItem
							comment={comment}
							onDeleteButtonClick={handleDeleteButtonClick}
						/>
					</Box>
				);
			})}
		</>
	);
}

export default CommentList;
