package Model;
import java.sql.*;


import java.util.ArrayList;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

public class Registration {
	private Connection con;
    HttpSession se;

    public Registration(HttpSession session) {
        try {

            Class.forName("com.mysql.jdbc.Driver"); // load the drivers
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sooksmas1", "root", "tiger");
            // connection with data base
            se = session;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Registration(String name, String phone, String email, String pw) {
        PreparedStatement ps;
        String status = "";
        try {
            Statement st = null;
            ResultSet rs = null;
            st = con.createStatement();
            rs = st.executeQuery("select * from sookshmas1 where phone='" + phone + "' or email='" + email + "';");
            boolean b = rs.next();
            if (b) {
                status = "existed";
            } else {
                ps = (PreparedStatement) con.prepareStatement("insert into sookshmas1 values(0,?,?,?,?,now())");
                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, email);
                ps.setString(4, pw);
                int a = ps.executeUpdate();
                if (a > 0) {
                    status = "success";
                } else {
                    status = "failure";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

	public String login(String email, String pass) {
        String status1 = "", id = "";
        String name = "", emails = "";

        try {
            Statement st = null;
            ResultSet rs = null;
            st = con.createStatement();

            rs = st.executeQuery("select * from sookshmas1 where email='" + email + "' and password='" + pass + "';");
            boolean b = rs.next();
            if (b == true) {
                id = rs.getString("sino");
                name = rs.getString("name");
                emails = rs.getString("email");
                se.setAttribute("uname", name);
                se.setAttribute("email", emails);
                se.setAttribute("id", id);
                status1 = "success";
            } else {
                status1 = "failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status1;
    }
	public ArrayList<Student> getUserinfo(String id) { 
		Statement st = null; 
		ResultSet rs = null; 
		ArrayList<Student> al = new ArrayList<Student>(); 
		try { 
		st = con.createStatement(); 
		String qry = "select * from sookshmas1 where sino = '" + id + "';"; 
		rs = st.executeQuery(qry); 
		while (rs.next()) { 
		Student p = new Student(); 
		p.setId(rs.getString("sino")); 
		p.setName(rs.getString("name")); 
		p.setemail(rs.getString("email")); 
		p.setphone(rs.getString("phone")); 
		p.setdate(rs.getString("date")); 
		al.add(p); 
		} 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		return al; 
		}
	public Student getInfo() {
        Statement st = null;
        ResultSet rs = null;
        Student s = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from sookshmas1 where sino= '" + se.getAttribute("id") + "'");
            boolean b = rs.next();
            if (b == true) {
                s = new Student();
                s.setName(rs.getString("name"));
                s.setphone(rs.getString("phone"));
                s.setemail(rs.getString("email"));
            } else {
                s = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
	public String update(String name, String pno, String email) {
        String status = "";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            st.executeUpdate("update sookshmas1 set name='" + name + "',phone='" + pno + "',email='" + email + "' where sino= '" + se.getAttribute("id") + "' ");
            se.setAttribute("uname", name);
            status = "success";
        } catch (Exception e) {
            status = "failure";
            e.printStackTrace();
        }

        return status;
    }
	public ArrayList<Student> getUserDetails() {
	       Statement st;
	       ResultSet rs;
	       ArrayList<Student> al = new ArrayList<Student>();
	       try {
	           st = con.createStatement();
	           String qry = "select *,"
	                   + "date_format(date,'%b %d, %Y') as date1"
	                   + " from sookshmas1 where sino not in(1);";
	           rs = st.executeQuery(qry);
	           while (rs.next()) {
	               Student p = new Student();
	               p.setId(rs.getString("sino"));
	               p.setName(rs.getString("name"));
	               p.setemail(rs.getString("email"));
	               p.setphone(rs.getString("phone"));
	               p.setdate(rs.getString("date1"));
	               al.add(p);
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return al;
	   }
	public String delete(int id) {
	       int count = 0;
	       Statement st = null;
	       String status = "";
	       try {
	           st = con.createStatement();
	           count = st.executeUpdate("delete from sookshmas1 where "
	                   + "sino='" + id + "'");
	           if (count > 0) {
	               status = "success";
	           } else {
	               status = "failure";
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }

	       return status;
	   }
	public String ForgotPassword(String mail, String pw) {
	       String status = "";
	       try {
	           Statement st = con.createStatement();

	           int rspw = st.executeUpdate("update sookshmas1  set password='" + pw + "' where email='" + mail + "';");
	           if (rspw > 0) {
	               status = "success";
	           } else {
	               status = "failure";
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return status;
	   }
	public String getPassword(String email,String oldPass) {
		// TODO Auto-generated method stub
		       String status = "";
		       PreparedStatement ps = null;
		       ResultSet rs = null;
		       String query = "select * from sookshmas1 where email=? and password=?";
		       try {
		           ps = con.prepareStatement(query);
		           ps.setString(1, email);
		           ps.setString(2, oldPass);
		           rs = ps.executeQuery();
		           if (rs.next()) {
		               //se.setAttribute("pwd", rs.getString(5));
		               status = "success";
		           } else {
		               status = "failed";
		           }
		       } catch (SQLException e) {
		           e.printStackTrace();
		       }
		       //System.out.println(status);
		       return status;
		   }
		// It Reset the user Password


	public String resetPassword(String email, String pwd) {
		// TODO Auto-generated method stub
		       String status = "";
		       PreparedStatement ps = null;
		       boolean res;
		       try {
		           ps = con.prepareStatement("update sookshmas1 set password =  ? where  email =  ?");
		           ps.setString(1, pwd);
		           ps.setString(2, email);
		           int rc = ps.executeUpdate();
		           if (rc > 0) {
		               status = "success";
		           } else {
		               status = "failed";
		           }
		       } catch (SQLException e) {
		// TODO Auto-generated catch block
		           e.printStackTrace();
		       }
		       return status;
		   }




}
