// @flow
import RestClient from './RestClient';

import type { Book, BookFormValues } from 'types';

class BookService {

	static fetchAllBooks(): Promise<Array<Book>> {
		return RestClient.get('/books');
	}

	static fetchBook(id: string): Promise<Book> {
		return RestClient.get(`/books/${id}`);
	}

	static createBook(values: BookFormValues): Promise<void> {
		return RestClient.post('/books', values);
	}

	static updateBook(id: string, values: BookFormValues): Promise<void> {
		return RestClient.put(`/books/${id}`, values);
	}

	static deleteBook(id: string): Promise<void> {
		return RestClient.del(`/books/${id}`);
	}
}

export default BookService;
