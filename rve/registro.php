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


if ((isset($_POST["MM_insert"])) && ($_POST["MM_insert"] == "form1")) 
{	
  $loginUsername = $_POST['txtid'];
  $LoginRS__query = sprintf("SELECT id FROM usuario WHERE id=%s", GetSQLValueString($loginUsername, "text"));
  mysql_select_db($database_Registro, $Registro);
  $LoginRS=mysql_query($LoginRS__query, $Registro) or die(mysql_error());
  $loginFoundUser = mysql_num_rows($LoginRS);
 if($loginFoundUser){
		?> 
		<script language="javascript"> 
		alert("Identidad ya se encuentra registrada");
		</script> 
		<?php  
  }
  else
  {
  $insertSQL = sprintf("INSERT INTO usuario (nomcompleto, id, direccion, edad, telefono, fecha, genero, pin) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
                       GetSQLValueString($_POST['txtnom'], "text"),
                       GetSQLValueString($_POST['txtid'], "text"),
                       GetSQLValueString($_POST['dir'], "text"),
                       GetSQLValueString($_POST['edad'], "int"),
                       GetSQLValueString($_POST['telefono'], "text"),
                       GetSQLValueString($_POST['fecha'], "text"),
                       GetSQLValueString($_POST['listGenero'], "text"),
                       GetSQLValueString($_POST['pin'], "text"));

  mysql_select_db($database_Registro, $Registro);
  $Result1 = mysql_query($insertSQL, $Registro) or die(mysql_error());

  $insertGoTo = "tomarfoto.php";
  if (isset($_SERVER['QUERY_STRING'])) {
    $insertGoTo .= (strpos($insertGoTo, '?')) ? "&" : "?";
    $insertGoTo .= $_SERVER['QUERY_STRING'];
  }
    header('Location: pin.php?var='.$_POST['txtid']);
  //header(sprintf("Location: %s", $insertGoTo));
}
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Formulario de Registro</title>
<style type="text/css">
<!--
#form1 p {
	background-color: #6C6;
	text-align: center;
	color: #000;
	font-size: 18px;
	font-weight: bold;
}
body {
	margin-left: 150px;
	margin-right: 150px;
	background-image: url(img/footer_lodyas.png);
	text-align: center;
}
.cuerpo {
	text-align: center;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 24px;
	background-image: url(img/footer_lodyas.png);
	color: #FFF;
}
.forma {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-size: 14px;
	font-weight: normal;
	background-image: url(img/footer_lodyas.png);
	color: #FFF;
	text-align: center;
}
.cuerpo .forma #form1 table {
	text-align: center;
}
.cuerpo .forma #form1 label a {
	color: #0FC;
}
.codbarra {
}
.forma a {
	color: #0F9;
}
.paneldeerror {
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
	text-align:center;
	float: left;
	position: absolute;
	visibility: hidden;
}
#apDiv1 {
	position:absolute;
	left:958px;
	top:124px;
	width:187px;
	height:222px;
	z-index:1;
}
.casillas{
color:#3C3; 
height:25px; 
border-radius:5px; 
font-weight:bold;
border-color:#3C3; 
border-width:medium; 
text-align:center;
}
-->
</style>
    <script language="javascript">
	
		function activar()
		{
		var nom = document.getElementById('txtnom');
		var dir = document.getElementById('dir');
		var id = document.getElementById('txtid');
		var edad = document.getElementById('edad');
		
		var error1 = document.getElementById('nomerror');
		var error2 = document.getElementById('direrror');
		var error3 = document.getElementById('iderror');
		var error4 = document.getElementById('ederror');
		var terror1 = document.getElementById('errorn');
		var terror2 = document.getElementById('errord');
		var terror3 = document.getElementById('errori');
		var terror4 = document.getElementById('errore');
		var boton = document.getElementById('btenviar');
	if((error1.style.visibility=="hidden")&&(error2.style.visibility=="hidden")&&(error3.style.visibility=="hidden")&&(error4.style.visibility=="hidden"))
			{
				boton.disabled = false;
				boton.style.backgroundColor = "#3C3";
			}
		else
		{
				boton.disabled = true;
				boton.style.backgroundColor = "#CCC";		
		}
		}

		function validarnombre()
		{	
		var nom = document.getElementById('txtnom');
		var dir = document.getElementById('dir');
		var id = document.getElementById('txtid');
		var edad = document.getElementById('edad');
		
		var error1 = document.getElementById('nomerror');
		var error2 = document.getElementById('direrror');
		var error3 = document.getElementById('iderror');
		var error4 = document.getElementById('ederror');
		var terror1 = document.getElementById('errorn');
		var terror2 = document.getElementById('errord');
		var terror3 = document.getElementById('errori');
		var terror4 = document.getElementById('errore');			
		if(nom.value == ''||nom.value.length<7)
			{
				error1.style.visibility="visible";
				terror1.style.visibility="visible";				
			}
		else
			{
				error1.style.visibility="hidden";
				terror1.style.visibility="hidden";
			}
		activar();					
		}

		function validardir()
		{
		var nom = document.getElementById('txtnom');
		var dir = document.getElementById('dir');
		var id = document.getElementById('txtid');
		var edad = document.getElementById('edad');
		
		var error1 = document.getElementById('nomerror');
		var error2 = document.getElementById('direrror');
		var error3 = document.getElementById('iderror');
		var error4 = document.getElementById('ederror');
		var terror1 = document.getElementById('errorn');
		var terror2 = document.getElementById('errord');
		var terror3 = document.getElementById('errori');
		var terror4 = document.getElementById('errore');				
		if(dir.value == ''||dir.value.length<10)
			{
				error2.style.visibility="visible";
				terror2.style.visibility="visible";
			}
			else
			{
				error2.style.visibility="hidden";
				terror2.style.visibility="hidden";
			}
		activar();
		}
		
		

		function validarid()
		{
		var nom = document.getElementById('txtnom');
		var dir = document.getElementById('dir');
		var id = document.getElementById('txtid');
		var edad = document.getElementById('edad');
		
		var error1 = document.getElementById('nomerror');
		var error2 = document.getElementById('direrror');
		var error3 = document.getElementById('iderror');
		var error4 = document.getElementById('ederror');
		var terror1 = document.getElementById('errorn');
		var terror2 = document.getElementById('errord');
		var terror3 = document.getElementById('errori');
		var terror4 = document.getElementById('errore');
		if(id.value == ''||id.value.length<13||isNaN(id.value))
			{
				error3.style.visibility="visible";
				terror3.style.visibility="visible";
			}
			else
			{
				error3.style.visibility="hidden";
				terror3.style.visibility="hidden";
				update_qrcode();
			}
		activar();
		}

		function validaredad()
		{
		var nom = document.getElementById('txtnom');
		var dir = document.getElementById('dir');
		var id = document.getElementById('txtid');
		var edad = document.getElementById('edad');
		
		var error1 = document.getElementById('nomerror');
		var error2 = document.getElementById('direrror');
		var error3 = document.getElementById('iderror');
		var error4 = document.getElementById('ederror');
		var terror1 = document.getElementById('errorn');
		var terror2 = document.getElementById('errord');
		var terror3 = document.getElementById('errori');
		var terror4 = document.getElementById('errore');				
		if(edad.value == ''||parseInt(edad.value)<18||isNaN(edad.value))
			{
				error4.style.visibility="visible";
				terror4.style.visibility="visible";
			}
			else
			{
				error4.style.visibility="hidden";
				terror4.style.visibility="hidden";
			}
		activar();
		}
		
	function fecha()
	{	
	
		Todays = new Date();
		mi=Todays.getMinutes();
		se=Todays.getSeconds();
		if(Todays.getHours()>12)
		{
			m="PM";
			h=Todays.getHours()-12
		}
		else
		{
			m="AM";			
		}
		TheDate = "" + (Todays.getMonth()+ 1) +"/"+ Todays.getDate() + "/" + (Todays.getYear() + 1900);
		document.getElementById('fecha').value = TheDate;
	}
function showtime () {
	var now = new Date();

	var hours = now.getHours();

	var minutes = now.getMinutes();

	var seconds = now.getSeconds()

	var timeValue = "" + ((hours >12) ? hours -12 :hours)

	timeValue += ((minutes < 10) ? ":0" : ":") + minutes

	timeValue += ((seconds < 10) ? ":0" : ":") + seconds

	timeValue += (hours >= 12) ? " P.M." : " A.M."

	document.getElementById('fecha').value += timeValue;
}
	
		
		function gencod()
		{
			var e = "";
			e+=Math.floor(Math.random() * 10);
			e+=Math.floor(Math.random() * 10);
			e+=Math.floor(Math.random() * 10);
			e+=Math.floor(Math.random() * 10);
			document.getElementById('pin').value = e;
			fecha();
			showtime();
		}
		
	</script>
</head>

<body onLoad="gencod()">
<form method="POST" name="form1" id="form1" class="forma">
  <label><br />
    Porfavor Ingrese Sus Datos<br />
  </label>
  <label><br />
  </label>
  <table width="467" border="0" align="center" class="forma">
    <tr>
      <td width="127" height="43" class="codbarra">Nombre completo</td>
      <td width="317"><input name="txtnom" type="text" id="txtnom" class="casillas" size="40" onChange="validarnombre()" />
      </td>
      <td width="10"><div id="nomerror" class="inc"><input name="errorn" type="text" disabled="disabled" class="inc" id="errorn" value="Nombre Invalido"/></div></td>
    </tr>
    <tr>
      <td height="42">Dirección</td>
      <td><input name="dir" type="text" id="dir" class="casillas" size="40" onChange="validardir()" /></td>
      <td><div id="direrror" class="inc"><input name="errord" type="text" disabled="disabled" class="inc" id="errord" value="Direccion Invalida"/></div></td>
    </tr>
    <tr>
      <td height="42">No. Identidad (sin guiones)</td>
      <td><input name="txtid" type="text" id="txtid" class="casillas" size="13" maxlength="13" onChange="validarid()"/></td>
      <td><div id="iderror" class="inc"><input name="errori" type="text" disabled="disabled" class="inc" id="errori" value="ID Invalido"/></div></td>
    </tr>
    <tr>
      <td height="42">Telefono</td>
      <td><input name="telefono" type="text" class="casillas" id="telefono" size="10" maxlength="8"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="42">Edad</td>
      <td><input name="edad" type="text" class="casillas" id="edad" size="5" maxlength="3" onChange="validaredad()"/></td>
      <td><div id="ederror" class="inc"><input name="errore" type="text" disabled="disabled" class="inc" id="errore" value="Edad Invalido"/></div></td>
    </tr>
    <tr>
      <td height="42">Genero</td>
      <td><label>
        <select name="listGenero" class="casillas" id="listGenero" style="color:#3C3; height:25px; border-radius:5px; font-weight:bold;
          border-color:#3C3; border-width:medium; text-align:center;">
          <option value="Masculino">Masculino</option>
          <option value="Femenino">Femenino</option>
        </select>
      </label></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="42">Codigo Qr Personal</td>
      <td><div id="qr"></div></td>
      <td>&nbsp;</td>
    </tr>
  </table>
  <label>
    <script type="text/javascript" src="qrcode.js"></script>
    <script language="JavaScript1.5" type="text/javascript">
			function btnfoc()
			{
				document.getElementById('btenviar').style.backgroundColor = '#FFF'
				document.getElementById('btenviar').style.color = '#3C3'
				document.getElementById('btenviar').style.borderColor = '#3C3'			
			}
			function btnout()
			{
				document.getElementById('btenviar').style.backgroundColor = '#3C3'
				document.getElementById('btenviar').style.color = '#FFF'
				document.getElementById('btenviar').style.borderColor = '#3C3'			
			}
		   function isCheck(){
			   if (document.getElementById('chkterm').checked == true){
								   	document.getElementById('btenviar').disabled = false
									document.getElementById('btenviar').style.backgroundColor = '#3C3'
									document.getElementById('btenviar').style.color= '#FFF'
									document.getElementById('btenviar').style.borderColor = '#3C3'
									
									
									
				 
			   } else {
				   document.getElementById('btenviar').disabled = true
				   document.getElementById('btenviar').style.backgroundColor = '#CCC'
				   document.getElementById('btenviar').style.color = '#FFF'
				   document.getElementById('btenviar').style.borderColor= '#CCC'
									
			   }}
			  </script>
    <br />
    <input type="submit" style="width:100px; background-color:#CCC; height:30px; font:'Trebuchet MS', Arial, Helvetica, sans-serif; width:100px; height:36px; font-size:18px; color:#FFF; margin-left:-2px;" name="btenviar" id="btenviar" value="Registrar"
        onmouseover="btnfoc()" onMouseOut="btnout()" disabled="disabled"/>
    <br />
    <br />
    Perdi mi carnet: <a>Imprimir uno nuevo</a>
    <br />
    <input name="fecha" type="text" id="fecha" style=" color:#F00;border-radius:5px;border: 1px; border-width:thin; visibility:hidden" size="40" />
    <br />
    <input name="pin" type="text" id="pin" style=" color:#F00;border-radius:5px;border: 1px; border-width:thin; visibility:hidden" size="40" />
<br />
    <br />
  </label>
  <input type="hidden" name="MM_insert" value="form1" />
</form>
</body>
</html>