<html>
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
        <meta name="layout" content="${layout ?: 'main'}">
    </head>
    <body data-page="blog/index">
        <content tag="title">News</content>
        <g:each var="post" in="${posts}">
            <g:render template="/blog/post" model="[post: post]" />
        </g:each>
    </body> 
</html>