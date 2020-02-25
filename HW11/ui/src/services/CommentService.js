// @flow
import RestClient from './RestClient';

import type {
	Comment,
	CommentFormValues,
	CreateCommentResponse,
} from 'types';

class CommentService {

	static fetchAllComments(bookId: string): Promise<Array<Comment>> {
		return RestClient.get(`/comments?bookId=${bookId}`);
	}

	static createComment(values: CommentFormValues): Promise<CreateCommentResponse> {
		return RestClient.post(`/comments`, values);
	}

	static deleteComment(id: string): Promise<void> {
		return RestClient.del(`/comments/${id}`);
	}
}

export default CommentService;
