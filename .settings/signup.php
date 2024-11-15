<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>會員註冊</title>
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

        TT {
            display: block;
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
    <form method="get" action="signup_db.php">
        <p align="center"><font size="6" face="標楷體" color=white>NBA會員註冊</font></p>
        <hr>
        <TT>
            <center>
                <table border="0" width="100%" padding="5px">
                    <tr>
                        <td align="right">會員ID:</td>
                        <td align="left"><input type="text" maxLength="10" size="10" name="usrid"></td>
                    </tr>
                    <tr>
                        <td align="right">密碼:</td>
                        <td align="left"><input type="text" maxLength="10" size="10" name="passwd"></td>
                    </tr>
                    <tr>
                        <td align="right">E‐Mail:</td>
                        <td align="left"><input type="text" size="20" name="EMail"></td>
                    </tr>
                    <tr>
                        <td align="right">性別:</td>
                        <td align="left">
                            <input value="M" type="radio" name="sex" checked>男
                            <input value="F" type="radio" name="sex">女
                        </td>
                    </tr>
                    <tr>
                        <td align="right">生日:</td>
                        <td align="left">
                            西元:<input type="text" maxLength="4" size="4" name="year">年
                            <input type="text" maxLength="4" size="2" name="mon">月
                            <input type="text" maxLength="4" size="2" name="days">日
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="20%">電話:</td>
                        <td align="left"><input maxLength="15" size="15" name="phone" type="text"></td>
                    </tr>
                </table>
                <p align="center">
                    <input value="線上註冊" type="submit">
                    <input value=" 清  除 " type="reset">
                </p>
            </center>
        </TT>
    </form>
</body>
</html>
