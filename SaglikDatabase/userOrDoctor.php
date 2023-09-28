<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc'])) {
    if ($db->dbConnect()) {
        $doctor = $db->userOrDoctor("users", $_POST['tc']);
        echo $doctor;
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
