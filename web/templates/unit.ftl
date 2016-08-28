<#import "default.ftl" as default>
<@default.mainLayout "Register">
<h1>Register Unit</h1>
<#if error??>
        <div class="col-md-12 alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only"> Error: </span>
                ${error}
        </div>
</#if>
<div class="row">
	<form role="form" action="register_unit" method="post">
		<div classs="form-group">
			<label for="name">Code</label>
			<input type="text" class="form-control" placeholder="Code" name="code">
		</div>
                <div classs="form-group">
                        <label for="name">Title</label>
                        <input type="text" class="form-control" placeholder="" name="title">
                </div>
                <div classs="form-group">
                        <label for="name">Description</label>
                        <input type="text" class="form-control" placeholder="Description" name="description">
                </div>
                <div class="form-group">
		<button type="submit" class="btn btn-default">Submit</button>
                </div>

	</form>
</div>
</@default.mainLayout>
