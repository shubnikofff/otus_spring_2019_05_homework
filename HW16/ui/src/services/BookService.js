// @flow
import RestClient from './RestClient';

import type { Book, BookFormValues } from 'types';

const BASE_URL: string = '/api/book';

function withAllFieldsProjection(url: string): string {
	return `${url}?projection=allFields`;
}

class BookService {

	static fetchAllBooks(): Promise<Array<Book>> {
		return RestClient.get(withAllFieldsProjection(BASE_URL))
			.then(response => response._embedded.books);
	}

	static fetchBook(url: string, withProjection: boolean = false): Promise<Book> {
		const parsedUrl = new URL(url);
		return RestClient.get(withProjection
			? withAllFieldsProjection(parsedUrl.pathname + parsedUrl.search)
			: parsedUrl.pathname,
		);
	}

	static createBook(values: BookFormValues): Promise<void> {
		return RestClient.post(BASE_URL, values);
	}

	static updateBook(url: string, values: BookFormValues): Promise<void> {
		return RestClient.put(new URL(url).pathname, values);
	}

	static deleteBook(url: string): Promise<void> {
		return RestClient.del(new URL(url).pathname);
	}
}

export default BookService;
