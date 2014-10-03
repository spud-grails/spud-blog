package spud.blog

class SpudPostSite {
	static belongsTo = [post: SpudPost]
	Integer spudSiteId = 0

	static mapping = {
		table 'spud_post_sites'

		def cfg = it?.getBean('grailsApplication')?.config
		datasource(cfg?.spud?.core?.datasource ?: 'DEFAULT')
		cache true
	}
}
