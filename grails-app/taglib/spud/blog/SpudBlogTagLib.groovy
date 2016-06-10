package spud.blog

import java.text.BreakIterator

class SpudBlogTagLib {
	static defaultEncodeAs = 'html'
	static namespace = 'sp'
	static encodeAsForTags = [blog: 'raw', news: 'raw']
	def spudMultiSiteService

	def news = { attrs, body->
		def siteId = request.getAttribute('spudSiteId')
		def postQuery
		if(siteId == 0) {
			postQuery = "from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId)) ORDER BY publishedAt desc"
		} else {
			postQuery = "from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId) ORDER BY publishedAt desc"
		}
		def posts = SpudPost.findAll(postQuery,[today: new Date(),siteId: siteId],[max:(attrs.limit ?: 5), offset:(attrs.offset ?: 0)])
		def var = attrs.var ?: "post"

		posts.each { post ->
			def bodyContent = body((var):post)
			out << bodyContent
		}

	}


	def blog = { attrs, body ->
		def siteId = request.getAttribute('spudSiteId')
		def postQuery
		if(siteId == 0) {
			postQuery = "from SpudPost p WHERE p.isNews = false AND visible=true AND publishedAt <= :today AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId)) ORDER BY publishedAt desc"
		} else {
			postQuery = "from SpudPost p WHERE p.isNews = false AND visible=true AND publishedAt <= :today AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId) ORDER BY publishedAt desc"
		}

		def posts = SpudPost.findAll(postQuery,[today: new Date(),siteId: siteId],[max:(attrs.limit ?: 5), offset:(attrs.offset ?: 0)])
		def var = attrs.var ?: "post"
		posts.each { post ->
			out << body((var):post)
		}
	}

	def truncateHtml = { attrs, body ->

		def content = attrs.value ?: ''
		content = content.decodeHTML().replaceAll("<(.|\n)*?>", '')

		def contentLength = attrs.length?.toInteger() ?: 500
		if(content.size() > contentLength) {
			BreakIterator bi = BreakIterator.getWordInstance()
			bi.setText(content);
			def first_after = bi.following(contentLength)

			// to truncate:
			out << content.substring(0, first_after) + "..."
			// works like an "if" tag with test = is content too long
			def bodyContent = body(size: content.size())
			out << bodyContent
		} else {
			out << content

		}

	}
}
