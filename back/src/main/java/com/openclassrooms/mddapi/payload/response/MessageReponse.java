package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A response class indicating the result of an operation.
 * This class is used for sending success or error messages back to the client.
 */
@Data
@AllArgsConstructor
public class MessageReponse {

    /**
     * The message content.
     */
    private String message;
    
}
