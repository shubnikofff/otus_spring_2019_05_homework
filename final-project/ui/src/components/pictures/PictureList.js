// @flow
import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import {
	Box,
	GridList,
	GridListTile,
	GridListTileBar,
	IconButton,
} from '@material-ui/core';
import SearchIcon from '@material-ui/icons/Search';

import type { Picture } from 'types';

import PictureUploadForm from './PictureUploadForm';

type PictureListProps = {|
	bookId: string,
	items: Array<Picture>,
|}

function PictureList({ bookId, items }: PictureListProps) {
	const [pictures, setPictures] = useState<Array<Picture>>(items);

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
