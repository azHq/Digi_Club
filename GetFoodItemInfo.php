<?php

	define('db', "id8989971_digiclub");
	define('host', "localhost");
	define('user', "id8989971_azaz");
	define('password', "00000923");

	$connect=mysqli_connect(host,user,password,db);
	$response = array();
	if($connect){
		
		
			$selectcommand="SELECT name imagepath FROM FoodMenu";
			$result = mysqli_query($connect,$selectcommand);
			
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
			

			    $rows[]= $row; 
			   
			}


			mysqli_close($connect);
			header("Content-type: application/json; charset=utf-8");
			echo json_encode($rows);

		
	}
	else{

		$response['status'] ="Failed"; 
		echo "Connection failed";
	}
	
?>