package ar.com.wolox.javameetup2019.helper;


import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import org.apache.commons.lang3.math.NumberUtils;

public class ValidationUtils {

	private ValidationUtils() {
		// Private constructor to avoid instances, there are only static methods/attributes
	}

	/**
	 * Cuil validation method
	 * @param cuil
	 * @return Boolean
	 * @throws InvalidInputException
	 */
	public static boolean validateCuil(String cuil) throws InvalidInputException {

		if (cuil.length() != 11 || !NumberUtils.isCreatable(cuil)){
			throw new InvalidInputException(MessagesConstants.INVALID_CUIL);
		}

		String[] cuitArray = cuil.split("");

		Integer[] serie = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

		Integer aux = 0;

		for (int i = 0; i < 10; i++) {
			aux += Integer.valueOf(cuitArray[i]) * serie[i];
		}

		aux = 11 - (aux % 11);
		if (aux == 11) {
			aux = 0;
		} else if (aux == 10) {
			aux = 9;
		}
		return (Integer.valueOf(cuitArray[10]) == aux);
	}


	public static boolean isEmptyString(String stringToValidate){
		return (stringToValidate == null || stringToValidate.isEmpty());
	}

}

