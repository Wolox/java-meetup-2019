package ar.com.wolox.javameetup2019.helpers;

public class ErrorConstants {

	public static final int INVALID_INPUT_CODE = -1;
	public static final String INVALID_INPUT_DETAIL = "Incorrect body format.";
	public static final int MISSING_HEADER_CODE = -2;
	public static final String MISSING_HEADER_DETAIL =
			"Missing header: convert (boolean, determines "
					+ "whether to convert the result in jerigonza)";

	public static final int SERVER_ERROR_CODE = -3;
	public static final String SERVER_ERROR_DETAIL = "The server is not responding correctly";

	private ErrorConstants() {

	}

}
