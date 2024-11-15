<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>發文</title>
    <style>
        body {
            background-color: #1a1a1a;
            color: #ffffff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            overflow-y: hidden;
        }

        h1 {
            text-align: center;
            color: #61dafb;
            margin-top: 20px;
        }

        form {
            max-width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: #2b2e39;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #61dafb;
        }

        input[type="text"],
        textarea {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #61dafb;
            border-radius: 5px;
            background-color: #3a3f4c;
            color: #ffffff;
        }

        input[type="submit"] {
            background-color: #61dafb;
            color: #ffffff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease-in-out;
        }

        input[type="submit"]:hover {
            background-color: #4CAF50;
        }
    </style>
</head>
<body>
    <h1>發文</h1>
    <form method="post" action="">
        <label for="title">標題:</label>
        <input type="text" id="title" name="title">
        
        <label for="content">內容:</label>
        <textarea id="content" name="content" rows="10" cols="40"></textarea>
        
        <input type="submit" value="發布">
    </form>
</body>
</html>
<?php
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        include("connection.php");

        $select_db = @mysqli_select_db($link, "nba");

        $title = mysqli_real_escape_string($link, $_POST["title"]);
        $content = mysqli_real_escape_string($link, $_POST["content"]);
        $sql = "INSERT INTO posts (title, content) VALUES ('$title', '$content')";
        mysqli_query($link, $sql);

        // 重新導向到 chatting.php
    }
?>