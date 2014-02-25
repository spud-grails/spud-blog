<div class="spud_blog_post">
	<h3><g:link controller="blog" action="show" id="${post.urlName}">${post.title}</g:link></h3>
	<h4>Posted by ${post.userDisplayName} on ${post.publishedAt?.format('yyyy-MM-dd hh:mm a')}</h4>

	<div class="spud_blog_post_content">
		<sp:truncateHtml value="${post.render()}" length="250"/>
	</div>
</div>
