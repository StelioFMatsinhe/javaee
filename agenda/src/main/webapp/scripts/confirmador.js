/**
 * Confirmar a exclusao de um contacto
 * @author Stelio Matsinhe
 * @param idcon
 */

 function confirmar(idcon) {
	 let resposta = confirm("Confirma a exclusão deste contacto?")
	 if (resposta ===true) {
		window.location.href="delete?idcon=" + idcon
	 }
 }