package Catalog.com;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.io.*;
import java.util.*;
import org.junit.Test;

import Catalog.com.Description;

public class DescriptionTest
{
    private ArrayList<String> t = new ArrayList<>();
	private Description model = new Description("hello", 2, 3000,t ,"stepa","path",4);

    @Test
    public void getModelPrice() {
        assertEquals(3000, model.getPrice()); 
    }
    @Test
    public void getModelfind() {
        assertEquals(true, model.find(4)); 
    }
    @Test
    public void getModelQ() {
        assertEquals(2, model.getQuintity()); 
    }

   
}