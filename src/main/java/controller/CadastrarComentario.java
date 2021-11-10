package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comentario;
import model.Usuario;
import services.ComentarioService;
import services.UsuarioService;

/**
 * Servlet implementation class CadastrarComentario
 */
/**
 * @author mateussilva
 *
 */
@WebServlet("/CadastrarComentario")
public class CadastrarComentario extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private ComentarioService comentarioService = new ComentarioService();
    private UsuarioService usuarioService = new UsuarioService();

    /**
     * Recebe a requisi��o com dados do coment�rio, cadastra coment�rio e
     * redireciona para controller ExibirTopico
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

	req.setCharacterEncoding("utf-8");
	resp.setCharacterEncoding("utf-8");

	int idTopico = 0;

	try
	{
	    Usuario usuario = ((Usuario) req.getSession().getAttribute("usuarioLogado"));
	    idTopico = Integer.parseInt(req.getParameter("idTopico"));
	    String conteudo = req.getParameter("textoComentario");

	    /* Adiciona o coment�rio ao banco de dados */
	    Comentario comentario = new Comentario();
	    comentario.setConteudo(conteudo);
	    comentario.setNumeroTopico(idTopico);
	    comentarioService.cadastrarComentario(comentario, usuario.getLogin());

	    /* Adiciona os pontos e atualiza */
	    usuario.adicionarPontos(3);
	    usuarioService.atualizarPontos(usuario);

	    /* Atualiza o usu�rio da sess�o */
	    req.getSession().setAttribute("usuarioLogado", null);
	    req.getSession().setAttribute("usuarioLogado", usuario);

	    resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/ExibirTopico?topicoID=" + idTopico));

	} catch (Exception e)
	{
	    resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/ExibirTopico?topicoID=" + idTopico
		    + "&comentarioResult=" + e.getLocalizedMessage()));

	}
    }

}
