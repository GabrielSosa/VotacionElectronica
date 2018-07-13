<?php
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
error_reporting ( E_ALL ^ E_DEPRECATED );
$hostname_Registro = "localhost";
$database_Registro = "registro";
$username_Registro = "root";
$password_Registro = "";
$Registro = mysql_pconnect($hostname_Registro, $username_Registro, $password_Registro) or trigger_error(mysql_error(),E_USER_ERROR); 
?>