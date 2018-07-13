<?php require_once('Connections/Registro.php'); ?>
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

$colname_juego = "-1";
if (isset($_GET['var'])) {
  $colname_juego = $_GET['var'];
}
mysql_select_db($database_Registro, $Registro);
$query_juego = sprintf("SELECT pin FROM usuario WHERE id = %s ORDER BY id ASC", GetSQLValueString($colname_juego, "text"));
$juego = mysql_query($query_juego, $Registro) or die(mysql_error());
$row_juego = mysql_fetch_assoc($juego);
$totalRows_juego = mysql_num_rows($juego);
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<style type="text/css">
<!--
.carn {
	padding-right: 300px;
	padding-left: 300px;
	width: 600px;
}
.foto {
	color: #009;
	background-color: #FFF;
	text-align: center;
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
.inc{
	color: #FFF;
	background-color: #F00;
	border:#FFF;
	text-align:center;
	height:25px;
	border-radius:5px;
	font-weight:bold;
	border-color:#3C3;
	border-width:medium;
	visibility:hidden;
}
.inc1 {	color: #FFF;
	background-color: #F00;
	border:#FFF;
	text-align:center;
	height:25px;
	border-radius:5px;
	font-weight:bold;
	border-color:#3C3;
	border-width:medium;
	visibility:hidden;
}
.inc11 {color: #FFF;
	background-color: #F00;
	border:#FFF;
	text-align:center;
	height:25px;
	border-radius:5px;
	font-weight:bold;
	border-color:#3C3;
	border-width:medium;
	visibility:hidden;
}
-->
</style>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Pin</title>
</head>

<body background="img/footer_lodyas.png">
<div class="carn" id="muestra">
  <div id="foto" align="center" class="foto">
    <p>&nbsp;</p>
    <table width="200" border="0" align="center">
      <tr>
        <td height="109" align="center" style="font-family:'Trebuchet MS', Arial, Helvetica, sans-serif; font-size:14px;"><p>Se le asignara un pin de 4 digitos es unico y personal, por su propia seguridad guardelo en un lugar seguro y no comparta dicha información.</p></td>
      </tr>
      <tr>
        <td height="109" align="center"><form id="form1" name="form1">
          <a href="javascript:ver()"><input type="button" name="ver" id="ver" value="Ver Pin"/></a>
  <p>
    <input name="ver2" type="text" disabled="disabled" class="inc11" id="ver2" value="<?php echo $row_juego['pin']; ?>" size="6" />
  </p>
  <p><a href="tomarfoto.php?var=<?php echo $colname_juego; ?>">
    <input type="button" name="ver3" id="ver3" value="Siguiente" style="visibility:hidden"/>
  </a> </p>
        </form></td>
<script language="javascript">
function ver()
{
		document.getElementById('ver2').style.visibility = "visible";
		document.getElementById('ver3').style.visibility = "visible";		
}
</script>
      </tr>
    </table>
    <p>&nbsp;</p>
  </div>
</div>
</body>
</html><?php
mysql_free_result($juego);
?>
