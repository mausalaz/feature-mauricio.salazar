package com.desafio.fechas.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.desafio.fechas.constant.Constantes;
import com.desafio.fechas.model.Periodo;

@Controller
public class ApiFechasController {

	final static Logger logger = Logger.getLogger(ApiFechasController.class);
	
	@Value("${url.rest.gdd}") 
	String urlRestGDD;

  @RequestMapping(method = RequestMethod.GET, value="/fechasFaltantes/fechas")
  @ResponseBody
  public Periodo getFechasFaltantes() {
	  
	  //TODO VALIDAR SI SERVICIO NO RESPONDE
	  //TODO hacer junit test
	  Periodo datosGdd = callApi();
	  List<LocalDate> fechasFaltantes = new ArrayList<LocalDate>();
	  
	  Periodo response = new Periodo();
	  response.setFechaCreacion( datosGdd.getFechaCreacion() );
	  response.setFechaFin( datosGdd.getFechaFin() );
	  response.setFechas( datosGdd.getFechas() );
	  
	  	int mes = 0;
		int anio = 0;
	  if ( !datosGdd.getFechaCreacion().equals( datosGdd.getFechas().get( 0 ) ) ) {
		  LocalDate fechaActual = datosGdd.getFechaCreacion();
		  LocalDate fechaSiguiente = datosGdd.getFechas().get( 0 );
		  mes = fechaActual.getMonthValue() + 1;
		  anio = fechaActual.getYear();
		  LocalDate aux = null;
		  
		  if ( fechaActual.getMonthValue() == 12 ) {
			  mes = 1;
			  anio++;
		  	}
		  
		  do {   
	        	if (mes == Constantes.NUMBER_12 && anio != fechaSiguiente.getYear()) {
	        		aux = LocalDate.of( anio, mes , Constantes.NUMBER_1);
	        		fechasFaltantes.add( aux );
	        		anio++;
	        		mes = Constantes.NUMBER_1;
	        		aux = LocalDate.of( anio, mes , Constantes.NUMBER_1);
	        		fechasFaltantes.add( aux );
				}else {
					aux = LocalDate.of( anio, mes , Constantes.NUMBER_1);
					if (!fechaSiguiente.equals(aux)) {
						fechasFaltantes.add( aux );
					}
				}
	        	
	        	mes++;
	        } while ( !fechaSiguiente.equals(aux) );  
	  }
	  
	  
	  	 mes = 0;
		 anio = 0;
	  for (int i = 0; i < datosGdd.getFechas().size(); i++) {
		  
		  if ( i+1 == datosGdd.getFechas().size() ) {//ultimo registro
				continue;
		}
		  
		  if ( i == 0 ) {
			  fechasFaltantes.add( datosGdd.getFechas().get( 0 ) );
		}
		  
		  LocalDate fechaActual = datosGdd.getFechas().get( i );
		  LocalDate fechaSiguiente = datosGdd.getFechas().get( i +1 );
		  
		  if ( fechaSiguiente.getYear() == fechaActual.getYear() && fechaSiguiente.getMonthValue() == fechaActual.getMonthValue() +1 ) {
			  fechasFaltantes.add( fechaSiguiente );
			  continue;
		  }
		  
		  mes = fechaActual.getMonthValue() + 1;
		  anio = fechaActual.getYear();
		  LocalDate aux = null;
			
		  if ( fechaActual.getMonthValue() == 12 ) {
			  mes = 1;
			  anio++;
		  	}
				
		        do {   
		        	if (mes == Constantes.NUMBER_12 && anio != fechaSiguiente.getYear()) {
		        		aux = LocalDate.of( anio, mes , Constantes.NUMBER_1);
		        		fechasFaltantes.add( aux );
		        		anio++;
		        		mes = Constantes.NUMBER_1;
		        		aux = LocalDate.of( anio, mes , Constantes.NUMBER_1);
		        		fechasFaltantes.add( aux );
					}else {
						aux = LocalDate.of( anio, mes , Constantes.NUMBER_1);
						fechasFaltantes.add( aux );
					}
		        	
		        	mes++;
		        } while ( !fechaSiguiente.equals(aux) );  
	}
	  
	  response.setFechasFaltantes( fechasFaltantes );
	  
	  return response;
  }
  
  /**
   * servicio que consume REST GDD
   * @return
   */
	private Periodo callApi() {
		//TODO cambiar a package y clase propia, e implementar HTTP errors
	    RestTemplate restTemplate = new RestTemplate();
	    Periodo response = restTemplate.getForObject( urlRestGDD, Periodo.class);
	    
	    System.out.println("==== RESTful API GDD START=======");
	    System.out.println(response.toString());
	    System.out.println("==== RESTful API GDD END =======");
	    
	    return response;
	  }
}
