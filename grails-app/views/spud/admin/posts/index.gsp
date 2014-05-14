<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <spAdmin:link action="create" title="New Post" class="btn btn-primary">New Post</spAdmin:link>
</content>
<content tag="detail">
    <table class="admin-table data-table" id="usertable">
    <thead>
      <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Published&nbsp;At</th>

        <th></th>
      </tr>
    </thead>
    <tbody>
      <g:each var="post" in="${posts}">

        <tr >
          <td><spAdmin:link action="edit" id="${post.id}" title="Edit Post">${post.title}</spAdmin:link></td>
          <td style="width:125px;">${post.userDisplayName}</td>
          <td style="width:175px;">${post.publishedAt?.format('MM/dd/YYYY hh:mm a')}</td>



          <td align="right" style="width:75px;">
            <spAdmin:link action="delete" id="${post.id}" data-confirm="Are you sure you want to remove this post?" data-method="DELETE" method="DELETE" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></spAdmin:link>
          </td>
        </tr>
      </g:each>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6">
          <g:paginate action="index" namespace="spud_admin" total="${postCount}" max="25" />
        </td>
      </tr>
    </tfoot>
  </table>
</content>

</g:applyLayout>
