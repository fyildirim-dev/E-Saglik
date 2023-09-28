<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['tc']) && isset($_POST['ad']) && isset($_POST['soyad']) && isset($_POST['email']) && isset($_POST['sifre'])) {
    if ($db->dbConnect()) {
        if ($db->profilUpdate("users", $_POST['tc'], $_POST['ad'], $_POST['soyad'], $_POST['email'], $_POST['sifre'])) {
            echo "Profil Update Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
