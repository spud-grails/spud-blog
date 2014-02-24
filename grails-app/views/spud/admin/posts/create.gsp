<g:applyLayout name="spud/admin/detail" >

<content tag="detail">
	<g:form name="new_post" url="[action: 'save', method: 'POST', namespace: 'spud_admin']" method="POST" class="form-horizontal">
		<g:render template="/spud/admin/posts/form" model="[post: post]" />
	</g:form>

</content>

</g:applyLayout>
