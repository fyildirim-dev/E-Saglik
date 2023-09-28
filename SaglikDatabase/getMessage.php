<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['sendID'])) {
    if ($db->dbConnect()) {
        $dizi = $db->getMessages("messages", $_POST['sendID']);
        echo $dizi[$_POST['index']];
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
