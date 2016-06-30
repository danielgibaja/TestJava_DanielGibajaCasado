<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:write name="prestacionesForm" property="titulo"/></title>
</head>
<body>
	<h1><bean:write name="prestacionesForm" property="titulo"/></h1>
	Inicio de proceso: <bean:write name="prestacionesForm" property="inicio"/>
	<br/>
	Fin de proceso: <bean:write name="prestacionesForm" property="fin"/>
	<br/>
	<br/>
	Diferencia de tiempo (ms): <bean:write name="prestacionesForm" property="diferencia_tiempo"/>
	<br/>
	<br/>
	<a href="/TestJava_DanielGibajaCasado/titulo.do">Volver</a>
</body>
</html>