package com.compethome.image.exception.image;


import com.compethome.image.exception.CustomException;
import com.compethome.image.exception.FailResponseMessage;

public class ImageFileNameWrongException extends CustomException {
    public ImageFileNameWrongException() {
        super(FailResponseMessage.IMAGE_FILE_NAME_WRONG.getCode(),
                FailResponseMessage.IMAGE_FILE_NAME_WRONG.getMessage());
    }
}
