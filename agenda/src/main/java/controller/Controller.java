package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contacto. */
	JavaBeans contacto = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();

	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contactos(request, response);
		} else if (action.equals("/insert")) {
			adicionarContacto(request, response);
		} else if (action.equals("/select")) {
			listarContacto(request, response);
		} else if (action.equals("/update")) {
			editarContacto(request, response);
		} else if (action.equals("/delete")) {
			removerContacto(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Contactos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Listar contactos
	protected void contactos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um objecto que ira receber os dados da classe JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContactos();
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contactos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * Adicionar contacto.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Novo contacto
	protected void adicionarContacto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Settar as variaveis JavaBeans
		contacto.setNome(request.getParameter("nome"));
		contacto.setFone(request.getParameter("fone"));
		contacto.setEmail(request.getParameter("email"));
		// Invocar o metodo inserirContacto passando o objecto contacto
		dao.inserirContacto(contacto);
		// Redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

	/**
	 * Listar contacto.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Editar contacto
	protected void listarContacto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// settar a variavel
		contacto.setIdcon(request.getParameter("idcon"));
		// Executar o metod selecionar contacto;
		dao.selecionarContacto(contacto);
		// settar os atributos do formulario com o conteudo JavaBeans
		request.setAttribute("idcon", contacto.getIdcon());
		request.setAttribute("nome", contacto.getNome());
		request.setAttribute("fone", contacto.getFone());
		request.setAttribute("email", contacto.getEmail());
		// Encaminhar ao docimento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	/**
	 * Editar contacto.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContacto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Settar as variaveis JavaBeans
		contacto.setIdcon(request.getParameter("idcon"));
		contacto.setNome(request.getParameter("nome"));
		contacto.setFone(request.getParameter("fone"));
		contacto.setEmail(request.getParameter("email"));
		// executar o medto alterContacto()
		dao.alterarContacto(contacto);
		// Redireciondo ao dcumento agenda.jsp com as actualizacoes feitas
		response.sendRedirect("main");
	}

	/**
	 * Remover contacto.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Remover contacto
	protected void removerContacto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// settar a variavel idcon JavaBeans
		contacto.setIdcon(request.getParameter("idcon"));
		// executar o medto deletarContacto;
		dao.deletarContacto(contacto);
		// Redireciondo ao dcumento agenda.jsp com as actualizacoes feitas
		response.sendRedirect("main");
	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Gerar relatorio
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			// tipo de conteudo
			response.setContentType("apllication/pdf");
			// nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contactos.pdf");
			// Criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// Abrir documento - Conteudo
			documento.open();
			documento.add(new Paragraph("Lista de Contactos:"));
			documento.add(new Paragraph(" "));
			// Criar uma tabela
			PdfPTable tabela = new PdfPTable(3);
			// Cabecalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// Popular a tabela com os contactos
			ArrayList<JavaBeans> lista = dao.listarContactos();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			documento.close();
		}
	}
}
