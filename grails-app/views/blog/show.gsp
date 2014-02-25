<html>
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
        <meta name="layout" content="main">
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