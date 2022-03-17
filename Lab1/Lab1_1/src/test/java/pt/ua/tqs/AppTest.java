package pt.ua.tqs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    TqsStack<Integer> stack;

    @BeforeEach
    void setUp(){
        stack = new TqsStack<Integer>();
    }

    @AfterEach
    void release(){
        stack = null;
    }

    @Test
    @DisplayName("A stack is empty on construction")
    public void stackEmptyOnConstruction(){
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("A stack has size 0 on construction.")
    public void stackSize0OnConstruction(){
        assertEquals(stack.size(),0);
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    public void stackNotEmptyAfterPush(){
        int n = 5;
        for (int i = 0; i < n; i++){
            stack.push(1);
        }
        assertFalse(stack.isEmpty());
        assertEquals(stack.size(), n);
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x.")
    public void valuePushedEqualsStackPop(){
        int x = 3;
        stack.push(x);
        assertEquals(stack.pop(), x);
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    public void valuePushedEqualsStackPeek(){
        int x = 6;
        stack.push(x);
        int initialSize = stack.size();
        assertEquals(stack.peek(), x);
        assertEquals(stack.size(), initialSize);
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    public void stackOfSizeNEmptyAfterNPops(){
        for (int i = 0; i < 7; i++){
            stack.push(i);
        }
        int n = stack.size();
        for (int i = 0; i < n; i++){
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(stack.size(), 0);
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    public void popEmptyStackThrowsException(){
        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    public void peekEmptyStackThrowsException(){
        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    @DisplayName("For bounded stacks only:pushing onto a full stack does throw an IllegalStateException")
    public void pushFulBoundedStackThrowsException(){
        int size = 5;
        BoundedStack<Integer> bStack = new BoundedStack<Integer>(size);
        for (int i = 0; i < size; i++){
            bStack.push(i);
        }
        System.out.println(bStack.size());
        assertEquals(bStack.size(), size);
        assertThrows(IllegalStateException.class, () -> bStack.push(3));
    }
}
