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
    session_start();  // 開始 session

    include("connection.php");

    $select_db = @mysqli_select_db($link, "nba");
    if (!$select_db) {
        echo '<h1>找不到資料庫!</h1>';
    } 
    else {
        echo '<h1>' . $_SESSION["usrid"] . '</h1>';

        if (isset($_SESSION["usrid"]) && isset($_GET["favorite_player"]) && isset($_GET["favorite_team"])) {
            $usrid = $_SESSION["usrid"];
            $favorite_player = $_GET["favorite_player"];
            $favorite_team = $_GET["favorite_team"];

            // 更新使用者資訊
            $sql_update = "UPDATE usr SET English_name = '$favorite_player', team = '$favorite_team' WHERE usrid = '$usrid'";
            $result = mysqli_query($link, $sql_update);

            if ($result) {
                echo '<p class="success">新增成功!</p>';
            } else {
                echo '<p class="failure">新增失敗!</p>';
            }
        }
        
        $sql = "UPDATE players_popularity_ranking SET popularity_ranking = popularity_ranking + 1 WHERE `English_name` = '$favorite_player'";
        $result = mysqli_query($link, $sql);
        $sql = "UPDATE champion_predict SET predict_ranking = predict_ranking + 1 WHERE `team` = '$favorite_team'";
        $result = mysqli_query($link, $sql);
    }
    ?>
</body>
</html>
