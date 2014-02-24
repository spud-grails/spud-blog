package spud.blog

import spud.core.*
import spud.permalinks.*
// import grails.plugin.cache.CacheEvict
// import grails.plugin.cache.Cacheable

class SpudBlogService {
	def spudPermalinkService

	def generateUrlName(post) {
		post.urlName = "${post?.publishedAt?.format("YYYY-MM-dd")}-${post?.title?.parameterize().toLowerCase()}"
	}
}


		// self.url_name = "#{self.published_at.strftime('%Y-%m-%d')}-#{self.title.parameterize}"
