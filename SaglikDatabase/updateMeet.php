<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['patient']) && isset($_POST['doctor']) && isset($_POST['mesaj']) && isset($_POST['point'])) {
    if ($db->dbConnect()) {
        if ($db->meetingUpdate("meetings", $_POST['patient'], $_POST['doctor'], $_POST['mesaj'], $_POST['point'])) {
            echo "Meeting Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
