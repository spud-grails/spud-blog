package spud.blog

class SpudBlogTagLib {
    static defaultEncodeAs = 'html'
    static namespace = 'sp'
    static encodeAsForTags = [blog: 'raw', news: 'raw']

    def news = { attrs, body->
    	def limit = attrs.limit?.toInteger() ?: 5
    	def posts = SpudPost.publicNewsPosts.list(max: limit, sort: 'publishedAt', direction: 'desc')
    	def var = attrs.var ?: "post"

    	posts.each { post ->
    		out << body((var):post)
    	}

    }

    def blog = { attrs, body ->
		def posts = SpudPost.publicBlogPosts.list(max: limit, sort: 'publishedAt', direction: 'desc')
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
