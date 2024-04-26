package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/** Modulo de conexao. */
	// Parametro de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";

	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "Magente123";

	// Metodo de conexao

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}

	}

	/**
	 * CRUD CREATE.
	 *
	 * @param contacto the contacto
	 */
	public void inserirContacto(JavaBeans contacto) {
		String create = "insert into contactos (nome, fone, email) values (?,?,?)";
		try {
			// abrir a conexao com o DB
			Connection con = conectar();
			// Peparar a query para a execucao no Banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os paramentros (?) pelo conteudo das variaveis javaBeans
			pst.setString(1, contacto.getNome());
			pst.setString(2, contacto.getFone());
			pst.setString(3, contacto.getEmail());
			// Executar a query
			pst.executeUpdate();
			// Encerrar a conexao com o DB
			con.close();
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	/**
	 * CRUD READ.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContactos() {
		// Criando um objecto para acessar a class JavaBeans
		ArrayList<JavaBeans> contactos = new ArrayList<>();
		String read = "select * from contactos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// O loop abaixo sera executado enquanto houver contactos
			while (rs.next()) {
				// variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// Populando o array list / Armaznear tudo no vector dinamico
				contactos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contactos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 *  CRUDE UPDATE *.
	 *
	 * @param contacto the contacto
	 */
	// Selecionar o contacto
	public void selecionarContacto(JavaBeans contacto) {
		String read2 = "select * from contactos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contacto.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contacto.setIdcon(rs.getString(1));
				contacto.setNome(rs.getString(2));
				contacto.setFone(rs.getString(3));
				contacto.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println();
		}
	}

	/**
	 * Alterar contacto.
	 *
	 * @param contacto the contacto
	 */
	// editar o contacto
	public void alterarContacto(JavaBeans contacto) {
		String update = "update contactos set nome=?, fone=?, email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, contacto.getNome());
			pst.setString(2, contacto.getFone());
			pst.setString(3, contacto.getEmail());
			pst.setString(4, contacto.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contacto.
	 *
	 * @param contacto the contacto
	 */
	//CRUD DELETE
	public void deletarContacto(JavaBeans contacto) {
		String delete = "delete from contactos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contacto.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

}
