package spud.blog

class SpudBlogAdminTagLib {
	def spudMultiSiteService
	static defaultEncodeAs = 'raw'
	static namespace = 'spAdmin'

	//TODO: Does this merit going into core
	def availableSites = { attrs, body ->
        def sites = spudMultiSiteService.availableSites()
		def activeSiteId = spudMultiSiteService.activeSite.siteId

		sites.each { site ->
			out << body(site: site, siteActive: site.siteId == activeSiteId)
		}
	}
}
