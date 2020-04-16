// @flow
import RestClient from './RestClient';

import type {
	Comment,
	CommentFormValues,
	CreateCommentResponse,
	Linkable,
} from 'types';

class CommentService {

	static fetchAllComments(bookId: string): Promise<Array<Linkable<Comment>>> {
		return RestClient.get(`/api/comment/search/books?bookid=${bookId}`)
			.then(response => response._embedded.comments);
	}

	static createComment(values: CommentFormValues): Promise<CreateCommentResponse> {
		return RestClient.post(`/comments`, values);
	}

	static deleteComment(id: string): Promise<void> {
		return RestClient.del(`/comments/${id}`);
	}
}

export default CommentService;
