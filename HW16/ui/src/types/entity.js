// @flow
export type Author = {|
	name: string
|}

export type Genre = {|
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
	username: string,
	text: string,
	bookId: string,
|}
