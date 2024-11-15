<?php
    session_start();

    include("connection.php");

    $select_db = @mysqli_select_db($link, "nba");
    if (!$select_db) {
        echo '<br>找不到資料庫!<br>';
    } else {
        isset($_SESSION["usrid"]);
        $usrid = $_SESSION["usrid"];

        // Fetch total popularity
        $sql_popularity = "SELECT SUM(popularity_ranking) AS total_popularity FROM players_popularity_ranking";
        $result = mysqli_query($link, $sql_popularity);
        $row = mysqli_fetch_assoc($result);
        $total_popularity = $row['total_popularity'];

        // Fetch and display player data
        $html_table = '<html>
                            <head>
                                <style>
                                    body {
                                        background-color: #DBD1D2;
                                        display: flex;
                                        align-items: stretch;
                                        height: 100vh;
                                    }
                                    .table-container {
                                        flex: 1;
                                        overflow-y: auto;
                                        margin-right: 10px;
                                    }
                                    .ranking-table {
                                        border-collapse: collapse;
                                        width: 100%;
                                    }
                                    .ranking-table th, .ranking-table td {
                                        border: 1px solid #3a2529;
                                        padding: 10px;
                                        text-align: center;
                                    }
                                    .ranking-table th {
                                        background-color: #4A4D9D;
                                        color: #FFFFFF;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="table-container">
                                    <table class="ranking-table" border="1" align="center">
                                        <tr>
                                            <th>球員</th>
                                            <th>球員熱度排名</th>
                                        </tr>';

        $sql_players = "SELECT English_name, popularity_ranking FROM players_popularity_ranking WHERE popularity_ranking > 0 ORDER BY popularity_ranking DESC";
        $result_players = mysqli_query($link, $sql_players);
        while ($row_players = mysqli_fetch_assoc($result_players)) {
            $player_name = $row_players['English_name'];
            $player_rate = $row_players['popularity_ranking'] / $total_popularity * 100;

            $html_table .= '<tr>
                                <td>' . $player_name . '</td>
                                <td>' . number_format($player_rate, 2) . '%</td>
                            </tr>';
        }

        $html_table .= '</table></div>';

        echo $html_table;

        // Fetch total predict
        $sql_champion = "SELECT SUM(predict_ranking) AS total_predict FROM champion_predict";
        $result1 = mysqli_query($link, $sql_champion);
        $row1 = mysqli_fetch_assoc($result1);
        $total_predict = $row1['total_predict'];

        // Fetch and display team data
        $html_table1 = '<div class="table-container">
                            <table class="ranking-table" border="1" align="center">
                                <tr>
                                    <th>球隊</th>
                                    <th>冠軍預測排名</th>
                                </tr>';

        $sql_teams = "SELECT team, predict_ranking FROM champion_predict WHERE predict_ranking > 0 ORDER BY predict_ranking DESC";
        $result_teams = mysqli_query($link, $sql_teams);
        while ($row_teams = mysqli_fetch_assoc($result_teams)) {
            $team_name = $row_teams['team'];
            $team_rate = $row_teams['predict_ranking'] / $total_predict * 100;

            $html_table1 .= '<tr>
                                <td>' . $team_name . '</td>
                                <td>' . number_format($team_rate, 2) . '%</td>
                            </tr>';
        }

        $html_table1 .= '</table></div></body></html>';

        echo $html_table1;
    }
?>
