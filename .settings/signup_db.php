<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>更新使用者資訊</title>
    <style>
        body {
            background-color: #1a1a1a;
            color: #ffffff;
            font-family: '標楷體', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            overflow-y: hidden;
            text-align: center;
        }

        h1 {
            color: #61dafb;
        }

        p {
            font-size: 18px;
        }

        .success {
            color: #00ff00;
        }

        .failure {
            color: #ff0000;
        }
    </style>
</head>
<body>
<?php
include("connection.php");

$select_db = @mysqli_select_db($link, "nba");

if (!$select_db) {
    echo '<br><font color="#ff0000" size="5">找不到資料庫!</font><br>';
} else {
    if ($_SERVER["REQUEST_METHOD"] == "GET") {
        // Retrieve form data
        $usrid = $_GET["usrid"];
        $passwd = $_GET["passwd"];
        $EMail = $_GET["EMail"];
        $sex = $_GET["sex"];
        $year = $_GET["year"];
        $mon = $_GET["mon"];
        $days = $_GET["days"];
        $phone = $_GET["phone"];

        // Validate the data (you may want to add more validation)
        if (empty($usrid) || empty($passwd) || empty($EMail) || empty($year) || empty($mon) || empty($days) || empty($phone)) {
            echo '<font color="#ff0000" size="5">資料不完整，請填寫必填欄位</font>';
        } else {
            // Database connection

            // Check connection
            if (!$link) {
                die("Connection failed: " . mysqli_connect_error());
            }
            // Perform any additional validation or processing here

            // Insert data into usr table
            $sql = "INSERT INTO usr (usrid, passwd, `EMail`, sex, birthday, phone) 
                VALUES ('$usrid', '$passwd', '$EMail', '$sex', '$year-$mon-$days', '$phone')";

            if (mysqli_query($link, $sql)) {
                echo '<h2 style="color: #00ff00;">註冊成功！</h2>';
                echo "會員ID: $usrid<br>";
                echo "E-Mail: $EMail<br>";
                echo "性別: $sex<br>";
                echo "生日: $year 年 $mon 月 $days 日<br>";
                echo "電話: $phone<br>";
            } else {
                echo '<font color="#ff0000">Error: ' . $sql . '<br>' . mysqli_error($link) . '</font>';
            }
            // Close the database connection
            mysqli_close($link);
        }
    }
}
?>

    
</body>
</html>