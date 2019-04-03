<?php

	define('db', "id8989971_digiclub");
	define('host', "localhost");
	define('user', "id8989971_azaz");
	define('password', "00000923");

	$connect=mysqli_connect(host,user,password,db);
	$response = array();
	if($connect){
		
		echo "connect successfully";

		$type=$_POST["type"];
		if($type=="add"){

				$name=$_POST["name"];
				$num=$_POST["price"];
				$price=echo (double)$num;
				$path="naa";
				$insertcommand="INSERT INTO FoodMenu (name,price,imagepath) VALUES ('$name','$price','$path')";
				if(mysqli_query($connect,$insertcommand)){

			        $response["status"] ="success"; 
			       
				}
				else{

					echo "Failed upload data";
					$response["status"] ="Failed"; 
					
				}

		}
		else if($type=="delete"){

				$id=$_POST["id"];
				
				$deletecommand="DELETE FROM FoodMenu WHERE id='$id'";
				if(mysqli_query($connect,$deletecommand)){

			        $response["status"] ="success"; 
			       
				}
				else{

					echo "Failed upload data";
					$response["status"] ="Failed"; 
					
				}

		}
		
		

			

		
	}
	else{

		$response['status'] ="Failed"; 
		echo "Connection failed";
	}

	echo json_encode($response);
	
?>