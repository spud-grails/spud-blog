package spud.blog

class SpudBlogTagLib {
	static defaultEncodeAs = 'html'
	static namespace = 'sp'
	static encodeAsForTags = [blog: 'raw', news: 'raw']
	def spudMultiSiteService

	def news = { attrs, body->
		def siteId = request.getAttribute('spudSiteId')
		def postQuery = "from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId) ORDER BY publishedAt desc"
		def posts = SpudPost.findAll(postQuery,[today: new Date(),siteId: siteId],[max:(attrs.limit ?: 5)])
		def var = attrs.var ?: "post"

		posts.each { post ->
			def bodyContent = body((var):post)
			out << bodyContent
		}

	}



	def blog = { attrs, body ->
		def siteId = request.getAttribute('spudSiteId')
		def postQuery = "from SpudPost p WHERE p.isNews = false AND visible=true AND publishedAt <= :today AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId) ORDER BY publishedAt desc"
		def posts = SpudPost.findAll(postQuery,[today: new Date(),siteId: siteId],[max:(attrs.limit ?: 5)])
		def var = attrs.var ?: "post"
		posts.each { post ->
			out << body((var):post)
		}
	}

	def truncateHtml = { attrs ->

		def content = attrs.value ?: ''
		content = content.replaceAll("<(.|\n)*?>", '')

		def contentLength = attrs.length?.toInteger() ?: 500
		if(content.size() > contentLength) {
			return out << content.substring(0,contentLength)
		}

		return  out << content
	}
}
