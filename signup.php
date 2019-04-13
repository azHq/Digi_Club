<?php

	define('db', "id8989971_digiclub");
	define('host', "localhost");
	define('user', "id8989971_azaz");
	define('password', "00000923");

	$connect=mysqli_connect(host,user,password,db);
	$response = array();
	if($connect){
		

		$id=uniqid('', true);
		$name=$_POST["name"];
		$email=$_POST["email"];
		$password=$_POST["password"];

		$selectcommand="SELECT * FROM userinfo where email='$email'";

		$result = mysqli_query($connect,$selectcommand);
		$count = mysqli_num_rows($result);
		
		if($count>0){

			$response["status"] ="This email already exist"; 
		}
		else{

			$insertcommand="INSERT INTO userinfo (id,name,email,password) VALUES ('$id','$name','$email','$password')";
			if(mysqli_query($connect,$insertcommand)){

		        $response["status"] ="success".",".$id; 
		       
			}
			else{

				echo "Failed upload data";
				$response["status"] ="Failed"; 
				
			}

			

		}
		
	}
	else{

		$response["status"] ="Failed"; 
		echo "Connection failed";
	}

	echo json_encode($response);
?>