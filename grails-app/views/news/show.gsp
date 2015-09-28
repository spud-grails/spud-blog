<html>
    <head>
	    <title>${post.title}</title>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
        <meta name="layout" content="${layout ?: 'main'}">

        <meta property="og:title" content="${post.title}" />

		<g:if test="${post.metaDescription}">
			<meta name="description" content="${post.metaDescription}">
			<meta property="og:description" content="${post.metaDescription}" />

		</g:if>
		<g:if test="${post.metaKeywords}">
			<meta name="keywords" content="${post.metaKeywords}">
		</g:if>
    </head>
    <body data-page="blog/index">
    	<div class="spud_blog_post">
			<h3>${post.title}</h3>
			<h4>Posted by ${post.userDisplayName} on ${post.publishedAt?.format('yyyy-MM-dd hh:mm a')}</h4>
			
			<div id="spud_blog_post_content">
				${raw(post.render())}
			</div>
		</div>
    </body> 
</html>