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
	Book,
	Comment,
	CommentFormValues,
} from 'types';
import type { FormikBag } from 'formik';

type CommentListProps = {|
	book: Book,
|}

function CommentList({ book }: CommentListProps) {
	const [comments, setComments] = useState<Array<Comment> | null>(null);

	function fetchComments() {
		CommentService.fetchAllComments(book._links.comments.href).then(setComments);
	}

	useEffect(fetchComments, [book]);

	if (comments === null) {
		return (<LinearProgress />);
	}

	const initialValues: CommentFormValues = {
		text: '',
		username: '',
		book: book._links.self.href,
	};

	const handleSubmit = (values: CommentFormValues, { resetForm }: FormikBag) =>
		CommentService.createComment(values)
			.then((responce: Comment) => {
				resetForm();
				setComments([responce, ...comments]);
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
				const { href } = comment._links.self;
				const handleDeleteButtonClick = () => {
					CommentService.deleteComment(href)
						.then(setComments(comments.filter(comment => comment._links.self.href !== href)));
				};

				return (
					<Box mb={2} key={comment._links.self.href}>
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
