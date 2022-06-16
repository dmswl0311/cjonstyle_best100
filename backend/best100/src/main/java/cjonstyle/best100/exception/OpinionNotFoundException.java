package cjonstyle.best100.exception;

public class OpinionNotFoundException extends RuntimeException{
    public OpinionNotFoundException(){
        super();
    }
    public OpinionNotFoundException(String message){
        super(message);
    }
}
