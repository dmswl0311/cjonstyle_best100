package cjonstyle.best100.exception;

public class OpinionPasswordNotMatchException extends RuntimeException{
    public OpinionPasswordNotMatchException(){
        super();
    }
    public OpinionPasswordNotMatchException(String message){
        super(message);
    }
}
