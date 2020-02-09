// @flow
export type BookFormValues = {|
	title: string,
	genre: string,
	authors: Array<string>
|}

export type AuthorFormValues = {|
	name: string,
|}

export type GenreFormValues = {|
	name: string,
|}

export type CommentFormValues = {|
	user: string,
	text: string,
|}
