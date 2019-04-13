<?php

	define('db', "id8989971_digiclub");
	define('host', "localhost");
	define('user', "id8989971_azaz");
	define('password', "00000923");

	$connect=mysqli_connect(host,user,password,db);
	$response = array();
	if($connect){
		


		$type=$_POST["type"];
		if($type=="add"){

				$id=uniqid('', true);
				$name=$_POST["name"];
				$dept=$_POST["dept"];
				$pin=mt_rand(1000, 9999);
				$insertcommand="INSERT INTO memberinfo (id,name,department,pin) VALUES ('$id','$name','$dept','$pin')";
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
				
				$deletecommand="DELETE FROM memberinfo WHERE id='$id'";
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