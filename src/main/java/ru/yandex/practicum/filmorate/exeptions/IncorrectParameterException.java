package ru.yandex.practicum.filmorate.exeptions;


public class IncorrectParameterException extends RuntimeException {
    public String getParameter() {
        return parameter;
    }

    private final String parameter;

    public IncorrectParameterException(String parameter) {
        this.parameter = parameter;
    }


}
