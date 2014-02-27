<div class="spud_blog_post">
  <g:if test="${post.isNews}">
    <h3><g:link resource="news" action="show" id="${post.urlName}">${post.title}</g:link></h3>
  </g:if>
  <g:else>
    <h3><g:link resource="blog" action="show" id="${post.urlName}">${post.title}</g:link></h3>
  </g:else>
	<h4>Posted by ${post.userDisplayName} on ${post.publishedAt?.format('yyyy-MM-dd hh:mm a')}</h4>

	<div class="spud_blog_post_content">
		<sp:truncateHtml value="${post.render()}" length="250"/>
	</div>
</div>
