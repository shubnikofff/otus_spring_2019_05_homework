// @flow
export type Author = {|
	name: string
|}

export type Genre = {|
	name: string
|}

export type Book = {|
	id: number,
	title: string,
	genre: Genre,
	authors: Array<Author>
|}

export type Comment = {|
	username: string,
	text: string,
	bookId: string,
|}
