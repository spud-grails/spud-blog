package spud.blog

import spud.core.*
import spud.permalinks.*
// import grails.plugin.cache.CacheEvict
// import grails.plugin.cache.Cacheable

class SpudBlogService {
	static transactional = false
	def spudPermalinkService
	def grailsApplication

	def generateUrlName(post) {
		post.urlName = "${post?.publishedAt?.format("YYYY-MM-dd")}-${post?.title?.parameterize().toLowerCase()}"
	}


	def isNewsEnabled() {
		def config = grailsApplication.config.spud.blog
		return grailsApplication.config.spud.blog.containsKey('newsEnabled') ? grailsApplication.config.spud.blog.newsEnabled : false
	}

	def isBlogEnabled() {
		def config = grailsApplication.config.spud.blog
		return grailsApplication.config.spud.blog.containsKey('blogEnabled') ? grailsApplication.config.spud.blog.blogEnabled : true
	}


	def getNewsMapping() {
		def config = grailsApplication.config.spud.blog
		return grailsApplication.config.spud.blog.containsKey('newsMapping') ? grailsApplication.config.spud.blog.newsMapping : 'news'
	}

	def getBlogMapping() {
		def config = grailsApplication.config.spud.blog
		return grailsApplication.config.spud.blog.containsKey('blogMapping') ? grailsApplication.config.spud.blog.blogMapping : 'blog'
	}
}
