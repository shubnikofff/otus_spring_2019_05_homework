// @flow
import React, { useState } from 'react';
import { AuthorService } from 'services';

import { Formik, Form } from 'formik';
import { TextField } from 'formik-material-ui';
import {
	IconButton,
	ListItem,
	ListItemSecondaryAction,
	ListItemText,
} from '@material-ui/core';
import {
	Edit as EditIcon,
	Done as DoneIcon,
} from '@material-ui/icons';

import type { FormikProps } from 'formik';
import type { Author, AuthorFormValues } from 'types';

import { FIELD_NAME } from 'constants/fields';

type AuthorListItemProps = {|
	author: Author
|}

function AuthorListItem({ author }: AuthorListItemProps) {
	const [editMode, setEditMode] = useState<boolean>(false);
	const [authorName, setAuthorName] = useState<string>(author.name);
	const initialValues: AuthorFormValues = { name: authorName };

	function handleSubmit(values: AuthorFormValues) {
		return AuthorService.updateAuthor(author._links.self.href, values)
			.then((response: Author) => {
				setAuthorName(response.name);
			})
			.finally(() => {
				setEditMode(false);
			});
	}

	if (editMode) {
		return (
			<Formik
				initialValues={initialValues}
				onSubmit={handleSubmit}
			>{({ isSubmitting }: FormikProps<AuthorFormValues>) => (
				<Form>
					<ListItem>
						<TextField
							fullWidth
							label="Name"
							name={FIELD_NAME}
							required
							variant="outlined"
						/>
						<IconButton
							disabled={isSubmitting}
							type="submit"
						>
							<DoneIcon fontSize="small" />
						</IconButton>
					</ListItem>
				</Form>
			)}
			</Formik>
		);
	}

	return (
		<ListItem>
			<ListItemText primary={authorName} />
			<ListItemSecondaryAction>
				<IconButton onClick={() => {
					setEditMode(true);
				}}>
					<EditIcon fontSize="small" />
				</IconButton>
			</ListItemSecondaryAction>
		</ListItem>
	);
}

export default AuthorListItem;
