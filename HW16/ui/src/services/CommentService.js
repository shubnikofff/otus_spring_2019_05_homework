// @flow
import RestClient from './RestClient';

import type { Comment, CommentFormValues } from 'types';

const BASE_URL: string = '/api/comment';

class CommentService {

	static fetchAllComments(url: string): Promise<Array<Comment>> {
		return RestClient.get(new URL(url).pathname)
			.then(response => response._embedded.comments);
	}

	static createComment(values: CommentFormValues): Promise<Comment> {
		return RestClient.post(BASE_URL, values);
	}

	static deleteComment(url: string): Promise<void> {
		return RestClient.del(new URL(url).pathname);
	}
}

export default CommentService;
