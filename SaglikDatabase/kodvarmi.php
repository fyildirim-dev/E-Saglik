<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc']) && isset($_POST['kod'])) {
    if ($db->dbConnect()) {
        if ($db->kodvarmi("forgotpass", $_POST['tc'], $_POST['kod'])) {
            echo "Code Var";
        } else echo "Code Yok";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
