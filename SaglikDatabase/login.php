<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc']) && isset($_POST['sifre'])) {
    if ($db->dbConnect()) {
        if ($db->logIn("users", $_POST['tc'], $_POST['sifre'])) {
            echo "Login Success";
        } else echo "Username or Password wrong";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
