// @flow
import type { Link } from 'types/link';

export type Author = {|
	name: string,
	_links: {
		self: Link,
	}
|}

export type Genre = {|
	name: string,
	_links: {
		self: Link,
	}
|}

export type Book = {|
	title: string,
	genre: Genre,
	authors: Array<Author>,
	_links: {
		self: Link,
		genre: Link,
		authors: Link,
		comments: Link,
	}
|}

export type Comment = {|
	username: string,
	text: string,
	_links: {
		self: Link,
	}
|}
