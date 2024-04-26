<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda de Contactos</title>
<link rel='icon' href='imagens/faviconn.png'>
<link rel='stylesheet' href='style.css'>
</head>
<body>
	<h1>Editar Contacto</h1>
	<form name="frmContacto" action="update">
		<table>
			<tr>
				<td><input type="text" name="idcon" id="caixa3" readonly
				value="<%out.print(request.getAttribute("idcon"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="nome" class="Caixa" 
				value="<%out.print(request.getAttribute("nome")); %>"></td>
			</tr>
			<tr>
				<td><input type="text" name="fone" class="Caixa2" 
				value="<%out.print(request.getAttribute("fone")); %>"></td>
			</tr>

			<tr>
				<td><input type="text" name="email" class="Caixa" 
				value="<%out.print(request.getAttribute("email")); %>"></td>
			</tr>

		</table>
		<input type="button" value="Salvar" class="Botao1" onclick="validar()">

	</form>
	<script src="scripts/validador.js"></script>
</body>
</html>