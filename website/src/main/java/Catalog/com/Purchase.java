package Catalog.com;

public class Purchase {
	private String product;
	private int price;
	private int quintity;

	public Purchase (String str, int p, int q) {
		product = str;
		price = p;
		quintity = q;
	}

	public String gen(int count, String login) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li class = '" + product + "'>");
		sb.append("<b class = 'purchaseProductName'>"+product +"</b>");
		sb.append("<span class = 'purchaseQ'> <button onclick ='subtractProd("+count +",\"" + product +"\",\"" + login +"\")'>-</button> ");
		sb.append("<input type = 'text' pattern = '^[ 0-9]+$' class = 'purchaseProductQuintity' value = '"+ quintity +"'");
		sb.append("onchange='checkQuiantity(\""+product+"\")'>");
		sb.append(" <button onclick ='addProd("+count+",\"" + product + "\",\"" + login + "\")'>+</button> </span> ");
		sb.append(" <b class = 'purchaseProductPrice'>" + price + "</b>");
		sb.append("<b class = 'purchaseTotalPrice'> </b>");
		sb.append("</li>");
		return sb.toString();
	}

	public void changeQuintity(int nq) {
		quintity += nq;
	}

	public String getName(){
		return product;
	}

	public int getQuintity() {
		return quintity;
	}

	public boolean find(String str) {
		return product.equals(str);
	}
}