package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ComentarioTO;
import model.Comentario;

/**
 * @author mateussilva
 *
 */
public class ComentarioDAO implements IComentarioDAO
{

    @Override
    public List<ComentarioTO> getListaComentarios(int numeroTopico) throws Exception
    {
	List<ComentarioTO> comentarios = new ArrayList<>();

	try (Connection c = ConnectionFactory.getConnection())
	{

	    String sql = "SELECT u.nome, c.conteudoC from comentarios c inner join usuario u on "
		    + "c.loginC = u.login where c.topicoC = (?) order by c.comentario ASC";

	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setInt(1, numeroTopico);

	    ResultSet result = ps.executeQuery();

	    while (result.next())
	    {
		ComentarioTO comentario = new ComentarioTO();
		comentario.setConteudo(result.getString("c.conteudoC"));
		comentario.setNomeCriador(result.getString("u.nome"));
		comentarios.add(comentario);
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}

	return comentarios;
    }

    @Override
    public void cadastrarComentario(Comentario comentario, String login) throws Exception
    {
	try (Connection c = ConnectionFactory.getConnection())
	{

	    String sql = "INSERT INTO comentarios(conteudoC,topicoC,loginC) VALUES ((?),(?),(?))";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, comentario.getConteudo());
	    ps.setInt(2, comentario.getNumeroTopico());
	    ps.setString(3, login);

	    if (ps.executeUpdate() == 0)
	    {
		throw new Exception("N�o foi poss�vel fazer o coment�rio");
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}
    }

}
