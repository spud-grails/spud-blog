import grails.converters.JSON
import spud.blog.SpudPost
import spud.blog.SpudPostCategory

class SpudBlogGrailsPlugin {
    def loadAfter=['spud-core']
    // the plugin version
    def version = "0.7.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Spud Blog Plugin" // Headline display name of the plugin
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = 'Provides Blog / News functionality for Spud'

    def documentation = "https://github.com/spud-grails/spud-blog"
    def license = "APACHE"
    def organization = [name: "Bertram Labs", url: "http://www.bertramlabs.com/"]
    def issueManagement = [system: "GITHUB", url: "https://github.com/spud-grails/spud-blog/issues"]
}
