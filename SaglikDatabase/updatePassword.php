<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc']) && isset($_POST['sifre'])) {
    if ($db->dbConnect()) {
        if ($db->passwordUpdate("users", $_POST['tc'], $_POST['sifre'])) {
            echo "Password Update Success";
        } else echo "Password Update Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
