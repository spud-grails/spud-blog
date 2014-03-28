package spud.admin
import  spud.core.*
import  spud.security.*
import  spud.blog.*


@SpudApp(name="Blog Posts", thumbnail="spud/admin/blog_icon.png", order="10", enabled="spud.blog.blogEnabled")
@SpudSecure(['BLOG'])
class BlogPostsController extends PostsController {
	static namespace = 'spud_admin'

	protected news() {
        return false
    }
}
