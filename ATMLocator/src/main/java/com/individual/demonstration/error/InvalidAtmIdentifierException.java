package com.individual.demonstration.error;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * A custom exception for invalid ATM identifier input
 */
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class InvalidAtmIdentifierException extends RuntimeException{

	public InvalidAtmIdentifierException(String message) {
		super(message);
	}
}
