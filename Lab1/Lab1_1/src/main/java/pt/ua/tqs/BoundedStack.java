package pt.ua.tqs;

public class BoundedStack<T> extends TqsStack <T>{
    
    private int limit;

    public BoundedStack(int limit){
        super();
        this.limit = limit;
    }

    @Override
    public void push(T object) {
        if(this.size() == this.limit){
            throw new IllegalStateException();
        }else{
            super.push(object);
        }
    }
}
