package com.mjc.school.service.exceptions;

public enum ServiceErrorCode {

    NEWS_ID_DOES_NOT_EXIST("000010", "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST("000020", "Author with id %d does not exist."),
    AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID("000021", "Author not found for news with id %d."),
    TAG_ID_DOES_NOT_EXIST("000030", "Tag with id %d does not exist."),
    TAG_DOES_NOT_EXIST_FOR_NEWS_ID("000031", "Tag not found for news with id %d."),
    COMMENT_ID_DOES_NOT_EXIST("000040", "Comment with id %d does not exist."),
    COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID("000041", "Comment not found for news with id %d."),
    NEWS_CONFLICT("000051", "News has a persistence conflict due to entity id existence."),
    AUTHOR_CONFLICT("000052", "Author has a persistence conflict due to entity id existence."),
    TAG_CONFLICT("000053", "Tag has a persistence conflict due to entity id existence."),
    COMMENT_CONFLICT("000054", "Comment has a persistence conflict due to entity id existence."),
    VALIDATION_ERROR("000060", "Validation error"),
    RESOURCE_NOT_FOUND("000070", ""),
    API_NOT_SUPPORTED("000080", "Api not supported for accept header"),
    NOT_SUPPORTED("000090", "Operation not supported"),
    CONFLICT("000100", "Conflict with authorization request. Username %s is already exist");

    private final String errorCode;
    private final String errorMessage;

    ServiceErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
