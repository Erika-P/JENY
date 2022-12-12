package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

public class BllException extends Exception{
    private static final long serialVersionUID = 1L;
    private List<Integer> listErrorCodes;
    
    public BllException(){
        super();
        this.listErrorCodes=new ArrayList<>();
    }
    public BllException(String message){
        super(message);
    }
    public BllException(String message, Throwable exception){
        super(message, exception);
    }
    @Override
    public String getMessage(){
        StringBuffer sb = new StringBuffer("Couche BLL - ");
        sb.append(super.getMessage());
        return sb.toString();
    }
    public void addError(int code)
    {
        if(!this.listErrorCodes.contains(code))
        {
            this.listErrorCodes.add(code);
        }
    }

    public boolean hasErrors()
    {
        return this.listErrorCodes.size()>0;
    }

    public List<Integer> getListErrorCodes()
    {
        return this.listErrorCodes;
    }
}
