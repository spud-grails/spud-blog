<fieldset>
	<div class="control-group">
		<label for="post.title" style="display:none;">Name</label>
		<g:textField name="post.title" class="full-width" placeholder="Enter title here" value="${post?.title}"/>
	</div>
</fieldset>
<div class="control-group">
	<div class="controls">
		%{-- Put Format Selector Here --}%
	</div>
</div>
<div style="clear:both;">
	<g:textArea name="post.content" class="spud-formatted-editor full-width" data-format="${post?.format}" value="${post?.content}" style="width:100%"/>
</div>

<fieldset class="spud_post_form_fieldset">
	<legend>Advanced</legend>

		<div class="spud_post_form_col">
			<h4>Meta Data</h4>

			<div class="spud_post_form_row">
				<label for="post.publishedAt">Publish Date</label>
				<g:textField name="post.publishedAt" value="${post?.publishedAt?.format('YYYY-MM-dd HH:mm')}" class="spud_form_date_picker"/>
			</div>
			<g:hiddenField name="post.userId" value="${post?.userId}"/>

			<div class="spud_post_form_row">
				<label for="post.visible">Visible</label>
				<div class="spud_post_form_input_group">
					<g:radio name="post.visible" value="${true}" checked="${post?.visible}"/>
					<label class="radio inline" for="post.visible">Yes</label>
					<g:radio name="post.visible" value="${false}" checked="${!post?.visible}"/>
					<label class="radio inline" for="post.visible">No</label>
				</div>
			</div>
			<g:if test="${!post?.isNews}">
				<div class="spud_post_form_row">
					<label for="post.commentsEnabled">Comments Enabled</label>
					
					<div class="spud_post_form_input_group">
						<g:radio name="post.commentsEnabled" value="true" checked="${post?.commentsEnabled}"/>
						<label class="radio inline" for="spud_post_comments_enabled_true">Yes</label>
						<g:radio name="post.commentsEnabled" value="false" checked="${!post?.commentsEnabled}"/>
						<label class="radio inline" for="spud_post_comments_enabled_false">No</label>
					</div>
				</div>
			</g:if>

				

			<div class="spud_post_form_row">
				<label for="post.metaKeywords">Keywords</label>
				<g:textField name="post.metaKeywords" value="${post?.metaKeywords}"/>
				
				<span class="spud_post_form_help_block">A Comma seperated list of keywords for search engines. Keep it short (no more than 10 keywords)</span>
			</div>

			<div class="spud_post_form_row">
				<label for="post.metaDescription">Description</label>
				<g:textField name="post.metaDescription" value="${post?.metaDescription}"/>
				<span class="spud_post_form_help_block">A short description of the article. This is what appears on a search engines search result page.</span>
			</div>
		</div>

		<div class="spud_post_form_col">
			<h4>Categories</h4>
			<a href='#' class='btn btn-mini spud_post_add_category'>Add Category</a>
			<input type="hidden" name="spud_post[category_ids][]" value="" />
			<ul class="spud_post_categories_form">
				
			</ul>
		</div>

</fieldset>

<div class="form-actions">
	<g:submitButton name="_submit" value="Save Post" class="btn btn-primary form-btn" data-loading-text="Saving..."/> or <spAdmin:link action="index" method="GET" class="btn">cancel</spAdmin:link>
</div>

<script type="text/javascript">
	$(document).ready(spud.admin.posts.edit);
</script>
