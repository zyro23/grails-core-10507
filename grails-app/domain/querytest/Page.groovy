package querytest


class Page {

	static hasMany = [words: Word]
	static belongsTo = [book: Book]

	String name

}
