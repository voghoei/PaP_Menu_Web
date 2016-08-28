<#import "default.ftl" as default>
<@default.mainLayout "Register">
<h1>Register Beer</h1>
<#if error??>
        <div class="col-md-12 alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only"> Error: </span>
                ${error}
        </div>
</#if>
<div class="row">
	<form role="form" action="register_beer" method="post">
		<div classs="form-group">
			<label for="name">Code</label>
			<input type="text" class="form-control" placeholder="Code" name="code">
		</div>
                <div classs="form-group">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" placeholder="Name" name="name">
                </div>
                <div classs="form-group">
                        <label for="name">Brand</label>
                        <input type="text" class="form-control" placeholder="Brand" name="brand">
                </div>
                <div classs="form-group">
                        <label for="email">Style</label>
                        <input type="email" class="form-control" placeholder="Style" name="style">
                </div>
                <div classs="form-group">
                        <label for="email">ABV</label>
                        <input type="email" class="form-control" placeholder="ABV" name="abv">
                </div>
		<div classs="form-group">
                        <label for="email">IBU</label>
                        <input type="email" class="form-control" placeholder="IBU" name="ibu">
                </div>
                <div classs="form-group">
                        <label for="phone">Description</label>
                        <input type="tel" class="form-control" placeholder="Description" name="description">
                </div>
                <div class="form-group">
		<button type="submit" class="btn btn-default">Submit</button>
                </div>

	</form>
</div>
</@default.mainLayout>
