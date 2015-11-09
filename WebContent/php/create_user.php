  <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Stock App</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link href="css/createProfileCss.css" rel="stylesheet">
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
<br>
<br>
<br>
<br>
<br>
<br>
<?php

if(isset($_POST['submit'])){
	echo "POST: <br>";
    $data_missing = array();
    
    if(empty($_POST['createUsername'])){

        // Adds name to array
        $data_missing[] = 'Username';

    } else {

        // Trim white space from the name and store the name
        $cUsername = trim($_POST['createUsername']);

    }

    if(empty($_POST['createPassword'])){

        // Adds name to array
        $data_missing[] = 'Password';

    } else{

        // Trim white space from the name and store the name
        $cPassowrd = trim($_POST['createPassword']);

    }

    if(empty($_POST['createOrganization'])){

        // Adds name to array
        $cOrganization = $_POST['createOrganization'];

    } else{

        // Trim white space from the name and store the name
        $cOrganization = trim($_POST['createOrganization']);

    }
   
    if(empty($data_missing)){
        
        require_once('php/mysqli_connect.php');
        
        $query = "INSERT INTO USER_PROF (USERNAME, PASSWORD, ORGANIZATION) VALUES (?, ?, ?)";
        
        $stmt = mysqli_prepare($dbc, $query);
        
        
        mysqli_stmt_bind_param($stmt, "sss", $cUsername, $cPassowrd, $cOrganization);
        
        mysqli_stmt_execute($stmt);
        
        $affected_rows = mysqli_stmt_affected_rows($stmt);
        
        if($affected_rows == 1){
            
            echo "Student Entered";
            
            mysqli_stmt_close($stmt);
            
            mysqli_close($dbc);
            
        } else {
            
        	echo "Error Occurred<br />";

        	
            echo mysqli_error();
            
            mysqli_stmt_close($stmt);
            
            mysqli_close($dbc);
            
        }
        
    } else {
        
        
        
        foreach($data_missing as $missing){
            
           
            
        }
        
    }
    
}

?>
</body>
</html>