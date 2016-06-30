package forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionForm;

public class PrestacionesForm extends ActionForm {

	private static final long serialVersionUID = 7352021000623040587L;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS z");
	
	private String titulo = null;
	private Date inicio = null;
	private Date fin = null;
	private long diferencia_tiempo = 0;

	public PrestacionesForm() {
		super();
		titulo = "Prestaciones";
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getInicio() {
		return formatoFecha.format(inicio);
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return formatoFecha.format(fin);
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public String getDiferencia_tiempo() {
		return String.valueOf(diferencia_tiempo);
	}

	public void setDiferencia_tiempo(long diferencia_tiempo) {
		this.diferencia_tiempo = diferencia_tiempo;
	}

}
