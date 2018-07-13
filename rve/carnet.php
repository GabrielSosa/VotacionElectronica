<?php require_once('Connections/Registro.php'); ?>
<?php
//initialize the session
if (!isset($_SESSION)) {
  session_start();
}

// ** Logout the current user. **
$logoutAction = $_SERVER['PHP_SELF']."?doLogout=true";
if ((isset($_SERVER['QUERY_STRING'])) && ($_SERVER['QUERY_STRING'] != "")){
  $logoutAction .="&". htmlentities($_SERVER['QUERY_STRING']);
}

if ((isset($_GET['doLogout'])) &&($_GET['doLogout']=="true")){
  //to fully log out a visitor we need to clear the session varialbles
  $_SESSION['MM_Username'] = NULL;
  $_SESSION['MM_UserGroup'] = NULL;
  $_SESSION['PrevUrl'] = NULL;
  unset($_SESSION['MM_Username']);
  unset($_SESSION['MM_UserGroup']);
  unset($_SESSION['PrevUrl']);
	
  $logoutGoTo = "index.php";
  if ($logoutGoTo) {
    header("Location: $logoutGoTo");
    exit;
  }
}
?>
<?php
if (!isset($_SESSION)) {
  session_start();
}
$MM_authorizedUsers = "";
$MM_donotCheckaccess = "true";

// *** Restrict Access To Page: Grant or deny access to this page
function isAuthorized($strUsers, $strGroups, $UserName, $UserGroup) { 
  // For security, start by assuming the visitor is NOT authorized. 
  $isValid = False; 

  // When a visitor has logged into this site, the Session variable MM_Username set equal to their username. 
  // Therefore, we know that a user is NOT logged in if that Session variable is blank. 
  if (!empty($UserName)) { 
    // Besides being logged in, you may restrict access to only certain users based on an ID established when they login. 
    // Parse the strings into arrays. 
    $arrUsers = Explode(",", $strUsers); 
    $arrGroups = Explode(",", $strGroups); 
    if (in_array($UserName, $arrUsers)) { 
      $isValid = true; 
    } 
    // Or, you may restrict access to only certain users based on their username. 
    if (in_array($UserGroup, $arrGroups)) { 
      $isValid = true; 
    } 
    if (($strUsers == "") && true) { 
      $isValid = true; 
    } 
  } 
  return $isValid; 
}

$MM_restrictGoTo = "index.php";
if (!((isset($_SESSION['MM_Username'])) && (isAuthorized("",$MM_authorizedUsers, $_SESSION['MM_Username'], $_SESSION['MM_UserGroup'])))) {   
  $MM_qsChar = "?";
  $MM_referrer = $_SERVER['PHP_SELF'];
  if (strpos($MM_restrictGoTo, "?")) $MM_qsChar = "&";
  if (isset($QUERY_STRING) && strlen($QUERY_STRING) > 0) 
  $MM_referrer .= "?" . $QUERY_STRING;
  $MM_restrictGoTo = $MM_restrictGoTo. $MM_qsChar . "accesscheck=" . urlencode($MM_referrer);
  header("Location: ". $MM_restrictGoTo); 
  exit;
}
?>
<?php
if (!function_exists("GetSQLValueString")) {
function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
{
  if (PHP_VERSION < 6) {
    $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
  }

  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

  switch ($theType) {
    case "text":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;    
    case "long":
    case "int":
      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
      break;
    case "double":
      $theValue = ($theValue != "") ? doubleval($theValue) : "NULL";
      break;
    case "date":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;
    case "defined":
      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
      break;
  }
  return $theValue;
}
}

$colname_Recordset1 = "-1";
if (isset($_GET['var'])) {
  $colname_Recordset1 = $_GET['var'];
}
mysql_select_db($database_Registro, $Registro);
$query_Recordset1 = sprintf("SELECT nomcompleto, id FROM usuario WHERE id = %s", GetSQLValueString($colname_Recordset1, "text"));
$Recordset1 = mysql_query($query_Recordset1, $Registro) or die(mysql_error());
$row_Recordset1 = mysql_fetch_assoc($Recordset1);
$totalRows_Recordset1 = mysql_num_rows($Recordset1);
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Carnet de Votacion</title>
<style type="text/css">
<!--
.carn {
	padding-right: 300px;
	padding-left: 300px;
	width: 600px;
}
.foto {
	padding-top: 20px;
	padding-right: 20px;
	padding-left: 325px;
	color: #009;
	background-color: #FFF;
	background:url(img/fondo.png);
}
.datos {
	float: left;
	padding-top: 7px;
	padding-left: 20px;
	color: #009;
	font-size: 16px;
	font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
	background-color: #FFF;
	background:url(img/fondo.png);
	font-weight: bold;
}
ca {
	color: #3C3;
	background:url(img/fondo.png);
}
-->
</style>
</head>

<body background="img/footer_lodyas.png" onload="update_qrcode()">
<div align="center" id="carnet" >
<p></p>
<p></p>
<p></p>
<div class="carn" id="muestra">
  <div id="datos" align="left" class="datos">
    <table width="364" border="0" align="center">
      <tr>
        <td width="358" height="43" align="left">Nombre: <?php echo $row_Recordset1['nomcompleto']; ?></td>
      </tr>
      <tr>
        <td height="42" align="left">No. Identidad : <?php echo $_GET["var"];?>
<label>
  <input type="text" name="txtid" id="txtid" value="<?php echo $_GET["var"];?>" style="visibility:hidden"/>
</label></td>
      </tr>
      <tr>
        <td height="42" align="left">Codigo Qr Personal
          <script type="text/javascript" src="qrcode.js"></script>
          <script type="text/javascript">
			  function imprSelec(muestra)
					{	
						document.getElementById('bte').style.visibility='hidden';
						window.print();
					}
			function btnfoc()
			{
				document.getElementById('bte').style.backgroundColor = '#FFF'
				document.getElementById('bte').style.color = '#3C3'
				document.getElementById('bte').style.borderColor = '#3C3'			
			}
			function btnout()
			{
				document.getElementById('bte').style.backgroundColor = '#3C3'
				document.getElementById('bte').style.color = '#FFF'
				document.getElementById('bte').style.borderColor = '#3C3'			
			}
			  </script>
          <div id="qr"></div></td>
      </tr>
    </table>
  </div>
  <div id="foto" align="right" class="foto">
    <p><img src="imagenesusuarios/<?php echo $_GET["var"];?>.jpeg" width="130" height="140"/></p>
    <p class="datos">&nbsp;</p>

	
</p>
    <p>Fecha de emision: 	<script language="javascript">
		Todays = new Date();
		TheDate = "" + (Todays.getMonth()+ 1) +"/"+ Todays.getDate() + "/" + (Todays.getYear() + 1900);  
		document.write(TheDate);
    </script></p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
  </div>
</div>
<p></p>

<input type="button" style="width:100px; background-color:#3C3; height:30px; font:'Trebuchet MS', Arial, Helvetica, sans-serif; width:100px; height:36px; font-size:18px; border-width:medium; border-radius:5px;  color:#FFF; margin-left:-2px;" name="btenviar" id="bte" value="Imprimir";  
        onmouseover="btnfoc()" onmouseout="btnout()" />
  <a href="<?php echo $logoutAction ?>">
<input type="button" style="width:100px; background-color:#3C3; height:30px; font:'Trebuchet MS', Arial, Helvetica, sans-serif; width:100px; height:36px; font-size:18px; border-width:medium; border-radius:5px;  color:#FFF; margin-left:-2px;" name="bte" id="bte2" value="Ir a Inicio";  
        onmouseover="btnfoc()" onmouseout="btnout()" />
</a></div>
</body>
</html>
<?php
mysql_free_result($Recordset1);
?>
