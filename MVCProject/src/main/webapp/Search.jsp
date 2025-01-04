<%@page import="java.util.Iterator"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="Model.Student"%> 
<%@page import="Model.Registration"%> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>JSP Page</title> 
<link rel="stylesheet" 
href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" > 
</head> 
<body> 
<%@include file="Header.jsp"%> 
<style> 
.jumbotron{ 
background-color: white; 
} 
</style> 
    <center> 
        <% if (session.getAttribute("id") != null && session.getAttribute("id").equals("1")) {%> 
        <font color="blue" size="4"> 
        <h2> Search module </h2> 
        </font> 
        <br><br> 
        <form action="search" method="POST"> 
            <div class="form-group col-md-4"> 
                <label >Student id:</label> 
                <input type="text" name="id" class="form-control" > 
            </div> 
            <button type="submit" class="btn btn-primary" name="submit">Search</button> 
        </form> 
        <% if (request.getParameter("id") != null) {%> 
        <div class="container "> 
            <div class="jumbotron"> 
 
                <table class="table"> 
                    <thead> 
                        <tr style="background-color: lightblue;"> 
                    <br> 
                    <th>Sino</th> 
                    <th>Name</th> 
                    <th>Email</th> 
                    <th>Phone</th> 
                    <th>Date</th> 
 
                    </tr> 
                    </thead> 
                    <tbody id="table"> 
                        <% Registration reg = new Registration(session); 
                            ArrayList<Student> mydata = reg.getUserinfo(request.getParameter("id")); 
                            Iterator<Student> itr = mydata.iterator(); 
                            while (itr.hasNext()) { 
                                Student s = itr.next(); 
                        %> 
                        <tr> 
                            <td><%=s.getId()%></td> 
                            <td><%=s.getName()%></td> 
                            <td><%=s.getemail()%></td> 
                            <td><%=s.getphone()%></td> 
                            <td><%=s.getdate()%></td> 
                        </tr>  
                        <%}%>  
                    </tbody> 
                </table> 
            </div> 
        </div> 
        <%} 
            }%>  
            
    </center> 
    <%@include file="Footer.jsp"%> 
</body> 
</html>
