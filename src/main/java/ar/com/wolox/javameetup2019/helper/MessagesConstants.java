package ar.com.wolox.javameetup2019.helper;

public class MessagesConstants {

    public static final String EMPTY_BODY = "The message body is empty. " +
            "Please, fill the necessary fields for creating the client.";
    public static final String EMPTY_NAME = "The field name is empty.";
    public static final String EMPTY_LASTNAME = "The field last_name is empty.";
    public static final String EMPTY_DOCUMENT_TYPE = "The field document_type is empty.";
    public static final String EMPTY_DOCUMENT_NUMBER = "The field document_number is empty.";
    public static final String EXISTENT_DNI =  "A client with this document already exists.";
    public static final String INCORRECT_DOCUMENT_TYPE = "The field document_type is wrong. " +
            "It only can be dni or cuil.";
    public static final String INCORRECT_DNI = "The dni is not valid.";
    public static final String INCORRECT_CUIL = "The cuil is not valid.";
    public static final String INVALID_CUIL = "The cuil is not valid (it has to be a number with 11 digits).";
    public static final String NONEXISTENT_CLIENT = "There is no client found with the given information.";
    public static final String SAVED_CLIENT = "The client was correctly created.";

    public static final String RESPONSE_OK = "Successful";
    public static final String RESPONSE_INTERNAL_ERROR = "Internal Server error";
    public static final String RESPONSE_BAD_REQUEST = "Bad request";

    private MessagesConstants(){
        // Private constructor to avoid instances, there are only static methods/attributes
    }

}
