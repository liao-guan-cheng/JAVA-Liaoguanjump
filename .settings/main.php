<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NBA會員專區</title>
    <style>
        body {
            background-color: #1a1a1a;
            color: #ffffff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            overflow-y: hidden;
        }

        .header {
            background-color: #282c35;
            color: #61dafb;
            font-size: 32px;
            padding: 20px 0;
            text-align: center;
        }

        .center-table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            border: 2px solid #61dafb;
        }

        td {
            text-align: center;
            padding: 15px;
            font-size: 18px;
            color: #ffffff;
            border: 1px solid #61dafb;
            transition: background-color 0.3s ease-in-out;
        }

        td a {
            text-decoration: none;
            color: #61dafb;
            transition: color 0.3s ease-in-out;
        }

        td:hover {
            background-color: #61dafb;
        }

        td:hover a {
            color: #ffffff;
        }
    </style>
</head>
<body>
    <div class="header">NBA會員專區</div>
    <table class="center-table">
        <tr>
            <td><a href="bg01.php" target="top">首頁</a></td>
            <td><a href="signup.php" target="top">註冊</a></td>
            <td><a href="login.php" target="top">登入</a></td>
            <td><a href="add.php" target="top">新增</a></td>
            <td><a href="edit.php" target="top">修改</a></td>
            <td><a href="chose.php" target="top">喜愛選擇</a></td>
            <td><a href="chatting_frame.php" target="top">論壇</a></td>
            <td><a href="logout.php" target="top">登出</a></td>
        </tr>
    </table>
</body>
</html>
