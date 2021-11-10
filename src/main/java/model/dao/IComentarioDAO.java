package model.dao;

import java.util.List;

import dto.ComentarioTO;
import model.Comentario;

/**
 * @author mateussilva
 *
 */
public interface IComentarioDAO
{
    List<ComentarioTO> getListaComentarios(int numeroTopico) throws Exception;

    void cadastrarComentario(Comentario comentario, String login) throws Exception;
}
