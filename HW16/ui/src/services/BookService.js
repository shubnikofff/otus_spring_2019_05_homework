// @flow
import RestClient from './RestClient';

import type { Book, BookFormValues, Linkable } from 'types';

function withAllFieldsProjection(url: string): string {
	return `${url}?projection=allFields`;
}

class BookService {

	static fetchAllBooks(): Promise<Array<Linkable<Book>>> {
		return RestClient.get(withAllFieldsProjection('/api/book'))
			.then(response => response._embedded.books);
	}

	static fetchBook(url: string): Promise<Linkable<Book>> {
		const parsedUrl = new URL(url);
		return RestClient.get(withAllFieldsProjection(parsedUrl.pathname + parsedUrl.search));
	}

	static createBook(values: BookFormValues): Promise<void> {
		return RestClient.post('/book', values);
	}

	static updateBook(url: string, values: BookFormValues): Promise<void> {
		const parsedUrl = new URL(url);
		return RestClient.put(parsedUrl.pathname, values);
	}

	static deleteBook(id: string): Promise<void> {
		return RestClient.del(`/book/${id}`);
	}
}

export default BookService;
