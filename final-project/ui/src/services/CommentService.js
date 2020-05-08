// @flow
import RestClient from './RestClient';

import type {
	Comment,
	CommentFormValues,
	CreateCommentResponse,
} from 'types';

const BASE_PATH: string = '/comment';

class CommentService {

	static fetchAllComments(bookId: string): Promise<Array<Comment>> {
		return RestClient.get(`${BASE_PATH}?bookId=${bookId}`);
	}

	static createComment(values: CommentFormValues): Promise<CreateCommentResponse> {
		return RestClient.post(BASE_PATH, values);
	}

	static deleteComment(id: string): Promise<void> {
		return RestClient.del(`${BASE_PATH}/${id}`);
	}
}

export default CommentService;
