package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UsuarioTO;
import model.Usuario;

/**
 * @author mateussilva
 *
 */
public class UsuarioDAO implements IUsuarioDAO
{

    @Override
    public void cadastrarUsuario(Usuario u) throws Exception
    {
	try (Connection c = ConnectionFactory.getConnection())
	{

	    String sql = "INSERT INTO usuario(login,email,nome,senha,pontos) VALUES((?),(?),(?),(?),(?))";

	    PreparedStatement ps = c.prepareStatement(sql);

	    ps.setString(1, u.getLogin());
	    ps.setString(2, u.getEmail());
	    ps.setString(3, u.getNome());
	    ps.setString(4, u.getSenha());
	    ps.setInt(5, u.getPontos());

	    if (ps.executeUpdate() == 0)
	    {
		throw new Exception("N�o foi poss�vel cadastrar usu�rio!");
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}
    }

    @Override
    public Usuario consultarUsuario(String login) throws Exception
    {
	Usuario usuario = null;

	try (Connection c = ConnectionFactory.getConnection())
	{

	    String sql = "SELECT * FROM usuario WHERE login = (?)";
	    PreparedStatement ps = c.prepareStatement(sql);

	    ps.setString(1, login);
	    ResultSet result = ps.executeQuery();

	    if (result.next())
	    {
		usuario = new Usuario();
		usuario.setEmail(result.getString("email"));
		usuario.setLogin(result.getString("login"));
		usuario.setNome(result.getString("nome"));
		usuario.setSenha(result.getString("senha"));
		usuario.setPontos(result.getInt("pontos"));
	    } else
	    {
		throw new Exception("N�o foi poss�vel recuperar o usu�rio! ");
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}

	return usuario;
    }

    public Usuario realizarLogin(String login, String senha) throws Exception
    {
	Usuario usuario = null;

	try (Connection c = ConnectionFactory.getConnection())
	{

	    String sql = "SELECT * FROM usuario WHERE login = (?) AND senha = (?)";
	    PreparedStatement ps = c.prepareStatement(sql);

	    ps.setString(1, login);
	    ps.setString(2, senha);

	    ResultSet result = ps.executeQuery();

	    if (result.next())
	    {
		usuario = new Usuario();
		usuario.setEmail(result.getString("email"));
		usuario.setLogin(result.getString("login"));
		usuario.setNome(result.getString("nome"));
		usuario.setSenha(result.getString("senha"));
		usuario.setPontos(result.getInt("pontos"));
	    } else
	    {
		throw new Exception("Usu�rio n�o existe, tente com outro! ");
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}

	return usuario;
    }

    public List<UsuarioTO> listarRanking() throws Exception
    {
	List<UsuarioTO> usuarios = new ArrayList<>();

	String sql = "SELECT login,nome,pontos FROM usuario ORDER BY PONTOS DESC";

	try (Connection c = ConnectionFactory.getConnection())
	{

	    PreparedStatement ps = c.prepareStatement(sql);
	    ResultSet result = ps.executeQuery();

	    int colocao = 1;
	    while (result.next())
	    {
		UsuarioTO usuario = new UsuarioTO();
		usuario.setColocao(colocao);
		usuario.setLogin(result.getString("login"));
		usuario.setNome(result.getString("nome"));
		usuario.setPontos(result.getInt("pontos"));
		usuarios.add(usuario);
		colocao++;
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}

	return usuarios;
    }

    public void atualizarPontos(Usuario usuario) throws Exception
    {
	String sql = "UPDATE usuario SET pontos = (?) WHERE login = (?)";

	try (Connection c = ConnectionFactory.getConnection())
	{

	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setInt(1, usuario.getPontos());
	    ps.setString(2, usuario.getLogin());

	    if (ps.executeUpdate() == 0)
	    {
		throw new Exception("N�o foi poss�vel atualizar os pontos!");
	    }

	} catch (SQLException e)
	{
	    throw new Exception("Ocorreu um erro interno!", e);
	}

    }

}
