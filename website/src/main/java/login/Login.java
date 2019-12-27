package login;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
//javac -cp ..\..\..\..\lib\servlet-api.jar Login.java -d ..\..\WEB-INF\classes
public class Login extends HttpServlet {


    private HashMap<String,ArrayList<String>> users = new HashMap<>();

    public void init(ServletConfig config) {

        getBase("database.txt");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.equals("/website/login"))
            request.getRequestDispatcher("/files/login.jsp").forward(request,response);
        if (uri.equals("/website/singup")){
            request.getRequestDispatcher("/files/singup.jsp").forward(request,response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String uri = request.getRequestURI();
            PrintWriter out = response.getWriter();
        if (uri.equals("/website/singup")) { //регистрация 
            response.getWriter().write(request.getParameter("name"));
            ArrayList<String> tmp = new ArrayList<>();
            String userName = request.getParameter("login");
            tmp.add(request.getParameter("password"));
            tmp.add(request.getParameter("name"));
            tmp.add(request.getParameter("surname"));
            addUser(userName,tmp);   
            
        } else if(uri.equals("/website/login")) { // вход в систему
            if(loggingIn(request.getParameter("login"), request.getParameter("password"))) {
                HttpSession session = request.getSession();
                String login = request.getParameter("login");
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                session.setAttribute("username",login);
                session.setAttribute("name",new String(getParametrFromDatabase(login,1).getBytes("UTF-8"),"UTF-8") );
                session.setAttribute("surname", getParametrFromDatabase(login,2));
                session.setAttribute("password", (login + name + surname).hashCode());
                if(users.get(login).size() < 4 )
                    session.setAttribute("isCreator","false");
                else if (getParametrFromDatabase(login,3).equals("true"))
                    session.setAttribute("isCreator","true");

            }
            else {
                //не вошел
                response.getWriter().write("Login or password is invalid");
            }
        } else if (uri.equals("/website/singup/login")) {
            String login = request.getParameter("login");
             response.getWriter().println(users.containsKey(login));

        }

    }

    private boolean loggingIn(String name, String password) {
        ArrayList<String> tmp = users.get(name);
        if(tmp != null) 
            if(tmp.get(0).equals(password))
                return true;
        return false;
    }

    private synchronized String getParametrFromDatabase(String login, int index) {
         return users.get(login).get(index);

    }


    private synchronized void addUser(String name, ArrayList<String> str) {
        try{
            users.put(name,str);
            File f1 = new File("database.txt");
            PrintWriter fw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f1,true), "UTF-8"));
            fw.write(name+" ");
            for (int i = 0; i < str.size(); i++) {
                fw.write(str.get(i) + " ");
            }
            fw.write("\n");
            fw.close();
        }
        catch(IOException e){

        }
        
    }

    private synchronized void getBase(String filename){
        try {
  
            StringBuilder sb = new StringBuilder();
            Scanner scan = new Scanner(new InputStreamReader(new FileInputStream("database.txt"), "UTF8"));
            while (scan.hasNext()) {
                String tmp = scan.nextLine();
                sb.append(tmp);
                sb.append("\n");
            }
            //name = sb.toString();
            String[] strs = sb.toString().split("\n");  
            for (int i = 0; i < strs.length; i++) {
                String[] tmp = strs[i].split(" ");
                ArrayList<String> data = new ArrayList<String>();
                for (int j = 1; j < tmp.length; j++) {
                    data.add(tmp[j]);
                }
                users.put(tmp[0], data);
            }

        }
        catch(Exception e){}
    }
    public String getString(String str){
        return str;
    }
}