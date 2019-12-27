package Catalog.com;

import java.io.*;
import java.util.*;

public class Description {
	private int quantity;
	private ArrayList<String> tags;
	private String picture;
	private String decrip;
	private int price;
	private String nameOfMaker;
	private String picFile;
	private int id;

	public Description(String d, int q, int p, ArrayList<String> t, String n, String pic, int count) {
		quantity = q;
		tags = t;
		decrip = d;
		price = p;
		nameOfMaker = n;
		picFile = pic;
		id = count;
	}
	public String getName() {
		return nameOfMaker;
	}

	public String genAdvertisement(String name, String username,boolean isAuth) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class = 'product'>\n");
		sb.append("<a href='/website/product/" + id + "'>");
		sb.append("<img class =' productImg' src = '/website/images/"+ picFile +"'></a>\n");
		sb.append("<h1 class = 'productHeader'>" + name + "</h1>\n");
		if(isAuth)
			sb.append("<pre class = 'purchaseProduct' onclick = 'addToPurchase(\""+name+"\",\""+ username+"\",true)'></pre>");
		sb.append("<pre class = 'productPrice'>" + price + "</pre>");
		sb.append("</div>");
		return sb.toString();
	}

	public String genPage(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1 class ='pageLabel'>" + str + "</h1>\n");
		sb.append("<img class ='imgPage' src = '/website/images/"+ picFile +"'>\n");
		sb.append("<p class = 'productDecription'>" + decrip + "</p>\n");
		sb.append("<h2 class = 'productsLeft'>" + quantity + "</h2>\n");
		sb.append("<mark class = 'productPrice'>" + price + "</mark>\n");
		sb.append("<p id = 'author' style = 'display:none'>" + nameOfMaker + "</p>");
		return sb.toString();
	}


	public void changeQuantity(int num) {
		quantity = num;
	}
	public void changePrice(int num) {
		price = num;
	}
	public void changeDecrip(String str) {
		decrip = str;
	}
	
	public void changeTags(ArrayList<String> t) {
		tags = t;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(quantity + " /#/ ");
		for(int i = 0; i < tags.size(); i++)
			sb.append(tags.get(i) + " ");
		sb.append("/#/ ");
		sb.append(decrip + " /#/ ");
		sb.append(price+ " /#/ ");
		sb.append(nameOfMaker+" /#/ ");
		sb.append(picFile+" /#/ ");
		sb.append(id);
		return sb.toString();
	}
	public boolean find(int count) {
		return count == id;
	}
	public int getPrice() {
		return price;
	}
	public int getQuintity() {
		return quantity;
	}
}