package spud.admin
import  spud.core.*
import  spud.security.*
import  spud.blog.*

class PostsController {

    def sitemapService
    def spudPageService
    def spudBlogService
    def sharedSecurityService


    def index() {
    	def posts = SpudPost.where{ isNews == news() }
    	def postCount = posts.count()
    	posts = posts.list([sort: 'publishedAt', order: 'desc', max: 25] + params)
    	render view: '/spud/admin/posts/index', model: [posts: posts, postCount: postCount ]
    }

    def show() {

    }

    def create() {
        def post = new SpudPost(isNews: news(), publishedAt: new Date())
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

        def post = SpudPost.read(params.id)
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
