package spud.blog

class SpudPostCategory {
	String name
	String urlName
	SpudPostCategory parent

    //TODO: ADD JOIN TABLE TO SpudPost
	
	static mapping = {
		def cfg = it?.getBean('grailsApplication')?.config
		datasource(cfg?.spud?.core?.datasource ?: 'DEFAULT')
		table 'spud_post_categories'
	}

	static constraints = {
		name nullable:false, unique: 'parent'
		urlName nullable:false
		parent nullable:true
	}
}
