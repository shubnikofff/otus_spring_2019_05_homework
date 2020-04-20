// @flow
export type Link = {|
	href: string,
|}

export type Linkable<T> = {|
	...T,
	_links: {
		self: Link,
	}
|}
