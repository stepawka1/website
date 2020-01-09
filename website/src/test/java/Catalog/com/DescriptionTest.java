package Catalog.com;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.io.*;
import java.util.*;
import org.junit.Test;

import Catalog.com.Description;
import Catalog.com.Purchase;
public class ModelsTest
{
    private ArrayList<String> t = new ArrayList<>();
	private Description model = new Description("hello", 2, 3000,t ,"stepa","path",4);
	private Purchase p = new Purchase("a",21000,5);

    @Test
    public void getMDescriptionPrice() {
        assertEquals(3000, model.getPrice()); 
    }
    @Test
    public void getDescriptionfind() {
        assertEquals(true, model.find(4));
    }
    @Test
    public void getDescriptionQ() {
        assertEquals(2, model.getQuintity()); 
    }
    @Test
    public void getPurchaseFind(){ assertEquals(true, p.find("a"));}
    @Test
    public void getPurchaseGetQ(){ assertEquals(5, p.getQuintity());}
    @Test
    public void getPurchaseGetName(){ assertEquals("a", p.getName());}
   
}
