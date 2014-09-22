package spud.blog

import grails.converters.*

class NewsController {
	def grailsApplication
	static responseFormats = ['html','rss','atom']
    def index() {
    	def postsPerPage = grailsApplication.config.spud.blog.postsPerPage ?: 25
    	def layout = grailsApplication.config.spud.blog.newsLayout ?: 'main'
    	def posts = SpudPost.publicNewsPosts()
    	def postCount = posts.count()

		withFormat {
			html {
				posts = posts.list([sort: 'publishedAt', order: 'desc', max: postsPerPage] + params)

				render view: '/news/index', model: [posts: posts, postCount: postCount, layout: layout ]
			}
			rss {
				render(feedType:"rss", feedVersion:"2.0") {
					title = grailsApplication.config.spud.siteName ?: grailsApplication.config.spud.blog.newsName ?: 'Spud News'
					link = g.link(controller:'blog',action:'index',absolute:true)

					description = grailsApplication.config.spud.blog.newsDescription ?: 'Spud News Description'

					posts.list([sort: 'publishedAt', order: 'desc']).each { post ->
						entry(post.title) {
							link = g.link(controller:'blog',action:'show', id: post.urlName,absolute:true)
							post.contentProcessed
						}
					}
				}

			}
			atom {
				render(feedType:"atom") {
					title = grailsApplication.config.spud.siteName ?: grailsApplication.config.spud.blog.newsName ?: 'Spud News'
					link = g.link(controller:'blog',action:'index',absolute:true)
					description = grailsApplication.config.spud.blog.newsDescription ?: 'Spud News Description'

					posts.list([sort: 'publishedAt', order: 'desc']).each { post ->
						entry(post.title) {
							link = g.link(controller:'blog',action:'show', id: post.urlName,absolute:true)
							post.contentProcessed
						}
					}
				}
			}
			json {
				posts = posts.list([sort: 'publishedAt', order: 'desc', max: postsPerPage] + params)
				render [posts: posts, postCount: postCount] as JSON
			}
			xml {
				posts = posts.list([sort: 'publishedAt', order: 'desc', max: postsPerPage] + params)
				render [posts: posts, postCount: postCount] as XML
			}
		}
    }


    def show() {
    	def post = SpudPost.where{ isNews == true && visible == true && publishedAt <= new Date() && urlName == params.id}.find()
    	def layout = grailsApplication.config.spud.blog.newsLayout ?: 'main'
    	if(!post) {
    		redirect action: 'index'
    		return
    	}

		withFormat {
			json {
				render post as JSON
			}
			xml {
				render post as XML
			}
			html {
				render view: '/news/show', model: [post: post, layout: layout]
			}
		}


    }

}
