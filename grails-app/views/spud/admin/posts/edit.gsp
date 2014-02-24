<g:applyLayout name="spud/admin/detail" >

<content tag="detail">
	<g:form name="edit_post" url="[action: 'update', id:post.id, method: 'PUT', namespace: 'spud_admin']" method="PUT" class="form-horizontal">
		<g:render template="/spud/admin/posts/form" model="[post: post]" />
	</g:form>

</content>

</g:applyLayout>
