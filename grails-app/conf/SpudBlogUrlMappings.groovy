		
class SpudBlogUrlMappings {

	static mappings = {
        "/spud/admin/blog-posts"(resources: 'blogPosts', namespace: 'spud_admin')
        "/spud/admin/news-posts"(resources: 'newsPosts', namespace: 'spud_admin')
	}
}
