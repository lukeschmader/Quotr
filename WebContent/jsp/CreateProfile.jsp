<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Stock App</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link href="../css/createProfileCss.css" rel="stylesheet">
  </head>

  <body>


<nav class="navbar navbar-inverse navbar-fixed-top">

        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand heading" href="#">Luke Schmader</a>
        </div>
        <div id="navbar" class="" >
        <div class="navbar-right">
          <form class="navbar-form ">
            <div class="form-group">
              <input type="text" placeholder="Username" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" placeholder="Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-warning">Sign in</button>
            <div class="create-account inline">
					             		 or <a href="#">create account</a>
            </div>
          </form>
         </div>
        </div><!--/.navbar-collapse -->

</nav>
	<div class="sub-heading">
		<div class="sub-heading-title">
			Stock Analysis
		</div>
		</div>

<div class="login-div">
	<h1 class="loginTitle">Create Profile</h1>
	<form class="centerForm" >
		<br><br>
		<div class="loginInputs">
		Username:<input type="text" name="createUsername" placeholder="username..." class="form-control" >
		</div><br>
		<div class="loginInputs">
		Password:<input type="password" name="createPassword" placeholder="password..." class="form-control" >
		</div> <br>
		<div class="loginInputs">
		Organization: <font color=grey><i>(optional)</i> </font><input type="text" name="createOrganization" placeholder="organization..." class="form-control" >
		</div> <br>
		<div class="loginButton">
		<button type="submit" class="btn btn-warning">Sign Up</button><br>
		</div><br><br><br><br>
		<div class="loginButton">
			<a href="#">sign in</a>
		</div>
	</form>
</div>


  </body>
</html>