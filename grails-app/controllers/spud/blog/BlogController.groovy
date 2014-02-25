package spud.blog

class BlogController {
	def grailsApplication

    def index() { 
    	def postsPerPage = grailsApplication.config.spud.blog.postsPerPage ?: 25
    	def layout = grailsApplication.config.spud.blog.blogLayout ?: 'main'
    	def posts = SpudPost.publicBlogPosts
    	def postCount = posts.count()
    	posts = posts.list([sort: 'publishedAt', direction: 'desc', max: postsPerPage] + params)
    	println posts
    	render view: '/news/index', model: [posts: posts, postCount: postCount, layout: layout ]
    }


    def show() {
    	def post = SpudPost.publicBlogPosts.where { urlName == params.id }.find()
    	def layout = grailsApplication.config.spud.blog.blogLayout ?: 'main'
    	if(!post) {
    		redirect action: 'index'
    		return
    	}

    	render view: '/news/show', model: [post: post, layout: layout]

    }

}
