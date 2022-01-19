package com.individual.demonstration.error;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * Represents exception while calling service provider
 */
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class ServiceProviderException extends RuntimeException{

	public ServiceProviderException(String message) {
		super(message);
	}
}
