package com.individual.demonstration.error;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Consists global exception handlers
 */
@ControllerAdvice
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = InvalidAtmIdentifierException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> invalidAtmIdentifierExceptionHandler(final InvalidAtmIdentifierException exception) {
		this.logger.error(exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ServiceProviderException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public ResponseEntity<String> serviceProviderExceptionHandler(final ServiceProviderException exception) {
		this.logger.error(exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
	}

	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> entityNotFoundExceptionHandler(final EntityNotFoundException exception) {
		this.logger.error(exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> unknownExceptionHandler(final RuntimeException exception) {
		this.logger.error(exception.getMessage());
		return new ResponseEntity<>("Unknown error while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
