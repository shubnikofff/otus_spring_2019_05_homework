// @flow
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { PictureService } from 'services';

import {
	Box,
	GridList,
	GridListTile,
	GridListTileBar,
	LinearProgress,
	IconButton,
} from '@material-ui/core';
import SearchIcon from '@material-ui/icons/Search';

import type { Picture } from 'types';

import PictureUploadForm from './PictureUploadForm';

type PictureListProps = {|
	bookId: string,
|}

function PictureList({ bookId }: PictureListProps) {
	const [pictures, setPictures] = useState<Array<Picture> | null>(null);

	useEffect(() => {
		PictureService.getPictureList(bookId).then(setPictures);
	}, [bookId]);

	if (!pictures) {
		return (<LinearProgress />);
	}

	function handleUpload(picture: Picture): void {
		setPictures([picture, ...pictures]);
	}

	return (
		<>
			<Box mt={2}>
				<PictureUploadForm
					bookId={bookId}
					onUpload={handleUpload}
				/>
			</Box>
			<Box mt={2}>
				<GridList cols={3} style={{ flexWrap: 'nowrap' }}>
					{pictures.map((picture: Picture) => (
						<GridListTile key={picture.id}>
							<img src={`/api/picture/${picture.id}`} alt="Book" />
							<GridListTileBar
								title={picture.name}
								subtitle={picture.uploadDate}
								actionIcon={
									<IconButton
										to={{
											pathname: '/picture',
											state: { picture },
										}}
										component={Link}
									>
										<SearchIcon style={{ color: 'white' }} />
									</IconButton>
								}
							/>
						</GridListTile>
					))}
				</GridList>
			</Box>
		</>
	);
}

export default PictureList;
