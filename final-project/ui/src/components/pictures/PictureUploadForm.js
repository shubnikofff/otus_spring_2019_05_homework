// @flow
import React, { useRef, useState } from 'react';

import { PictureService } from 'services';

import {
	Box,
	Button,
	Grid,
	LinearProgress,
} from '@material-ui/core';

import type { Picture } from 'types';

type PictureUploadFormProps = {|
	bookId: string,
	onUpload: (picture: Picture) => void,
|}

function PictureUploadForm({ bookId, onUpload }: PictureUploadFormProps) {
	const [selectedFile, setSelectedFile] = useState<File | null>(null);
	const [uploadInProgress, setUploadInProgress] = useState<boolean>(false);
	const inputRef = useRef(null);

	function handleChangeFile(event) {
		setSelectedFile(event.target.files[0]);
	}

	function handleUploadClick() {
		if (selectedFile) {
			const formData = new FormData();
			formData.append("file", selectedFile, selectedFile.name);
			formData.append("bookId", bookId);
			setUploadInProgress(true);
			PictureService.uploadPicture(formData).then((id: Picture) => {
				setSelectedFile(null);
				setUploadInProgress(false);
				if (typeof onUpload === 'function') {
					onUpload(id);
				}
			});
		}
	}

	function handleChooseFileClick() {
		if (inputRef && inputRef.current) {
			inputRef.current.click();
		}
	}

	if (uploadInProgress) {
		return (
			<LinearProgress />
		);
	}

	return (
		<form>
			<input
				accept="image/*"
				onChange={handleChangeFile}
				ref={inputRef}
				style={{ display: 'none' }}
				type="file"
			/>
			<Grid container>
				<Grid item>
					<Box mr={2}>
						<Button
							color="primary"
							onClick={handleChooseFileClick}
							variant="outlined"
						>
							Choose file
						</Button>
					</Box>
				</Grid>
				<Grid item>
					<Button
						color="primary"
						disabled={!selectedFile}
						onClick={handleUploadClick}
						variant="contained"
					>
						{selectedFile ? `Upload ${selectedFile.name}` : 'Upload'}
					</Button>
				</Grid>
			</Grid>
		</form>
	);
}

export default PictureUploadForm;
