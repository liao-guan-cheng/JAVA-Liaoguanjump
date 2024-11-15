<?php
include("connection.php");

$select_db=@mysqli_select_db($link,"nba");

if(!$select_db){
    echo '<br>找不到資料庫!<br>';
}
else{
    $sql = "SELECT team FROM teams";
    $result = mysqli_query($link, $sql);
    $teams = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $teams[] = $row['team'];
    }
}
?>

<html>
<head>
<title>喜愛選擇</title>
</head>
<body>
<p>喜歡的隊伍</p>
<form method="get" action="chose_db.php">
    
<select name="fav_team">
    <?php foreach ($teams as $team): ?>
        <option value="<?php echo $team; ?>"><?php echo $team; ?></option>
    <?php endforeach; ?>
</select>

<input value="送出" type="submit">
</form>

<form method="get" action="chose_db.php">
<br>

<p>喜歡的球員1:</p>
<select name="first">
    <?php foreach ($teams as $team): ?>
        <option value="<?php echo $team; ?>"><?php echo $team; ?></option>
    <?php endforeach; ?>
</select>
<br>

<p>喜歡的球員2:</p>
<select name="sec">
    <?php foreach ($teams as $team): ?>
        <option value="<?php echo $team; ?>"><?php echo $team; ?></option>
    <?php endforeach; ?>
</select>
<br>

<p>喜歡的球員3:</p>
<select name="third">
    <?php foreach ($teams as $team): ?>
        <option value="<?php echo $team; ?>"><?php echo $team; ?></option>
    <?php endforeach; ?>
</select>
<br>

<input value="送出" type="submit">
</form>