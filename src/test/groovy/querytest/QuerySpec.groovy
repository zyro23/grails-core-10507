package querytest

import grails.test.mixin.Mock
import spock.lang.Specification

@Mock([Book, Page, Word])
class QuerySpec extends Specification {

	def "test where query with multi-level association restriction"() {
		def b1 = new Book(name: "b1")
		def b1p1 = new Page(name: "b1p1")
		def b1p1w1 = new Word(name: "b1p1w1")
		def b1p1w2 = new Word(name: "b1p1w2")
		def b1p2 = new Page(name: "b1p2")
		def b1p2w1 = new Word(name: "b1p2w1")
		def b1p2w2 = new Word(name: "b1p2w2")
		def b2 = new Book(name: "b2")
		def b2p1 = new Page(name: "b2p1")
		def b2p1w1 = new Word(name: "b2p1w1")
		def b2p1w2 = new Word(name: "b2p1w2")
		def b2p2 = new Page(name: "b2p2")
		def b2p2w1 = new Word(name: "b2p2w1")
		def b2p2w2 = new Word(name: "b2p2w2")

		b1.addToPages b1p1
		b1p1.addToWords b1p1w1
		b1p1.addToWords b1p1w2
		b1.addToPages b1p2
		b1p2.addToWords b1p2w1
		b1p2.addToWords b1p2w2
		b2.addToPages b2p1
		b2p1.addToWords b2p1w1
		b2p1.addToWords b2p1w2
		b2.addToPages b2p2
		b2p2.addToWords b2p2w1
		b2p2.addToWords b2p2w2

		b1.save flush: true, failOnError: true
		b2.save flush: true, failOnError: true

		when:
		def words = Word.findAll {
			page {
				book.id == 1
			}
		}

		then:
		words.size() == 4
		b1p1w1 in words
		b1p1w2 in words
		b1p2w1 in words
		b1p2w2 in words
	}

}
