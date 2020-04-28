// @flow
import RestClient from './RestClient';

import type { Book, BookFormValues } from 'types';

const BASE_PATH: string = '/book';

class BookService {

	static fetchAllBooks(): Promise<Array<Book>> {
		return RestClient.get(BASE_PATH);
	}

	static fetchBook(id: string): Promise<Book> {
		return RestClient.get(`${BASE_PATH}/${id}`);
	}

	static createBook(values: BookFormValues): Promise<void> {
		return RestClient.post(BASE_PATH, values);
	}

	static updateBook(id: string, values: BookFormValues): Promise<void> {
		return RestClient.put(`${BASE_PATH}/${id}`, values);
	}

	static deleteBook(id: string): Promise<void> {
		return RestClient.del(`${BASE_PATH}/${id}`);
	}
}

export default BookService;
