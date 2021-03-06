package br.com.casadocodigo.loja.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.dao.AutorDao;
import br.com.casadocodigo.loja.dao.LivroDao;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

// CDI
// Named para indicar que será gerenciado pelo cdi e não pelo jsf
/**
 * 
 * @author romulo
 * @description classe controller de livros
 * @date 2017.05.01
 */
@Named
@RequestScoped
public class AdminLivrosBean {

	private Livro livro = new Livro();
	// CDI
	@Inject
	private LivroDao dao;
	@Inject
	private AutorDao autorDao;

	@Inject
	private FacesContext context;

	private List<Integer> autoresId = new ArrayList<>();


	@Transactional
	public String salvar() {
		for (Integer autorId : autoresId) {
			livro.getAutores().add(new Autor(autorId));
		}
		dao.salvar(livro);

		// ativando contexto flash, que dura 2 requests.
		context.getExternalContext().getFlash().setKeepMessages(true);
		;
		context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

		return "/livros/lista?faces-redirect=true";
	}

	@SuppressWarnings("unused")
	private void limparFormulario() {
		this.livro = new Livro();
		this.autoresId = new ArrayList<>();
	}

	public List<Autor> getAutores() {
		return autorDao.listar();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Integer> getAutoresId() {
		return autoresId;
	}

	public void setAutoresId(List<Integer> autoresId) {
		this.autoresId = autoresId;
	}
}
