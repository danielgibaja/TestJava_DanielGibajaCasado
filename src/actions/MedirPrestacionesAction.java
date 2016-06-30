package actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import forms.PrestacionesForm;
import utils.Ficheros;

public class MedirPrestacionesAction extends Action {

	private long tiempo_inicio = 0, tiempo_fin = 0;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		((PrestacionesForm)form).setInicio(new Date());
		tiempo_inicio = System.currentTimeMillis();
		
		Ficheros.realizarProceso(request.getRealPath("WEB-INF"+Ficheros.NOMBRE_FICHERO_ALEATORIO), 
								 request.getRealPath("WEB-INF"+Ficheros.NOMBRE_FICHERO_ZIP), 
								 Ficheros.NUM_LINEAS, 
								 Ficheros.NUM_PALABRAS_POR_LINEA);
		
		((PrestacionesForm)form).setFin(new Date());
		tiempo_fin = System.currentTimeMillis();
		((PrestacionesForm)form).setDiferencia_tiempo(tiempo_fin - tiempo_inicio);
		
		return mapping.findForward("prestaciones");
	}
}
