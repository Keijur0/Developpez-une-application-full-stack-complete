package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A response class indicating the result of an operation.
 * This class is used for sending success or error messages back to the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReponse {

    /**
     * The message content.
     */
    private String message;

}
