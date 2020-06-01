// @flow
import React, { useState } from 'react';
import { CommentService } from 'services';
import { Formik } from 'formik';
import {
	Box,
	Card,
	CardContent,
} from '@material-ui/core';
import CommentForm from './CommentForm';
import CommentListItem from './CommentListItem';

import type {
	Comment,
	CommentFormValues,
} from 'types';
import type { FormikBag } from 'formik';

type CommentListProps = {|
	bookId: string,
	items: Array<Comment>,
|}

function CommentList({ bookId, items }: CommentListProps) {
	const [comments, setComments] = useState<Array<Comment>>(items);

	const initialValues: CommentFormValues = {
		text: '',
		username: '',
	};

	const handleSubmit = (values: CommentFormValues, { resetForm }: FormikBag) =>
		CommentService.createComment(bookId, values)
			.then((id: string) => {
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
