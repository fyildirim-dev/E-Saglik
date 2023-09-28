<?php
    $con=mysqli_connect("localhost", "root", "", "saglik") or die("I cannot connect to the database.".mysql_errno());

    mysqli_select_db($con,"saglik");
    mysqli_query($con,"SET NAMES 'utf8'");

?>