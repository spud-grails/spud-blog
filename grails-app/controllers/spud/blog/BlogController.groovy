package spud.blog

class BlogController {
	def grailsApplication
	static responseFormats = ['html','rss','atom']

    def index() {
    	def postsPerPage = grailsApplication.config.spud.blog.postsPerPage ?: 25
    	def layout = grailsApplication.config.spud.blog.blogLayout ?: 'main'
    	def posts = SpudPost.publicBlogPosts()
    	def postCount = posts.count()
		withFormat {
			html {
				posts = posts.list([sort: 'publishedAt', order: 'desc', max: postsPerPage] + params)

				render view: '/blog/index', model: [posts: posts, postCount: postCount, layout: layout ]
			}
			rss {
				render(feedType:"rss", feedVersion:"2.0") {
					title = grailsApplication.config.spud.siteName ?: grailsApplication.config.spud.blog.blogName ?: 'Spud Blog'

					link = g.link(controller:'blog',action:'index',absolute:true)

					description = grailsApplication.config.spud.blog.blogDescription ?: 'Spud blog Description'

					posts.list([sort: 'publishedAt', order: 'desc']).each { post ->
						entry(post.title) {
							link = g.link(controller:'blog',action:'show', id: post.urlName,absolute:true)
							post.contentProcessed
						}
					}
				}

			} atom {
				render(feedType:"atom") {
					title = grailsApplication.config.spud.siteName ?: grailsApplication.config.spud.blog.blogName ?: 'Spud Blog'
					link = g.link(controller:'blog',action:'index',absolute:true)
					description = grailsApplication.config.spud.blog.blogDescription ?: 'Spud blog Description'

					posts.list([sort: 'publishedAt', order: 'desc']).each { post ->
						entry(post.title) {
							link = g.link(controller:'blog',action:'show', id: post.urlName,absolute:true)
							post.contentProcessed
						}
					}
				}
			}
		}
    }


    def show() {
    	def post = SpudPost.where { isNews == false && visible == true && publishedAt <= new Date() && urlName == params.id}.find()
    	def layout = grailsApplication.config.spud.blog.blogLayout ?: 'main'
    	if(!post) {
    		redirect action: 'index'
    		return
    	}

    	render view: '/blog/show', model: [post: post, layout: layout]

    }

}
