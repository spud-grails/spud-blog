import spud.blog.*
import grails.converters.*

class SpudBlogBootStrap {
	def init = { servletContext ->
		JSON.registerObjectMarshaller(SpudPost) {
			def output = [:]
			output.id              = it.id
			output.title           = it.title
			output.urlName         = it.urlName
			output.content         = it.render()
			output.publishedAt     = it.publishedAt
			output.dateCreated     = it.dateCreated
			output.lastUpdated     = it.lastUpdated
			output.userId          = it.userId
			output.userDisplayName = it.userDisplayName

			return output;
		}
	}

	def destroy = {

	}
}
