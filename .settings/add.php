<?php
    include("connection.php");

    $select_db = @mysqli_select_db($link, "nba");
?>
<html>
<head>
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
<!-- Modify the form to include dropdown lists -->
<form method="get" action="add_db.php">
    <p align="center"><font size="6" face="標楷體" color=white>最愛的NBA球員/隊伍</font></p>
    <hr>
    <center>
        <table border="0" width="30%" padding="5px">
            <tr bgcolor=#4A4D9D style="color:#FFFFFF">
                <td align="right">預測冠軍隊伍: </td>
                <td align="left">
                    <select name="favorite_team">
                        <?php
                            // Retrieve teams from the database and populate the dropdown
                            $query_teams = "SELECT team FROM teams";
                            echo $query_teams;
                            $result_teams = mysqli_query($link, $query_teams);

                            while ($team = mysqli_fetch_assoc($result_teams)) {
                                echo '<option value="' . $team['team'] . '">' . $team['team'] . '</option>';
                            }
                        ?>
                    </select>
                </td>
            </tr>
            <tr bgcolor=#A4ABD6 style="color:#FFFFFF">
                <td align="right">喜愛的球員: </td>
                <td align="left">
                    <select name="favorite_player">
                        <?php
                            // Retrieve players from the database and populate the dropdown
                            $query_players = "SELECT English_name FROM players";
                            $result_players = mysqli_query($link, $query_players);

                            while ($row_players = mysqli_fetch_assoc($result_players)) {
                                echo '<option value="' . $row_players['English_name'] . '">' . $row_players['English_name'] . '</option>';
                            }
                        ?>
                    </select>
                </td>
            </tr>
        </table>

        <p align="center">
            <input value=" 新  增 " type="submit">
            <input value=" 清  除 " type="reset">
        </p>
</form>