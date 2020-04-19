// @flow
import RestClient from './RestClient';

import type {
	Comment,
	CommentFormValues,
	Linkable,
} from 'types';

class CommentService {

	static fetchAllComments(bookId: number): Promise<Array<Linkable<Comment>>> {
		return RestClient.get(`/api/comment/search/books?bookid=${bookId}`)
			.then(response => response._embedded.comments);
	}

	static createComment(values: CommentFormValues): Promise<Linkable<Comment>> {
		return RestClient.post('/api/comment', values);
	}

	static deleteComment(url: string): Promise<void> {
		return RestClient.del(new URL(url).pathname);
	}
}

export default CommentService;
