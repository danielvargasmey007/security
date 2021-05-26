package co.com.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * The Class BaseController.
 * @author AVARGAS
 */
@RestControllerAdvice
public abstract class BaseController {

	/** The creado correctamente. */
	protected static String SUCCESS = "Request executed successfully.";

	/** The error guardar general. */
	protected static String ERROR = "Error executing the request.";
	

	/**
	 * Error 500.
	 *
	 * @param e the e
	 * @return the respuesta
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> gestionarError500(Throwable e) {
  return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
