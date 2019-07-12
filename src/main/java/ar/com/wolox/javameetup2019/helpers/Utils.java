package ar.com.wolox.javameetup2019.helpers;


import ar.com.wolox.javameetup2019.exceptions.InvalidInputException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	public Utils() {
		//Utils
	}

	public static boolean validateCuil(String cuil) throws InvalidInputException {

		if (cuil.length() != 11 || !NumberUtils.isCreatable(cuil)){
			throw new InvalidInputException("El cuil ingresado no es valido (se requieren 11 caracteres numericos).");
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
		return Integer.valueOf(cuitArray[10]) == aux;

	}

}

