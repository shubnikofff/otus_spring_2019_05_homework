// @flow
export type Author = {|
	name: string
|}

export type Book = {|
	id: string,
	title: string,
	genre: Genre,
	authors: Array<Author>
|}

export type Comment = {|
	id: string,
	user: string,
	text: string,
	bookId: string,
|}

export type Genre = {|
	name: string
|}
