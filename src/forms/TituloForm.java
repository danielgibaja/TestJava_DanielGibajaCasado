package forms;

import org.apache.struts.action.ActionForm;

public class TituloForm extends ActionForm {

	private static final long serialVersionUID = 7352021000623040587L;
	
	private String titulo = null;

	public TituloForm() {
		super();
		titulo = "TestJava_DanielGibajaCasado";
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
