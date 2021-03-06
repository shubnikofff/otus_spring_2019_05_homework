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
	authors: Array<string>,
	pictures: Array<Picture>,
	owned: boolean,
|}

export type Comment = {|
	id: string,
	username: string,
	text: string,
|}

export type Picture = {|
	id: string,
	name: string,
	uploadDate: string,
|}

export type BookCompleteData = {|
	id: string,
	title: string,
	genre: string,
	authors: Array<string>,
	comments: Array<Comment>,
	pictures: Array<Picture>,
	owner: UserProfile,
	owned: boolean,
|}

export type UserProfile = {|
	firstName: string,
	lastName: string,
	email: string,
|}

export type ExchangeRequest = {|
	id: string,
	requestedBook: {|
		id: string,
		title: string,
		genre: string,
		authors: Array<string>,
		owner: UserProfile,
	|},
	offeredBooks: Array<Book>,
	additionalInfo: string,
	user: UserProfile,
	requestedDate: string,
|}
