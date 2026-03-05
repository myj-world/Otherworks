<?php
$password = $_POST["password"];
$title = $_POST["title"];
$short_description = $_POST["s-describe"];
$subject = $_POST["subject"];
$chapter = $_POST["chap"];
$credit = $_POST["credit"];
if ($credit == "external") {
    $author_name = $_POST["auth_name"];
    $author_link = $_POST["auth_link"];
}


if ($password == "THE_PASSWORD") {
    $host = "";
    $dbuser = "Authorm";
    $dbpassword = "Auth@gi235";
    $dbname = "Accorm";
    $conn = new mysqli($host, $dbuser, $dbpassword, $dbname);
    if ($conn) {
        $conn->query("INSERT INTO ...");
    echo "Success";
    } else {
        echo "Error";
    }
} else {
    echo "Error";
}
?>