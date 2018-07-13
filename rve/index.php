<?php require_once('Connections/Registro.php'); ?>
<?php
$err = "";
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
?>
<?php
// *** Validate request to login to this site.
if (!isset($_SESSION)) {
  session_start();
}

$loginFormAction = $_SERVER['PHP_SELF'];
if (isset($_GET['accesscheck'])) {
  $_SESSION['PrevUrl'] = $_GET['accesscheck'];
}

if (isset($_POST['txtclave'])) {
  $loginUsername=$_POST['txtclave'];
  $password=$_POST['txtclave'];
  $MM_fldUserAuthorization = "";
  $MM_redirectLoginSuccess = "registro.php";
  $MM_redirectLoginFailed = "index.php?var=Codigo Incorrecto";
  $MM_redirecttoReferrer = false;
  mysql_select_db($database_Registro, $Registro);
  
  $LoginRS__query=sprintf("SELECT clave, clave FROM acceso WHERE clave=%s AND clave=%s",
    GetSQLValueString($loginUsername, "text"), GetSQLValueString($password, "text")); 
   
  $LoginRS = mysql_query($LoginRS__query, $Registro) or die(mysql_error());
  $loginFoundUser = mysql_num_rows($LoginRS);
  if ($loginFoundUser) {
     $loginStrGroup = "";
    
    //declare two session variables and assign them
    $_SESSION['MM_Username'] = $loginUsername;
    $_SESSION['MM_UserGroup'] = $loginStrGroup;	      

    if (isset($_SESSION['PrevUrl']) && false) {
      $MM_redirectLoginSuccess = $_SESSION['PrevUrl'];	
    }
    header("Location: " . $MM_redirectLoginSuccess );
  }
  else {
    $err = "Clave Invalida";
  }
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Acceso</title>
<style type="text/css">
<!--
.error {
	color: #FFF;
	background-color: #F00;
	font-family: Tahoma, Geneva, sans-serif;
	font-size: 24px;
	width: 200px;
	border:#FFF;
	border:medium;
}
-->
</style>
</head>

<body background="img/footer_lodyas.png">
<form ACTION="<?php echo $loginFormAction; ?>" METHOD="POST" name="Inicio" id="Inicio" style="text-align:center">
  <p style="color:#0C6; font-family:Tahoma, Geneva, sans-serif; font-size:24px;">
  <table width="200" border="0" align="center">
  <tr>
    <td height="20"><div id="error" class="error" ><?php echo $err; ?></div></td>
  </tr>
</table>

  
  </p>
  <p style="color:#0C6; font-family:Tahoma, Geneva, sans-serif; font-size:24px;">Ingrese Clave de Acceso</p>
  <p><input type="text" name="txtclave" id="txtclave" style="color:#0C6; font-family:Tahoma, Geneva, sans-serif; font-size:24px;
  text-align:center"></p>
  <p><input type="submit" value="Ingresar" style="border:hidden; font:Tahoma, Geneva, sans-serif; font-size:24px;" /></p>
 
  <p>&nbsp;</p>

</form>
</body>
</html>
