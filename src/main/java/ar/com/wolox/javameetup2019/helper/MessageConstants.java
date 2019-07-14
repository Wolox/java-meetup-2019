package ar.com.wolox.javameetup2019.helper;

public class MessageConstants {

    public static final String INVALID_CUIL = "El cuil ingresado no es válido (se requieren 11 caracteres numéricos).";
    public static final String EMPTY_NAME = "El campo name no fue ingresado.";
    public static final String EMPTY_LASTNAME = "El campo last_name no fue ingresado.";
    public static final String EMPTY_DOCUMENT_TYPE = "El campo document_type no fue ingresado.";
    public static final String EMPTY_DOCUMENT_NUMBER = "El campo document_number no fue ingresado.";
    public static final String INCORRECT_DOCUMENT_TYPE = "El campo document_type es incorrecto. " +
            "El tipo sólo puede ser dni o cuil";
    public static final String INCORRECT_DNI = "El dni ingresado es inválido.";
    public static final String EXISTENT_DNI =  "El documento ingresado ya existe.";
    public static final String INCORRECT_CUIL = "El cuil ingresado es inválido.";
    public static final String NONEXISTENT_CLIENT = "No existe un cliente registrado con los datos proporcionados.";
    public static final String SAVED_CLIENT = "El cliente fue guardado con éxito.";

    private MessageConstants(){
        // Private constructor to avoid instances, there are only static methods/attributes
    }

}
