<fieldset>
	<div class="form-group">
		<label for="post.title" style="display:none;">Name</label>
		<div class="col-sm-12">
			<g:textField name="post.title" class="full-width form-control" placeholder="Enter title here" value="${post?.title}" autofocus="true"/>
		</div>

	</div>
</fieldset>
<div class="control-group">
	<div class="controls">
		%{-- Put Format Selector Here --}%
	</div>
</div>
<div class="clearfix formtab">
	<div class="form-group">
		<div class="col-sm-2 col-sm-offset-10">
			<spAdmin:formatterSelect name='post.format' value="${post?.format}" class="pull-right input-sm form-control" data-formatter="spud-post-editor"/>
		</div>
	</div>
	<g:textArea name="post.postContent" id='spud-post-editor' class="spud-formatted-editor full-width" data-format="${post?.format}" value="${post?.postContent}" style="width:100%" data-content-css="${assetPath(src: 'spud/content.css')}"/>
</div>

<spAdmin:hasCustomFields type="${post.isNews ? 'newsPost' : 'blogPost'}">
  <fieldset class="custom-fields">
		<legend>Custom Fields</legend>
		<spAdmin:customFieldSet type="${post.isNews ? 'newsPost' : 'blogPost'}" objectType="post" objectField="customFields" object="${post}"/>
	</fieldset>
</spAdmin:hasCustomFields>

<fieldset class="spud_post_form_fieldset">
	<legend>Advanced</legend>
		<div class="col-md-6">
			<div class="form-horizontal">
					<h4>Meta Data</h4>

					<div class="form-group">
						<label for="post.publishedAt" class="control-label col-sm-4">Publish Date</label>
						<div class="col-sm-8">
							<g:textField name="post.publishedAt" value="${post?.publishedAt?.format('YYYY-MM-dd HH:mm')}" class="spud_form_date_picker form-control"/>
						</div>
					</div>
					%{-- <g:hiddenField name="post.userId" value="${post?.userId}"/> --}%

          <div class="form-group">
            <label for="post.urlName" class="control-label col-sm-2">Author</label>
            <div class="col-sm-8">
              <g:select class="form-control" name="post.userId" from="${users.asList()}" value="${post?.userId}" optionKey="id" />
            </div>
          </div>

						<div class="form-group">
							<label for="sites" class="control-label col-sm-2">Websites to Publish</label>

							<div class="spud_post_form_input_group col-sm-8">
								<spAdmin:availableSites>
									<g:checkBox name="sites" value="${site.siteId}" checked="${post?.id ? post.sites.collect{it.spudSiteId}.contains(site.siteId) : siteActive}"/>
									<label>${site.name}</label>
								</spAdmin:availableSites>
							</div>
						</div>


					<div class="form-group">
						<label for="post.visible" class="control-label col-sm-4">Visible</label>
						<div class="col-sm-8">
						<label class="radio-inline">
							<g:radio name="post.visible" value="${true}" checked="${post?.visible}"/> Yes
						</label>
						<label class="radio-inline">
							<g:radio name="post.visible" value="${false}" checked="${!post?.visible}"/> No
						</label>
						</div>
					</div>
					<g:if test="${!post?.isNews}">
						<div class="form-group">
							<label for="post.commentsEnabled" class="control-label col-sm-4">Comments Enabled</label>

							<div class="col-sm-8">
								<label class="radio-inline">

									<g:radio name="post.commentsEnabled" value="true" checked="${post?.commentsEnabled}"/> Yes
								</label>
								<label class="radio-inline">
									<g:radio name="post.commentsEnabled" value="false" checked="${!post?.commentsEnabled}"/> No
								</label>
							</div>
						</div>
					</g:if>

					<div class="form-group">
						<label for="post.metaKeywords" class="control-label col-sm-4">Keywords</label>
						<div class="col-sm-8">
							<g:textField name="post.metaKeywords" value="${post?.metaKeywords}" class="form-control"/>

							<span class="help-block">A Comma seperated list of keywords for search engines. Keep it short (no more than 10 keywords)</span>
						</div>
					</div>

					<div class="form-group">
						<label for="post.metaDescription" class="control-label col-sm-4">Description</label>
						<div class="col-sm-8">
							<g:textField name="post.metaDescription" value="${post?.metaDescription}" class="form-control"/>
							<span class="help-block">A short description of the article. This is what appears on a search engines search result page.</span>
						</div>
					</div>

					<div class="form-group">
						<label for="post.metaImage" class="control-label col-sm-4">Image</label>
						<div class="col-sm-8">
							<g:textField name="post.metaImage" value="${post?.metaImage}" class="form-control"/>
							<span class="help-block">Place an image url here. This field can be used for meta tag 'og:image'. To use, apply to the meta image content as ${'$'}{post.metaImage}.</span>
						</div>
					</div>
			</div>
		</div>
		<div class="col-md-6">

				<h4>Categories<a href='#' class='btn btn-xs btn-success pull-right'>Add Category</a></h4>

				<input type="hidden" name="spud_post[category_ids][]" value="" />
				<ul class="spud_post_categories_form">

				</ul>
		</div>


</fieldset>

<div class="col-sm-6 col-sm-offset-2">
	<g:submitButton name="_submit" value="Save Post" class="btn btn-primary form-btn" data-loading-text="Saving..."/> or <spAdmin:link action="index" method="GET" class="btn btn-default">cancel</spAdmin:link>
</div>

<script type="text/javascript">
	$(document).ready(spud.admin.posts.edit);
</script>
