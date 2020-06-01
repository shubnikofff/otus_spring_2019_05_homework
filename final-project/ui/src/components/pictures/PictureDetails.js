// @flow
import React from 'react';
import { useHistory, useLocation } from 'react-router';
import { PictureService } from 'services';
import { Grid, Box, Button } from '@material-ui/core';

function PictureDetails() {
	const { picture } = useLocation().state;
	const history = useHistory();

	if (!picture) {
		return null;
	}

	function handleDeleteClick() {
		PictureService.deletePicture(picture.id).then(history.goBack);
	}

	return (
		<>
			<Box mt={4}>
				<Grid container>
					<Box mr={2}>
						<Button
							onClick={history.goBack}
							color="primary"
							variant="outlined"
						>
							Back
						</Button>
					</Box>
					<Button
						color="secondary"
						variant="contained"
						onClick={handleDeleteClick}
					>
						Delete
					</Button>
				</Grid>
			</Box>
			<Box mt={4}>
				<Grid container justify="center">
					<Grid item>
						<img
							alt="Book"
							src={`/api/picture/${picture.id}`}
							style={{
								maxWidth: "100%",
							}}
						/>
					</Grid>
				</Grid>
			</Box>
		</>
	);
}

export default PictureDetails;
