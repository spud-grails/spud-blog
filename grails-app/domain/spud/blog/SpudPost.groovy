package spud.blog

import org.grails.databinding.BindingFormat

class SpudPost {
	def sharedSecurityService
	def spudTemplateService
	def grailsApplication

	static transients = ['user', 'userDisplayName', 'cachedContent', 'render', 'postContent']
	static hasMany = [sites: SpudPostSite]

	String title
	String content
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

	Map customFields

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
		sites cascade: "all-delete-orphan" 
    }

    static constraints = {
    	urlName nullable: false, unique: true
    	content nullable: true
    	contentProcessed nullable: true
    	metaKeywords nullable: true
    	metaDescription nullable: true
    	customFields nullable:true
    }

    String getUserDisplayName() {
    	def name
    	sharedSecurityService.withUser(userId) { user, account ->
    		name = user.toString()
    	}
    	return name
    }

    def getUser() {
    	def output
    	sharedSecurityService.withUser(userId) { user, account ->
    		output = user
    	}
    	return output
    }

	public void setPostContent(String _content) {
		content = _content
		contentProcessed = null
	}

	public String getPostContent() {
		return content
	}

	def beforeValidate() {
		if(this.content && !this.contentProcessed) {
			def formatter = grailsApplication.config.spud.formatters.find{ it.name == this.format}?.formatterClass
			if(formatter) {
				def formattedText = formatter.newInstance().compile(this.content)
				contentProcessed = formattedText
			} else {
				contentProcessed = this.content
			}
		}
	}

    public String render() {
		if(cachedContent) {
			return cachedContent
		}
		cachedContent = spudTemplateService.render("${this.urlName}",contentProcessed ?: content,[model: [post:this]])
	}

	public String getRender() {
		this.render()
	}

    static publicNewsPosts = { where { isNews == true && visible == true && publishedAt <= new Date() } }
    static publicBlogPosts = { where { isNews == false && visible == true && publishedAt <= new Date() } }

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

	def grailsCacheAdminService
	
	def afterInsert() {
		grailsCacheAdminService.clearAllCaches()
	}

	def afterUpdate() {
		grailsCacheAdminService.clearAllCaches()
	}

	def afterDelete() {
		grailsCacheAdminService.clearAllCaches()
	}
}
