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
	genre: string,
	authors: Array<string>
|}

export type Comment = {|
	id: string,
	user: string,
	text: string,
	bookId: string,
|}
