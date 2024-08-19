package me.pauloo27.common.utils;

public class AppException extends RuntimeException {
    private String title;

    public AppException(String title, String message) {
        super(message);
    }

    public String getTitle() {
        return this.title;
    }
}
