<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc'])) {
    if ($db->dbConnect()) {
        $name = $db->name("users", $_POST['tc']);
        echo $name;
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
