// @flow
import React, { useState } from 'react';
import { GenreService } from 'services';

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
import type { Genre, GenreFormValues } from 'types';

import { FIELD_NAME } from 'constants/fields';

type GenreListItemProps = {|
	genre: Genre,
|}

function GenreListItem({ genre }: GenreListItemProps) {
	const [editMode, setEditMode] = useState<boolean>(false);
	const [genreName, setGenreName] = useState<string>(genre.name);
	const initialValues: GenreFormValues = { name: genreName };

	function handleSubmit(values: GenreFormValues) {
		return GenreService.updateGenre(genre._links.self.href, values)
			.then((response: Genre) => {
				setGenreName(response.name);
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
			>{({ isSubmitting }: FormikProps<GenreFormValues>) => (
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
			<ListItemText primary={genreName} />
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

export default GenreListItem;
