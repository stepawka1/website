package Catalog;

import Catalog.com.Description;
import Catalog.com.Purchase;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import javax.servlet.annotation.MultipartConfig;
//javac -cp ..\..\..\..\lib\servlet-api.jar Login.java -d ..\..\WEB-INF\classes

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class Catalog extends HttpServlet {

    private HashMap<String, Description> desk = new HashMap<>();
    private HashMap<String, ArrayList<Purchase>> purchase = new HashMap<>();

    public void init(ServletConfig config) {
        getDesk("ad.txt");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String uri = request.getRequestURI();  
        if (uri.equals("/website/catalog/logout")) {
            HttpSession ses = request.getSession();
            ses.invalidate();
            response.sendRedirect("/website/catalog");
        } else if (uri.equals("/website/addproduct")) {
            HttpSession session = request.getSession();
            if(session.getAttribute("isCreator").equals("true"))
                request.getRequestDispatcher("/files/addPost.jsp").forward(request,response);
            else
                response.sendRedirect("/website/catalog");

        } else if(uri.equals("/website/purchase")) {
            request.getRequestDispatcher("/files/purchase.jsp").forward(request,response);
        } else if(uri.contains("/website/product/")) {
            request.getRequestDispatcher("/files/product.jsp").forward(request,response);
        } else 
            request.getRequestDispatcher("/files/catalog.jsp").forward(request,response);
    }  

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.equals("/website/catalog")) {
            HttpSession session = request.getSession();
            if(session.getAttribute("username") != null) {
                response.getWriter().write(genCatalog((String)session.getAttribute("username"),true));
            }
            else 
                response.getWriter().write(genCatalog(null,false));
            
        } else if(uri.contains("/website/product/")) {
            String[] tmp = uri.split("/");
            int id = Integer.parseInt(tmp[tmp.length-1]);
            response.getWriter().write(genPageForProduct(id));
        }  else if (uri.equals("/website/buyProducts")) {
            buyproducts(request,response);


        } else if (uri.equals("/website/purchase")) { // добавление товара в коризну 
           
            purchase(request, response);

        } else if (uri.equals("/website/getQuintity")) {
            response.getWriter().write(desk.get(request.getParameter("name")).getQuintity()+"");

        } else if(uri.equals("/website/search")) {
            HttpSession session = request.getSession();
            if(session.getAttribute("username") != null) {
                response.getWriter().write(genSearch(request.getParameter("arg"), (String)session.getAttribute("username"),true));
            }
            else 
                response.getWriter().write(genSearch(request.getParameter("arg"), (String)session.getAttribute("username"),false));
            

        } else if(uri.equals("/website/addproduct")) {
            addpost(request,response);
        } else if(uri.equals("/website/catalog/change")) { // меняю пост
            Description tochange = desk.get(request.getParameter("prevName"));

            if(!request.getParameter("price").equals("null"))
                //tochange.changePrice(Integer.parseInt(request.getParameter("price")));
            if(!request.getParameter("quiantity").equals("null"))
                //tochange.changeQuantity(Integer.parseInt(request.getParameter("quiantity")));

            if(!request.getParameter("decript").equals("null"))
                tochange.changeDecrip((String)request.getParameter("decript"));
            desk.put("prevName",tochange);


        } 
        
    }

    public synchronized void addAd(String name, String des, int quanitity, int price, ArrayList<String> tags, String login, String picFile) {
        try {
            Description tmp = new Description(des, quanitity, price, tags, login, picFile, desk.size() + 1);
            desk.put(name, tmp);
            File f1 = new File("ad.txt");
            PrintWriter fw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f1,true), "UTF-8"));
            fw.write(name + " /#/ " + tmp.toString());
            fw.write("\n");
            fw.close();
        } catch(IOException e) {

        }
        
    }

    public String genCatalog(String username, boolean isAuth) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Description> tmp  : desk.entrySet()) {
            String name = tmp.getKey();
            Description a = tmp.getValue();
                sb.append(a.genAdvertisement(name,username, isAuth));
        }
        return sb.toString();
    }

    public String genSearch(String s, String username,boolean isAuth) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Description> tmp  : desk.entrySet()) {
            String name = tmp.getKey();
            if(name.contains(s)){
                Description a = tmp.getValue();
                sb.append(a.genAdvertisement(name,username, isAuth));
            }
        }
        return sb.toString();
    }


    public String genPageForProduct(int id) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,Description> tmp : desk.entrySet()) {
            if(tmp.getValue().find(id)){
                return tmp.getValue().genPage(tmp.getKey());
            }
        }
        return "page not found";
    }


    private synchronized void getDesk(String filename){
        try {
  
            StringBuilder sb = new StringBuilder();
            Scanner scan = new Scanner(new InputStreamReader(new FileInputStream("ad.txt"), "UTF8"));
            while (scan.hasNext()) {
                String tmp = scan.nextLine();
                sb.append(tmp);
                sb.append("\n");
            }
            String[] strs = sb.toString().split("\n");  
            for (int i = 0; i < strs.length; i++) {
                String[] tmp = strs[i].split(" /#/ ");
                ArrayList<String> tags = new ArrayList<>();
                String[] tmpTag = tmp[2].split(" ");
                for(int j = 0; j < tmpTag.length; j++)
                    tags.add(tmpTag[j]);
                desk.put(tmp[0], new Description(tmp[3], Integer.parseInt(tmp[1]), Integer.parseInt(tmp[4]), tags, tmp[5], tmp[6], Integer.parseInt(tmp[7])) );
            }

        } catch(Exception e){}

    }



    public synchronized String genPurchase(String name, String login) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul class = 'purchasePage'>");   
        ArrayList<Purchase> tmp = purchase.get(name);
        for(int i = 0; i < tmp.size(); i++) {
            sb.append(tmp.get(i).gen(i, login));
        }
        sb.append("</ul>");
        sb.append("<b class = 'purchaseTotal'></b><br>");
        sb.append("<button class = 'purchaseBuyButton' onclick = 'buyProducts(\""+name+"\")'> </button>");
        return sb.toString();

    }
    public synchronized void writeToFile() {
        try {
            File f1 = new File("ad.txt");
            PrintWriter fw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f1,false), "UTF-8"));
            for (Map.Entry<String, Description> tmp  : desk.entrySet()) {
                String name = tmp.getKey();
                Description a = tmp.getValue();
                    fw.write(name + " /#/ " + a.toString());
                fw.write("\n");
            }
            fw.close();
        } catch(IOException e) {

        }
    }
    private void purchase(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            if((request.getParameter("login") != null && ! purchase.containsKey(request.getParameter("login")))) {
               purchase.put(request.getParameter("login"), new ArrayList<Purchase>());
            }
            if(request.getParameter("name") != null) {
                String name = request.getParameter("name");
                int price = desk.get(name).getPrice();
                int quantity = Integer.parseInt(request.getParameter("quiantity"));
                ArrayList<Purchase> tmp;
                if(purchase.get(request.getParameter("login")) == null) {
                    tmp = purchase.get(request.getParameter("login"));
                    tmp.add(new Purchase(name, price, quantity));
                    //purchase.put(request.getParameter("login"),tmp);
                } else {
                    tmp = purchase.get(request.getParameter("login"));
                    boolean isFound = false;//ести ли уже товар в корзине
                    if(tmp.size() > 0 ){

                        for(Purchase t : tmp) {
                            if(t.find(name)){                        
                                t.changeQuintity(quantity);
                                isFound = true;
                                if(t.getQuintity() == 0) {

                                    for(Purchase rab : purchase.get(request.getParameter("login"))) 
                                        if(rab.find(name))
                                            purchase.get(request.getParameter("login")).remove(rab);
                                }
                            }
                        }
                    }
                    if(!isFound) {
                        tmp.add(new Purchase(name,price,quantity));
                    }
                    
                }
                response.getWriter().write (purchase.get(request.getParameter("login")).size()+" ");    

            }
            if(request.getParameter("gen") != null) {
                if(purchase.get(request.getParameter("login")).size() == 0) 
                    response.getWriter().write("<h1> purchase is empty </h1>");
                else {
                    HttpSession session = request.getSession();
                    response.getWriter().write (genPurchase (request.getParameter ("login") , (String)session.getAttribute("username")));
                }

            }
        }
        public void addpost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String name = request.getParameter("name");
            HttpSession session = request.getSession();
            int quantity = Integer.parseInt(request.getParameter("quanity"));
            int price = Integer.parseInt(request.getParameter("price"));
            String dectrip = request.getParameter("decript");
            String[] tag = request.getParameter("tags").split(" ");
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 0; i < tag.length; i++)
                tags.add(tag[i]);
            String login =(String)session.getAttribute("username");
            Part filePart = request.getPart("picture");
            String picFile = desk.size() + filePart.getSubmittedFileName();
            File file = new File(request.getServletContext().getRealPath("/") + "img/", picFile);
            String tmp = filePart.getSubmittedFileName();
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
            }
            addAd(name, dectrip, quantity, price, tags, login,picFile);
            response.sendRedirect("/website/catalog");
        }

        public void buyproducts(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
                    HashMap<String,Integer> products = new HashMap<>(); // название товара и его количество
            ArrayList<Purchase> tmp = purchase.get(request.getParameter("login"));
            for(Purchase t : tmp) {
                products.put(t.getName(),t.getQuintity());
            }

            for (Map.Entry<String, Description> t  : desk.entrySet()) {
                String name = t.getKey();
                if(products.containsKey(name)){
                    Description a = t.getValue();
                    int newQ = a.getQuintity() - products.get(name);
                    if(newQ < 0) {
                        response.getWriter().write("Error! It`s impossible to buy this product");
                        return;
                    }
                    a.changeQuantity(newQ);
                }
            }
            writeToFile();
            purchase.remove(request.getParameter("login"));
            response.getWriter().write("<script>genPurchase('" + request.getParameter("login") + "')</script>");
        }

}