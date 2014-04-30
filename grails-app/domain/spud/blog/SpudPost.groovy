package spud.blog

import org.grails.databinding.BindingFormat

class SpudPost {
	def sharedSecurityService
	def spudTemplateService

	static transients = ['userDisplayName', 'cachedContent']

	String title
	String content //Set constraint to make it big
	String contentProcessed
	String format='html'
	Boolean commentsEnabled = false
	Boolean visible = true
	Boolean isNews = false

	String urlName
	String metaKeywords
	String metaDescription
	Integer commentsCount = 0
	Long userId


	@BindingFormat('yyyy-MM-dd HH:mm')
	Date publishedAt
	Date dateCreated
	Date lastUpdated

	// Transients
	String cachedContent


    static mapping = {
		def cfg = it?.getBean('grailsApplication')?.config
        datasource(cfg?.spud?.core?.datasource ?: 'DEFAULT')
		cache true
		table 'spud_posts'
		autoTimestamp true
		content type: 'text'
		contentProcessed type: 'text'
		format column: 'content_format'
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'
		userId column: 'spud_user_id'
    }

    static constraints = {
    	urlName nullable: false, unique: true
    	content nullable: true
    	contentProcessed nullable: true
    	metaKeywords nullable: true
    	metaDescription nullable: true
    }

    String getUserDisplayName() {
    	def name
    	sharedSecurityService.withUser(userId) { user, account ->
    		name = user.toString()
    	}
    	return name
    }

    public String render() {
		if(cachedContent) {
			return cachedContent
		}
		cachedContent = spudTemplateService.render("${this.urlName}",content,[model: [post:this]])
	}

    static publicNewsPosts = where { isNews == true && visible == true && publishedAt <= new Date() }
    static publicBlogPosts = where { isNews == false && visible == true && publishedAt <= new Date() }

    static namedQueries = {
    	forSpudSite { currentSiteId ->
    		eq('siteId', currentSiteId)
    	}

    	publicPosts {
    		eq('visible', true)
    		lte('publishedAt', new Date())
    		order('publishedAt','desc')
    	}

    	recentPosts { limit ->
    		publicPosts()
    		maxResults(limit)
    	}

    	recentNewsPosts { limit ->
    		eq('isNews', false)
    		recentPosts(limit)

    	}

    	recentBlogPosts { limit ->
    		eq('isNews', true)
    		recentPosts(limit)
    	}


    }
}
