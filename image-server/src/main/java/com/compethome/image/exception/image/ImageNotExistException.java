package com.compethome.image.exception.image;


import com.compethome.image.exception.CustomException;
import com.compethome.image.exception.FailResponseMessage;
public class ImageNotExistException extends CustomException {
    public ImageNotExistException() {
        super(FailResponseMessage.IMAGE_NOT_EXIST.getCode(),
                FailResponseMessage.IMAGE_NOT_EXIST.getMessage());
    }
}
