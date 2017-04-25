

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class controller
 */
@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method s
		response.getWriter().append("einai 02:34");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String param = request.getParameter("id");
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		
		
        PrintWriter out = response.getWriter();
		
				switch (param) {
				case "signin":
					String uname = request.getParameter("username");
					String pass = request.getParameter("password");
					
					try {
						init_db();
						byte myresult= authenticate( uname, pass);
						if(myresult==0)
						{
							request.setAttribute("message", "connection successful");

							session.setAttribute("SessionName", uname);
							getServletContext().getRequestDispatcher("/data.jsp").forward(request, response);
						}
						else if (myresult==1)
						{
							request.setAttribute("message", "no such username. try again!");

							
							getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
						}
						else if (myresult==2)
						{
							request.setAttribute("message", "wrong password. try again!");

							
							getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					break;
					
				case "register":
					String reg_uname = request.getParameter("username");
					String reg_pass = request.getParameter("password");
					String reg_pass2 = request.getParameter("password2");
					String email = request.getParameter("email");
					String fname = request.getParameter("first_name");
					String lname = request.getParameter("last_name");
					if (reg_pass.equals(reg_pass2))
					{
						if(reg_uname.equals("")||reg_pass.equals("")||reg_pass2.equals("")||email.equals("")|| fname.equals("")||lname.equals(""))
						{
							request.setAttribute("message", "form not fully completed. try again!");

							
							getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
						}
						else
						{
						try {
							init_db();
							byte myreg= register(fname,lname,reg_uname,reg_pass,email);
							if(myreg==0)
							{
								request.setAttribute("message", "register successful");

								
								getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
							}
							else if (myreg==1)
							{
								request.setAttribute("message", "existing user. try again!");

								
								getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
						}
					}
					else
					{
						request.setAttribute("message", "password missmatch. try again!");	
						getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
					}
					break;
				case "search":
					
					//getServletContext().getRequestDispatcher(page).forward(request, response);
					break;
				case "delete":
					String idc = request.getParameter("idc");
					try {
						delete_contact(idc);
						getServletContext().getRequestDispatcher("/data.jsp").forward(request, response);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case "insert":
					String name = request.getParameter("f_name");
					String prename = request.getParameter("l_name");
					String cellphone = request.getParameter("tel");
					String mail = request.getParameter("mail");
					String user = (String)session.getAttribute("SessionName");
					try {
						insert_data(name,prename,cellphone,mail, user);
						getServletContext().getRequestDispatcher("/data.jsp").forward(request, response);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case "logout":
					
					request.getSession().invalidate();
					getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
					break;
				default:
					getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
					
					break;
				}
	}

	public void init_db() throws SQLException{
		Connection con = null;
        Statement stm = null;
        

        
			    con = DriverManager.getConnection("jdbc:mysql://localhost/?user=afwteas&password=08ian1989&useSSL=false"); 
			    		stm = con.createStatement();
			    		stm.executeUpdate("CREATE DATABASE IF NOT EXISTS exe_26_4");
			    		stm.executeUpdate("USE exe_26_4");
			    		stm.executeUpdate("CREATE TABLE IF NOT EXISTS users " + "(idu int NOT NULL AUTO_INCREMENT,"+
		           "name VARCHAR(255) not NULL, " +  " prename VARCHAR(255) not NULL, " +  " username VARCHAR(255) not NULL, "
			    				+ " password VARCHAR(255) not NULL, " + "email VARCHAR(255) not NULL,"+
		           " PRIMARY KEY ( idu ))");
			    		
			    		stm.executeUpdate("CREATE TABLE IF NOT EXISTS contacts " + "(idc int NOT NULL AUTO_INCREMENT,"+
			 		           "name VARCHAR(255) not NULL, " +
			 		           " prename VARCHAR(255) not NULL, " +  " cellphone VARCHAR(255) not NULL, " + " email VARCHAR(255) not NULL, " + " idu int NOT NULL, " + 
			 		           " PRIMARY KEY ( idc ), FOREIGN KEY (idu) REFERENCES users(idu))");
			    		con.close();
	};
	
	public void insert_data(String name,String prename,String cellphone,String email, String user) throws SQLException{
		Connection con = null;
		ResultSet rs = null;
       String sql;
        Statement stm = null;
       // ResultSet rs = stm.executeQuery("SELECT region_name, zips FROM REGIONS");

        
			    con = DriverManager.getConnection("jdbc:mysql://localhost/exe_26_4?user=afwteas&password=08ian1989&useSSL=false"); 
			    		stm = con.createStatement();
			    		 System.err.println("trexei kai edw");
			   		 String query ="select idu from users where username = '"+user+"'";
			   		rs = stm.executeQuery(query);
			   		rs.next();
			   		String idu = rs.getString("idu");
			    		sql = "INSERT INTO contacts (name,prename,cellphone,email,idu) VALUES ('"+name+"', '"+prename+"', '"+cellphone+"', '"+email+"', '"+idu+"')";
				        stm.executeUpdate(sql);
				        con.close();
				       
	};
	
	
	public void delete_contact(String idc) throws SQLException{
		Connection con = null;
       String sql;
        Statement stm = null;
        
        con = DriverManager.getConnection("jdbc:mysql://localhost/exe_26_4?user=afwteas&password=08ian1989&useSSL=false"); 
		stm = con.createStatement();

			    		sql = "DELETE FROM contacts WHERE idc='"+idc+"'";
				        stm.executeUpdate(sql);
				        con.close();

	
	    	
	};
	//return 0 auth ok// return 1 no username//return 2 wrong password
	public byte authenticate(String uname,String pass) throws SQLException{
		Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        con = DriverManager.getConnection("jdbc:mysql://localhost/exe_26_4?user=afwteas&password=08ian1989&useSSL=false"); 
		stm = con.createStatement();
		 String query ="select username, password from users";
		rs = stm.executeQuery(query);
		 while (rs.next()) {
			if(uname.equals( rs.getString("username")))
			 	{
			 		if(pass.equals( rs.getString("password")))
			 		{
			 		con.close();
			 		return 0;
			 		}
			 		else 
			 			{
			 			con.close();
			 			return 2;
			 			}
			 	}

	           
	        }
				        con.close();

	return 1;
	    	
	};
	//return 0 register ok// return 1 existing user
	public byte register(String fname,String lname,String uname,String pass,String email) throws SQLException{
		 String sql;
		Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        con = DriverManager.getConnection("jdbc:mysql://localhost/exe_26_4?user=afwteas&password=08ian1989&useSSL=false"); 
		stm = con.createStatement();
		 String query ="select username, password from users";
		rs = stm.executeQuery(query);
		 while (rs.next()) {
			 
			 	if(uname.equals( rs.getString("username")))
			 	{
			 		con.close();
			 		return 1;
			 	}

	        }
		
		sql = "INSERT INTO users (name, prename,username,password,email) VALUES ('"+fname+"', '"+lname+"', '"+uname+"', '"+pass+"', '"+email+"')";
		
	        stm.executeUpdate(sql);
	        con.close();	       
		 con.close();

	return 0;
	    	
	};
	
	
}
