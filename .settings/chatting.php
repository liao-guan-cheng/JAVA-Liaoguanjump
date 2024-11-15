<?php
    include("connection.php");

    $select_db = @mysqli_select_db($link, "nba");

    $sql = "SELECT * FROM posts ORDER BY created_at DESC";
    $result = mysqli_query($link, $sql);
?>

<!DOCTYPE html>
<html>
<head>
    <title>匿名論壇</title>
    <style>
        div {
            border: 1px solid black;
            padding: 7px;
            margin: 5px;
            background-color: #DBD1D2; ;
        }
        body {
            background-color: #1a1a1a;;
        }
        h1 {
            text-align: center;
            color: white;
        }
    </style>
</head>
<body>
    <h1>匿名論壇</h1>
    <?php while ($row = mysqli_fetch_assoc($result)): ?>
        <div>
            <h2><?php echo $row["title"]; ?></h2>
            <p><?php echo $row["content"]; ?></p>
        </div>
    <?php endwhile; ?>
</body>
</html>