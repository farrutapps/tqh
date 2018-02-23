package com.farrutaps.tqhapp.client;

/**
 * Created by Sònia Batllori on 23/02/2018.
 */
public enum HTTP_1_0_Response {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    MOVED_PERMANENTLY(301),
    MOVED_TEMPORARILY(302),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEAWAY(502),
    SERVICE_UNAVAILABLE(503);

    private int responseCode;
    HTTP_1_0_Response(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public static HTTP_1_0_Response getResponseFromCode(int responseCode) {
        for(HTTP_1_0_Response response : HTTP_1_0_Response.values()) {
            if(response.getResponseCode() == responseCode)
                return response;
        }
        return null;
    }

    public String getResponseMessage() {
        String result = "";
        String[] words = this.name().split("_");
        for(String word : words){
            word = word.charAt(0) + word.substring(1).toLowerCase();
            result += word + " ";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }
}
