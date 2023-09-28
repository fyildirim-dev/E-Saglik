<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['doctor']) && isset($_POST['policlinic']) && isset($_POST['doctorkod'])) {
    if ($db->dbConnect()) {
        if ($db->signUpPoliclinic("policlinics", $_POST['doctor'], $_POST['policlinic'], $_POST['doctorkod'])) {
            $db->setDoctor("users", $_POST['doctor']);
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
