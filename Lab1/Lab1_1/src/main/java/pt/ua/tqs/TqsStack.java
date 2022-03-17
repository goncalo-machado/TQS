package pt.ua.tqs;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    
    private ArrayList<T> stack;

    public TqsStack(){
        stack = new ArrayList<T>();
    }

    public void push(T object){
        stack.add(object);
    }

    public T pop(){
        if(stack.size() == 0){
            throw new NoSuchElementException();
        }else{
            return stack.remove(stack.size() -1);
        }
    }

    public T peek(){
        if(stack.size() == 0){
            throw new NoSuchElementException();
        }else{
            return stack.get(stack.size()-1);
        }
    }

    public int size(){
        return stack.size();
    }

    public boolean isEmpty(){
        return stack.size() == 0;
    }
}
