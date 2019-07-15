package ar.com.wolox.javameetup2019.helper;

public class PropertiesConstants {

    public static final String PROPERTY_DETAIL = "detail";
    public static final String PROPERTY_CODE = "code";
    public static final String PROPERTY_METHOD = "method";
    public static final String PROPERTY_PATH = "path";
    public static final String VALUE_CHARSET = "application/json; charset=utf-8";

    public static final String PROPERTY_DOCUMENT_NUMBER = "document_number";
    public static final String PROPERTY_DOCUMENT_TYPE = "document_type";
    public static final String VALUE_DOCUMENT_TYPE_DNI = "dni";
    public static final String VALUE_DOCUMENT_TYPE_CUIL = "cuil";
    public static final String PROPERTY_NAME = "name";

    public static final String VALUE_CAMEL_URI = "CamelHttpUri";
    public static final String VALUE_CAMEL_METHOD = "CamelHttpMethod";

    public static final String ERROR_CODE_INCORRECT_DOCUMENT = "01";
    public static final String ERROR_CODE_INCORRECT_DOCUMENT_TYPE = "02";
    public static final String ERROR_CODE_EMPTY_BODY = "03";

    public static final int DNI_LENGTH = 8;

    private PropertiesConstants(){
        // Private constructor to avoid instances, there are only static methods/attributes
    }

}
