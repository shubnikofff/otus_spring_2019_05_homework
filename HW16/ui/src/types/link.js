// @flow
export type Linkable<T> = {|
	...T,
	_links: {
		self: {
			href: string
		}
	}
|}
