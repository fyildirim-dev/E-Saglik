<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc']) && isset($_POST['kod'])) {
    if ($db->dbConnect()) {
        if ($db->kodsend("forgotpass", $_POST['tc'], $_POST['kod'])) {
            echo "Code Send Success";
        } else echo "Code Send Wrong";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
