<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>My Catalog</title>
<style>
.row-padding {
    margin-top: 25px;
    margin-bottom: 25px;
}
</style>
<script>
$(function () {
    $( '#table' ).searchable({
        striped: true,
        oddRow: { 'background-color': '#f5f5f5' },
        evenRow: { 'background-color': '#fff' },
        searchType: 'fuzzy'
    });
    
    $( '#searchable-container' ).searchable({
        searchField: '#container-search',
        selector: '.row',
        childSelector: '.col-xs-4',
        show: function( elem ) {
            elem.slideDown(100);
        },
        hide: function( elem ) {
            elem.slideUp( 100 );
        }
    })
});
</script>

</head>
<body>
<div class="container">
       <div class="row">
        <div class="col-lg-12">
        <table ><tr>
        <td style="width:100%">
        <h3>Insert New Contact</h3>
        </td>
        <td >
        <h4>(user:<%= (String)session.getAttribute("SessionName")%>)</h4>
        </td>
        <td style="padding: 8px">
        <form action="controller" method="post"><input type="hidden" name="id" value="logout"><input type="submit" value="logout" class="btn btn-warning"></form>
        </td>
        </tr></table>
            
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
          <form action="controller" method="post">
            <table class="table">
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Telephone</th>
                        <th>email</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
             
                    <tr>
                        <td style="width:25%"><input type="text" name="f_name" required class="form-control"  ></td>
                        <td style="width:25%"><input type="text" name="l_name" required class="form-control"  ></td>
                        <td style="width:25%"><input type="text" name="tel" required class="form-control"  ></td>
                        <td style="width:25%"><input type="email" name="mail" required class="form-control"  ></td>
                        <td style="width:25%"><input type="hidden" name="id" value="insert"><input type="submit" value="Insert" class="btn btn-success"> </td>
                    </tr>
                
                  
                </tbody>
            </table>
             </form>   
            <hr>
        </div>
    </div>
     
   
   
    
    
           <%

String name = (String)session.getAttribute("SessionName");



if(name == "" || name ==null){

	name ="User";

}

%> 

    
    
    <div class="row">
        <div class="col-sm-12">
            <h3>My Contacts </h3>
        </div>
         
    </div>

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://localhost/exe_26_4?useSSL=false"
     user="afwteas"  password="08ian1989"/>
     <sql:query dataSource="${snapshot}" var="result">
SELECT * from contacts where idu in(select idu from users where username = '<%=(String)session.getAttribute("SessionName") %>');
</sql:query>
     
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <input type="search" id="search" value="" class="form-control" placeholder="Search using Fuzzy searching">
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <table class="table" id="table">
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Telephone</th>
                        <th>email</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="row" items="${result.rows}">
                    <tr>
                        <td style="width:25%"><c:out value="${row.name}"/></td>
                        <td style="width:25%"><c:out value="${row.prename}"/></td>
                        <td style="width:25%"><c:out value="${row.cellphone}"/></td>
                        <td style="width:25%"><c:out value="${row.email}"/></td>
                        <td style="width:25%"> <form action="controller" method="post"><input type="hidden" name="idc" value="${row.idc}"><input type="hidden" name="id" value="delete"><input type="submit" value="Delete" class="btn btn-danger"></form> </td>
                    </tr>
                    
                    </c:forEach>
                </tbody>
            </table>
            <hr>
        </div>
    </div>

    
  </div>  
  
  
<script src="//rawgithub.com/stidges/jquery-searchable/master/dist/jquery.searchable-1.0.0.min.js"></script>
</body>
</html>