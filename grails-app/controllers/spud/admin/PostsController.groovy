package spud.admin
import  spud.core.*
import  spud.security.*
import  spud.blog.*

class PostsController {

	def sitemapService
	def spudPageService
	def spudBlogService
	def spudMultiSiteService
	def sharedSecurityService


	def index() {

		def siteIds = spudMultiSiteService.availableSites().collect { it.siteId }
        def posts
        def postCount
		log.debug "Checking Active Site ${spudMultiSiteService.activeSite.siteId}"
        if(spudMultiSiteService.activeSite.siteId == 0) {
            postCount = SpudPost.executeQuery("select count(p) from SpudPost p WHERE p.isNews = :isNews AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId))",[isNews: news(), siteId: spudMultiSiteService.activeSite.siteId])[0]
            posts = SpudPost.findAll("from SpudPost p WHERE p.isNews = :isNews AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId)) ORDER BY publishedAt desc",[isNews: news(), siteId: spudMultiSiteService.activeSite.siteId],[max:25] + params)
        } else {
            postCount = SpudPost.executeQuery("select count(p) from SpudPost p WHERE p.isNews = :isNews AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId)",[isNews: news(), siteId: spudMultiSiteService.activeSite.siteId])[0]
            posts = SpudPost.findAll("from SpudPost p WHERE p.isNews = :isNews AND EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId) ORDER BY publishedAt desc",[isNews: news(), siteId: spudMultiSiteService.activeSite.siteId],[max:25] + params)

        }

		render view: '/spud/admin/posts/index', model: [posts: posts, postCount: postCount ]
	}

	def show() {

	}

	def create() {
		def post = new SpudPost(isNews: news(), publishedAt: new Date(), userId: sharedSecurityService.getUserIdentity())
		render view: '/spud/admin/posts/create', model: [post: post]
	}


	def save() {
		if(!params.post) {
			flash.error = "Post submission not specified"
			redirect action: 'index', method: 'GET', namespace: 'spud_admin'
			return
		}
		def post = new SpudPost(params.post)
		post.isNews = news()
		post.userId = sharedSecurityService.getUserIdentity()
		spudBlogService.generateUrlName(post)
		def sites = params.list('sites')

		if(!sites) {
			sites << spudMultiSiteService.activeSite.siteId
		}
		sites.each { site ->
			post.addToSites(new SpudPostSite(spudSiteId: site.toInteger()))
		}
		if(post.save(flush:true)) {
			sitemapService.evictCache()
			spudPageService?.evictCache()
			redirect action: 'index', method: 'GET', namespace: 'spud_admin'
		} else {
			println post.errors
			flash.error = "Error Saving Post"
			render view: '/spud/admin/posts/create', model: [post: post]
		}

	}


	def edit() {
		def post = loadPost()
		if(!post) {
			return
		}
		render view: '/spud/admin/posts/edit', model: [post: post]
	}


	def update() {
		def post = loadPost()
		if(!post) {
			return
		}
		bindData(post, params.post)
		post.isNews = news()
		def sites = params.list('sites')
		post.sites.clear()
		if(!sites) {
			sites << spudMultiSiteService.activeSite.siteId
		}
		sites.each { site ->
			post.addToSites(new SpudPostSite(spudSiteId: site.toInteger()))
		}
		if(post.save(flush:true)) {
			sitemapService.evictCache()
			spudPageService?.evictCache()
			redirect action: 'index', method: 'GET', namespace: 'spud_admin'
		} else {
			log.error post.errors
			flash.error = "Error Saving Post"
			render view: '/spud/admin/posts/edit', model: [post: post]
		}
	}


	def delete() {
		def post = loadPost()
		if(!post) {
			return
		}
		spudPageService?.evictCache()
		sitemapService.evictCache()
		post.delete()
		redirect action: 'index', method: 'GET', namespace: 'spud_admin'
	}

	protected loadPost() {
		if(!params.id) {
			flash.error = "Post not specified"
			redirect action: 'index', method: 'GET', namespace: 'spud_admin'
			return null
		}

        def post = SpudPost.find("from SpudPost p WHERE p.id = :id AND p.isNews = :isNews AND ( NOT EXISTS ( FROM SpudPostSite s WHERE s.post = p) OR EXISTS ( FROM SpudPostSite s Where s.post = p AND s.spudSiteId = :siteId))",[id: params.long('id'), isNews: news(), siteId: spudMultiSiteService.activeSite.siteId])
		if(!post) {
			flash.error = "Post not found!"
			redirect action: 'index', method: 'GET', namespace: 'spud_admin'
			return null
		}
		return post
	}

	protected news() {
		return false
	}
}
