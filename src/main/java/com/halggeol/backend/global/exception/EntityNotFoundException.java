package com.halggeol.backend.global.exception;

public class EntityNotFoundException extends BusinessException {
    
    public EntityNotFoundException(String message) {
        super(ErrorCode.ENTITY_NOT_FOUND, message);
    }
    
    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}