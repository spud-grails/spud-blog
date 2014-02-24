package spud.admin
import  spud.core.*
import  spud.security.*
import  spud.blog.*


@SpudApp(name="Blog Posts", thumbnail="spud/admin/posts_thumb.png", order="10")
@SpudSecure(['BLOG'])
class BlogPostsController extends PostsController {
	static namespace = 'spud_admin'

	protected news() {
        return false
    }
}
