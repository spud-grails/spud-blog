package spud.blog

import grails.converters.*

class NewsController {
	def grailsApplication
	def spudMultiSiteService
	static responseFormats = ['html','rss','atom']
    def index() {
    	def postsPerPage = grailsApplication.config.spud.blog.postsPerPage ?: 25
    	def layout = grailsApplication.config.spud.blog.newsLayout ?: 'main'
		def siteId = params.int('siteId') ?: request.getAttribute('spudSiteId')
		def today  = new Date()
		def postQuery
		def postCount
		if( siteId == 0) {
			postCount = SpudPost.executeQuery("select count(p) from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId))",[siteId: siteId, today: today])[0]
			postQuery = "from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId)) ORDER BY publishedAt desc"
		} else {
			postCount = SpudPost.executeQuery("select count(p) from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId)",[ siteId: siteId, today: today])[0]
			postQuery = "from SpudPost p WHERE p.isNews = true AND visible=true AND publishedAt <= :today AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId) ORDER BY publishedAt desc"

		}


		withFormat {
			html {
				def posts = SpudPost.findAll(postQuery,[siteId:siteId, today:today], [max:postsPerPage] + params)
				render view: '/news/index', model: [posts: posts, postCount: postCount, layout: layout ]
			}
			rss {
				render(feedType:"rss", feedVersion:"2.0") {
					title = grailsApplication.config.spud.siteName ?: grailsApplication.config.spud.blog.newsName ?: 'Spud News'
					link = g.link(controller:'blog',action:'index',absolute:true)

					description = grailsApplication.config.spud.blog.newsDescription ?: 'Spud News Description'
					def posts = SpudPost.findAll(postQuery,[siteId:siteId, today:today])
					posts.each { post ->
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
					def posts = SpudPost.findAll(postQuery,[siteId:siteId, today:today])
					posts.each { post ->
						entry(post.title) {
							link = g.link(controller:'blog',action:'show', id: post.urlName,absolute:true)
							post.contentProcessed
						}
					}
				}
			}
			json {
				def posts = SpudPost.findAll(postQuery,[siteId:siteId, today:today], [max:postsPerPage] + params)
				render ([posts: posts, postCount: postCount] as JSON)
			}
			xml {
				def posts = SpudPost.findAll(postQuery,[siteId:siteId, today:today], [max:postsPerPage] + params)
				render([posts: posts, postCount: postCount] as XML)
			}
		}
    }


    def show() {
    	def post = SpudPost.where{ isNews == true && visible == true && publishedAt <= new Date() && urlName == params.id}.find()
    	def layout = grailsApplication.config.spud.blog.newsLayout ?: 'main'
    	if(!post) {
			response.sendError(404)
    		return
    	}

		withFormat {
			html {
				render view: '/news/show', model: [post: post, layout: layout]
			}
			json {
				render post as JSON
			}
			xml {
				render post as XML
			}

		}


    }

}
