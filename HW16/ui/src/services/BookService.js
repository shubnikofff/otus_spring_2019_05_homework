// @flow
import RestClient from './RestClient';

import type { Book, BookFormValues } from 'types';

class BookService {

	static fetchAllBooks(): Promise<Array<Book>> {
		return RestClient.get('/book?projection=allFields').then(response => response._embedded.books);
	}

	static fetchBook(id: string): Promise<Book> {
		return RestClient.get(`/book/${id}`);
	}

	static createBook(values: BookFormValues): Promise<void> {
		return RestClient.post('/book', values);
	}

	static updateBook(id: string, values: BookFormValues): Promise<void> {
		return RestClient.put(`/book/${id}`, values);
	}

	static deleteBook(id: string): Promise<void> {
		return RestClient.del(`/book/${id}`);
	}
}

export default BookService;
