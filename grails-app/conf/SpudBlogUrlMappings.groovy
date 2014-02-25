		
class SpudBlogUrlMappings {

	static mappings = { appContext ->

		def spudBlogService = appContext.spudBlogService
		def blogMapping = spudBlogService.blogMapping
		def newsMapping = spudBlogService.newsMapping
		def newsEnabled = spudBlogService.isNewsEnabled()
		def blogEnabled = spudBlogService.isBlogEnabled()

		if(blogEnabled) {
			"/$blogMapping"(resources: 'blog')
	        "/spud/admin/blog-posts"(resources: 'blogPosts', namespace: 'spud_admin')
		}

		if(newsEnabled) {
			"/$newsMapping"(resources: 'news')
			"/spud/admin/news-posts"(resources: 'newsPosts', namespace: 'spud_admin')	
		}
		
        
	}
}
