<?php 
$id = $_GET["var"];
?>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Tomar Foto</title>
<?php
if (isset($GLOBALS["HTTP_RAW_POST_DATA"]))
{
// Get the data
$imageData=$GLOBALS['HTTP_RAW_POST_DATA'];
// Remove the headers (data:,) part.
// A real application should use them according to needs such as to check image type
$filteredData=substr($imageData, strpos($imageData, ",")+1);

// Need to decode before saving since the data we received is already base64 encoded
$unencodedData=base64_decode($filteredData);

//echo "unencodedData".$unencodedData;

// Save file. This example uses a hard coded filename for testing,
// but a real application can specify filename in POST variable
$fp = fopen( 'imagenesusuarios/'.$_GET["var"].'.jpeg', 'wb' );
fwrite( $fp, $unencodedData);
fclose( $fp );
}
?>
  <script type='text/javascript' src='js/jquery-1.8.3.js'></script>
  <script src="js/jquery.min.js"></script>
  <script src="js/html5.js"></script>
  
<script type="text/javascript">
function saveViaAJAX()
{
	var testCanvas = document.getElementById("foto");
	var canvasData = testCanvas.toDataURL("image/png");
	var postData = "canvasData="+canvasData;
	var debugConsole= document.getElementById("debugConsole");
	debugConsole.value=canvasData;
	var ajax = new XMLHttpRequest();
	ajax.open("POST",'tomarfoto.php?var='+document.getElementById('txtid').value,true);
	ajax.setRequestHeader('Content-Type', 'canvas/upload');


	ajax.onreadystatechange=function()
  	{
		if (ajax.readyState == 4)
		{
    			document.getElementById("debugFilenameConsole").innerHTML="Imagen guardada con exito";
		}
  	}

	ajax.send(postData);
}

</script>
  
  <style type='text/css'>
    .contenedorc{
	visibility:hidden;
	text-align: center;
}
.forma {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-size: 18px;
	font-weight: bold;
	background-image: url(img/footer_lodyas.png);
	color: #FFF;
	text-align: center;
}
.boton{
	background-color:#3C3; 
	height:30px; 
	font:'Trebuchet MS', Arial, Helvetica, sans-serif; 
	height:36px; 
	font-size:18px; 
	border-width:thin;
	color:#FFF; 
	margin-left:-2px;
	}
	.contenedorf{visibility:hidden;text-align: center;}
.titulo{ font-size: 12pt; font-weight: bold;text-align: center;}
#camara, #foto{
    width: 320px;
    min-height: 240px;
    border: 1px solid #008000;
	text-align: center;
}

  .boton1 {	background-color:#3C3; 
	height:30px; 
	font:'Trebuchet MS', Arial, Helvetica, sans-serif; 
	height:36px; 
	font-size:18px; 
	border-width:thin;
	color:#FFF; 
	margin-left:-2px;
}
  .boton2 {	background-color:#3C3; 
	height:30px; 
	font:'Trebuchet MS', Arial, Helvetica, sans-serif; 
	height:36px; 
	font-size:18px; 
	border-width:thin;
	color:#FFF; 
	margin-left:-2px;
}
  .boton3 {	background-color:#3C3; 
	height:30px; 
	font:'Trebuchet MS', Arial, Helvetica, sans-serif; 
	height:36px; 
	font-size:18px; 
	border-width:thin;
	color:#FFF; 
	margin-left:-2px;
}
  </style>
  


<script type='text/javascript'>
$(function(){
window.URL = window.URL || window.webkitURL;
navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia ||
function() {
    alert('Su navegador no soporta navigator.getUserMedia().');
};
window.datosVideo = {
    'StreamVideo': null,
    'url': null
}

jQuery('#botonIniciar').on('click', function(e) {
document.getElementById('subir').style.visibility="hidden";	
document.getElementById('botonFoto').style.visibility="visible";
document.getElementById('botonDetener').style.visibility="hidden";
document.getElementById('imp').style.visibility="hidden";
document.getElementById("c").innerHTML="<video id="+"camara"+" controls autoplay></video>";
document.getElementById("f").innerHTML="";
document.getElementById("debugFilenameConsole").innerHTML="";
    navigator.getUserMedia({
        'audio': false,
        'video': true
    }, function(streamVideo) {
        datosVideo.StreamVideo = streamVideo;
        datosVideo.url = window.URL.createObjectURL(streamVideo);
        jQuery('#camara').attr('src', datosVideo.url);
		document.getElementById('camara').style.visibility='visible';
		document.getElementById('foto').style.visibility='hidden';
    }, function() {
        alert('No fue posible obtener acceso a la cámara.');
    });

});

jQuery('#botonDetener').on('click', function(e) {

    if (datosVideo.StreamVideo) {
        datosVideo.StreamVideo.stop();
        window.URL.revokeObjectURL(datosVideo.url);
		
    }
	document.getElementById('botonDetener').style.visibility='hidden';
	document.getElementById('imp').style.visibility='visible';
	document.getElementById('subir').style.visibility='visible';

});


jQuery('#botonFoto').on('click', function(e) {
document.getElementById('botonDetener').style.visibility = "visible";
document.getElementById('botonFoto').style.visibility = "hidden";

	document.getElementById("f").innerHTML="<canvas id="+"foto"+" ></canvas>";	
    var oCamara, oFoto, oContexto, w, h;

    oCamara = jQuery('#camara');
    oFoto = jQuery('#foto');
    w = oCamara.width();
    h = oCamara.height();
    oFoto.attr({
        'width': w,
        'height': h
    });
    oContexto = oFoto[0].getContext('2d');
    oContexto.drawImage(oCamara[0], 0, 0, w, h);
	document.getElementById('camara').style.visibility='hidden';
	document.getElementById('foto').style.visibility='visible';
	document.getElementById("c").innerHTML="";
	document.getElementById("debugFilenameConsole").innerHTML="Foto tomada con exito";

});
});

</script>

    <script>
    function readURL(input) {
	document.getElementById('botonDetener').style.visibility = "visible";	
	document.getElementById('botonFoto').style.visibility = "hidden";	
	document.getElementById('imp').style.visibility = "hidden";	
	
	document.getElementById("f").innerHTML="<img id='img_prev' width='1' height='1' ></img><canvas id="+"foto"+" width='300' height='300'></canvas>";		
	document.getElementById('img_prev').style.visibility='visible';
	document.getElementById('foto').style.visibility='visible';
	
    if (input.files && input.files[0]) {
    var reader = new FileReader();
	reader.onload = function (e) {
    $('#img_prev')
    .attr('src', e.target.result)
    };
    reader.readAsDataURL(input.files[0]);
    }
	var ctx = cargaContextoCanvas('foto');
	if(ctx){
		 var img;
		 img = document.getElementById('img_prev');
		 img.onload = function(){
         ctx.drawImage(img, 0, 0, 300,300);
		 	document.getElementById("debugFilenameConsole").innerHTML="Imagen Cargada con exito";
		 }
    }
    }
	
	
    </script>
    <script language="javascript">
function cargaContextoCanvas(idCanvas){
   var elemento = document.getElementById(idCanvas);
   if(elemento && elemento.getContext){
	   
      var contexto = elemento.getContext('2d');
      if(contexto){
         return contexto;
		 
      }
   }
   return FALSE;
}


</script>
</head>
<body style="background:url(img/footer_lodyas.png)">
<form name="form1" method="post" action="">
  <div class="forma" name="con">
<p>Tome una foto para su carnet</p>
<div class="contenedorf" id="f" >
</div>
<div class="contenedorc" id="c">
</div>

<div class="cuer" id='botonera' align="center">
      <p>
        <input type='button' class="boton2" id='botonIniciar' value = 'Preparar webcam' > 
      </p>
      <p>
        <input type="file" class="boton3" id='subir' onchange="readURL(this);">
        </input>
      </p>
      <p>
        <input type='button' class="boton1" id='botonFoto' value = 'Tomar Foto' style="visibility:hidden;">
        <input type='button' class="boton" id='botonDetener' value = 'Guardar' onClick="saveViaAJAX();" style="visibility:hidden;" ></input>
        <a href="carnet.php?var=<?php echo $_GET["var"];?>">
        <input type='button' class="boton" id='imp' value = 'Imprimir' style="visibility:hidden">
        </input>
        </a></p>
<div id="debugFilenameConsole"></div>
    <textarea id="debugConsole" rows="1" cols="60" style="visibility:hidden">Data</textarea> 
</div>
<input type="text" name="txtid" id="txtid" value="<?php echo $id;?>" style="visibility:hidden"/>
</div>

</form>
</body>


</html>