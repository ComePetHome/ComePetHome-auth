package com.compethome.image.exception.image;


import com.compethome.image.exception.CustomException;
import com.compethome.image.exception.FailResponseMessage;

public class ImageUploadFailedException extends CustomException {
    public ImageUploadFailedException() {
        super(FailResponseMessage.IMAGE_UPLOAD_FAILED.getCode(),
                FailResponseMessage.IMAGE_UPLOAD_FAILED.getMessage());
    }
}
