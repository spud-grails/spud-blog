package spud.blog

class NewsController {
	def grailsApplication

    def index() { 
    	def postsPerPage = grailsApplication.config.spud.blog.postsPerPage ?: 25
    	def layout = grailsApplication.config.spud.blog.newsLayout ?: 'main'
    	def posts = SpudPost.publicNewsPosts
    	def postCount = posts.count()
    	posts = posts.list([sort: 'publishedAt', direction: 'desc', max: postsPerPage] + params)
    	println posts
    	render view: '/news/index', model: [posts: posts, postCount: postCount, layout: layout]
    }


    def show() {
    	def post = SpudPost.publicNewsPosts.where { urlName == params.id }.find()
    	def layout = grailsApplication.config.spud.blog.newsLayout ?: 'main'
    	if(!post) {
    		redirect action: 'index'
    		return
    	}

    	render view: '/news/show', model: [post: post, layout: layout]

    }

}