package model;

/**
 * @author mateussilva
 *
 */
public class Usuario
{

    private String login;
    private String nome;
    private String senha;
    private String email;
    private int pontos;

    public Usuario()
    {
	super();
    }

    public String getLogin()
    {
	return login;
    }

    public void setLogin(String login)
    {
	this.login = login;
    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getSenha()
    {
	return senha;
    }

    public void setSenha(String senha)
    {
	this.senha = senha;
    }

    public String getEmail()
    {
	return email;
    }

    public void setEmail(String email)
    {
	this.email = email;
    }

    public int getPontos()
    {
	return pontos;
    }

    public void setPontos(int pontos)
    {
	this.pontos = pontos;
    }

    public void adicionarPontos(int ponto)
    {
	pontos = pontos + ponto;
    }

    @Override
    public String toString()
    {
	return "Usuario [login=" + login + ", nome=" + nome + ", senha=" + senha + ", email=" + email + ", pontos="
		+ pontos + "]";
    }

}
