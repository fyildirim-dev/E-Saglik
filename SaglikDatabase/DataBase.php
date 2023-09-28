<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $tc, $sifre)
    {
        $tc = $this->prepareData($tc);
        $sifre = $this->prepareData($sifre);
        $this->sql = "select * from " . $table . " where tc = '" . $tc . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['tc'];
            $dbpassword = $row['sifre'];
            if ($dbusername == $tc && password_verify($sifre, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $tc, $ad, $soyad, $email, $sifre)
    {
        $tc = $this->prepareData($tc);
        $soyad = $this->prepareData($soyad);
        $sifre = $this->prepareData($sifre);
        $ad = $this->prepareData($ad);
        $email = $this->prepareData($email);
        $sifre = password_hash($sifre, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (tc, ad, soyad, email, sifre) VALUES ('" . $tc . "','" . $ad . "','" . $soyad . "','" . $email . "','" . $sifre . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function message($table, $mesaj, $sendID, $receiverID)
    {
        $mesaj = $this->prepareData($mesaj);
        $sendID = $this->prepareData($sendID);
        $receiverID = $this->prepareData($receiverID);
        $this->sql =
            "INSERT INTO " . $table . " (mesaj, sendID, receiverID) VALUES ('" . $mesaj . "','" . $sendID . "','" . $receiverID . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function getMessages($table, $sendID)
    {
        $sendID = $this->prepareData($sendID);
        $this->sql = "select mesaj from messages where sendID = '" . $sendID . "'";
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) > 0) {
            $dizi = array();
            while($row = mysqli_fetch_assoc($result)) {
                array_push($dizi, $row["mesaj"]);
            }
        } else {
          echo "0 results";
        }

        return $dizi;
    }

    function name($table, $tc)
    {
        $tc = $this->prepareData($tc);
        $this->sql = "select * from " . $table . " where tc = '" . $tc . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        return "". $row['ad'] . " " . $row['soyad'] ."";
    }

    function userOrDoctor($table, $tc)
    {
        $tc = $this->prepareData($tc);
        $this->sql = "select * from " . $table . " where tc = '" . $tc . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        return $row['doctor'];
    }

    function signUpPoliclinic($table, $doctor, $policlinic, $doctorkod)
    {
        $doctor = $this->prepareData($doctor);
        $policlinic = $this->prepareData($policlinic);
        $doctorkod = $this->prepareData($doctorkod);
        $this->sql =
            "INSERT INTO " . $table . " (doctor, policlinic, doctorkod) VALUES ('" . $doctor . "','" . $policlinic . "','" . $doctorkod . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function setDoctor($table, $tc)
    {
        $tc = $this->prepareData($tc);
        $this->sql = "update " . $table . " SET doctor = 1 where tc = '" . $tc . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        return true;
    }

    function meeting($table, $patient, $doctor, $mesaj, $point)
    {
        $patient = $this->prepareData($patient);
        $doctor = $this->prepareData($doctor);
        $mesaj = $this->prepareData($mesaj);
        $point = $this->prepareData($point);

        $this->sql =
            "INSERT INTO " . $table . " (patient, doctor, mesaj, point) VALUES ('" . $patient . "','" . $doctor . "','" . $mesaj . "','" . $point . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function meetingUpdate($table, $patient, $doctor, $mesaj, $point)
    {
        $patient = $this->prepareData($patient);
        $doctor = $this->prepareData($doctor);
        $mesaj = $this->prepareData($mesaj);
        $point = $this->prepareData($point);

        $this->sql =
            "UPDATE `meetings` SET `mesaj` = '$mesaj', `point` = '$point', `time` = CURRENT_TIME() WHERE (patient='$patient' AND doctor='$doctor')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function profilUpdate($table, $tc, $ad, $soyad, $email, $sifre)
    {
        $tc = $this->prepareData($tc);
        $soyad = $this->prepareData($soyad);
        $sifre = $this->prepareData($sifre);
        $ad = $this->prepareData($ad);
        $email = $this->prepareData($email);
        $sifre = password_hash($sifre, PASSWORD_DEFAULT);
        
        $this->sql =
            "UPDATE `users` SET `ad` = '$ad', `soyad` = '$soyad', `email` = '$email', `sifre` = '$sifre'  WHERE (tc = '$tc')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function kodsend($table, $tc, $kod)
    {
        $tc = $this->prepareData($tc);
        $kod = $this->prepareData($kod);
        $this->sql =
            "INSERT INTO " . $table . " (tc, kod) VALUES ('" . $tc . "','" . $kod . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function kodcheck($table, $tc, $kod)
    {
        $tc = $this->prepareData($tc);
        $kod = $this->prepareData($kod);
        $this->sql = "select * from " . $table . " where tc = '" . $tc . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbtc = $row['tc'];
            $dbkod = $row['kod'];
            if ($dbtc == $tc && $dbkod == $kod) {
                $code = true;
            } else $code = false;
        } else $code = false;

        return $code;
    }

    function kodvarmi($table, $tc, $kod)
    {
        $tc = $this->prepareData($tc);
        $kod = $this->prepareData($kod);
        $this->sql = "select * from " . $table . " where tc = '" . $tc . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) >= 1) {
            $code = true;
        } else $code = false;

        return $code;
    }

    function passwordUpdate($table, $tc, $sifre)
    {
        $tc = $this->prepareData($tc);
        $sifre = $this->prepareData($sifre);
        $sifre = password_hash($sifre, PASSWORD_DEFAULT);
        
        $this->sql =
            "UPDATE `users` SET `sifre` = '$sifre'  WHERE (tc = '$tc')";
        if (mysqli_query($this->connect, $this->sql)) {
            $this->sqll = "DELETE FROM `forgotpass` WHERE (tc = '$tc')";
            if (mysqli_query($this->connect, $this->sqll)) {
                return true;
            } else return false;
        } else return false;
    }

}

?>
