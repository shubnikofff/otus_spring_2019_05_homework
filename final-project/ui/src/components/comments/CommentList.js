// @flow
import React, { useState, useEffect } from 'react';
import { CommentService } from 'services';
import { Formik } from 'formik';
import {
	Box,
	Card,
	CardContent,
	LinearProgress,
} from '@material-ui/core';
import CommentForm from './CommentForm';
import CommentListItem from './CommentListItem';

import type {
	Comment,
	CommentFormValues,
	CreateCommentResponse,
} from 'types';
import type { FormikBag } from 'formik';

type CommentListProps = {|
	bookId: string,
|}

function CommentList({ bookId }: CommentListProps) {
	const [comments, setComments] = useState<Array<Comment> | null>(null);

	function fetchComments() {
		CommentService.fetchAllComments(bookId).then(setComments);
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
		CommentService.createComment(values)
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
					CommentService.deleteComment(id)
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
