// @flow
import RestClient from './RestClient';

import type { Book, BookFormValues, BookCompleteData } from 'types';

const BASE_PATH: string = '/book';

class BookService {

	static fetchAllBooks(): Promise<Array<Book>> {
		return RestClient.get('/cs/book');
	}

	static fetchBook(id: string): Promise<Book> {
		return RestClient.get(`${BASE_PATH}/${id}`);
	}

	static fetchBookCompleteData(id: string): Promise<BookCompleteData> {
		return RestClient.get(`/cs/book/${id}`);
	}

	static createBook(values: BookFormValues): Promise<void> {
		return RestClient.post(BASE_PATH, values);
	}

	static updateBook(id: string, values: BookFormValues): Promise<void> {
		return RestClient.put(`${BASE_PATH}/${id}`, values);
	}

	static deleteBook(id: string): Promise<void> {
		return RestClient.del(`/cs/book/${id}`);
	}
}

export default BookService;
