// @flow
import RestClient from './RestClient';

import type { Picture } from 'types';

const BASE_PATH: string = '/picture';

class PictureService {

	static getPictureList(bookId: string): Promise<Array<Picture>> {
		return RestClient.get(`${BASE_PATH}?bookId=${bookId}`);
	}

	static uploadPicture(formData: FormData): Promise<Picture> {
		return RestClient.post(BASE_PATH, formData);
	}

	static deletePicture(bookId: string): Promise<void> {
		return RestClient.del(`${BASE_PATH}/${bookId}`)
	}
}

export default PictureService;
