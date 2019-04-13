<?php

	define('db', "id8989971_digiclub");
	define('host', "localhost");
	define('user', "id8989971_azaz");
	define('password', "00000923");

	$connect=mysqli_connect(host,user,password,db);
	$response = array();
	if($connect){
		
		
			$selectcommand="SELECT * FROM memberinfo";
			$result = mysqli_query($connect,$selectcommand);
			$count = mysqli_num_rows($result);

			
			
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
			
			    $rows[]= $row; 
			   
			}


			mysqli_close($connect);
			echo json_encode($rows);

		
	}
	else{

		$response['status'] ="Failed"; 
		echo "Connection failed";
	}
	
?>