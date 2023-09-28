<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['mesaj']) && isset($_POST['sendID']) && isset($_POST['receiverID'])) {
    if ($db->dbConnect()) {
        if ($db->message("messages", $_POST['mesaj'], $_POST['sendID'], $_POST['receiverID'])) {
            echo "Message Success";
        } else echo "Message Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
