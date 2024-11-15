<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>會員登入</title>
    <style>
        body {
            background-color: #1a1a1a;
            color: #ffffff;
            font-family: '標楷體', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            overflow-y: hidden;
        }

        td, th {
            padding: 10px;
            font-size: 15px;
        }

        hr {
            border: 1px solid #61dafb;
            margin-top: 0;
        }

        center {
            display: block;
            text-align: center;
        }

        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
        }

        table tr {
            background-color: #4A4D9D;
            color: #FFFFFF;
        }

        table tr:nth-child(even) {
            background-color: #A4ABD6;
        }

        input[type="text"] {
            padding: 5px;
            margin: 5px 0;
        }

        input[type="submit"], input[type="reset"] {
            background-color: #61dafb;
            color: #ffffff;
            padding: 8px 15px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            margin: 5px;
            transition: background-color 0.3s ease-in-out;
        }

        input[type="submit"]:hover, input[type="reset"]:hover {
            background-color: #282c35;
        }
    </style>
</head>
<body>
    <form method="get" action="login_db.php">
        <p align="center"><font size="6" face="標楷體" color="white">NBA會員登入</font></p>
        <hr>
        <center>
            <table border="0" width="100%" padding="5px">
                <tr>
                    <td align="right">會員ID:</td>
                    <td align="left"><input type="text" maxLength="10" size="10" name="id"></td>
                </tr>
                <tr>
                    <td align="right">密碼:</td>
                    <td align="left"><input type="text" maxLength="10" size="10" name="passwd"></td>
                </tr>
            </table>
            <p align="center">
                <input value=" 登  入" type="submit">
                <input value=" 清  除 " type="reset">
            </p>
        </center>
    </form>
</body>
</html>
