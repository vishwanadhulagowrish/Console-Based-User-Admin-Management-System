package Project3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleProject {

	int userid=0;
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	Scanner sc=new Scanner(System.in);
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		landing();
	}
	public ConsoleProject(Connection con) throws ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.con=con;
	}

	//landing page
	public static void landing() throws SQLException,ClassNotFoundException{
		
		Scanner sc=new Scanner(System.in);
		System.out.println("---->Console based Project of user and  Admin<---------\nTap to strt the project");
		sc.nextLine();
		ConsoleProject p=new ConsoleProject(DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBC?user=root&&password=Varshitha@123"));
		System.out.println("Enter your choice");
		System.out.println("1.Register\n2.Login");
		switch(sc.nextInt()) {
		case 1:p.register();
				break;
		case 2:System.out.println("how do you want to login");
				System.out.println("1.Admin\n2.User");
				switch(sc.nextInt()) {
				case 1:if(p.adminLogin()) {
					p.adminMenu();
					
				}else {
					System.out.println("invalid credentials");
				}
				break;
				case 2:if(p.userLogin()) {
					p.userMenu();
				}else {
					System.out.println("invalid credentials");
				}
				break;
				default: System.out.println("Invalid option");
				break;
				}
				break;
		}
		
	}
	
	//registration
	public  void register()throws SQLException,ClassNotFoundException{
		System.out.println("Enter your details");
		System.out.println("Enter empid");
		int eid=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter email");
		String email=sc.nextLine();
		System.out.println("Enter pwd");
		String pwd=sc.nextLine();
		System.out.println("Enter you role (admin/user");
		String role=sc.nextLine();
		
		String qry="insert into logindetails values(?,?,?,?)";
		ps=con.prepareStatement(qry);
		ps.setInt(1,eid);
		ps.setString(2,email);
		ps.setString(3,pwd);
		ps.setString(4,role);
		if(ps.executeUpdate()>0) {
			System.out.println("Registration successfull");
			landing();
		}else {
			System.out.println("Not registered");
		}
	}
	
	//AdminLogin
	public boolean adminLogin()throws SQLException {
		ps=con.prepareStatement("Select userid,email,type from logindetails where email=? and password=? and type='admin'");
		
		System.out.println("Enter your email");
		String email=sc.nextLine();
		System.out.println("Enter your password");
		String pwd=sc.nextLine();
		ps.setString(1, email);
		ps.setString(2, pwd);
		rs=ps.executeQuery();
		if(rs.next()) {
			System.out.println("Admin login success");
			return true;
		}else {
			System.out.println("Invalid credentials");
			return false;
		}
	}
	
	
	
	//UserLogin
	public boolean userLogin()throws SQLException {
		  System.out.println("enter your mail");
		    String mail=sc.nextLine();
		    System.out.println("enter your password");
		    String pass=sc.nextLine();

		    ps=con.prepareStatement("select userid, email,password,type from logindetails where email=? and password=? and type='user'");
		    ps.setString(1, mail);
		    ps.setString(2, pass);
		    ResultSet rs=ps.executeQuery();
		    if(rs.next()) {
		    	 userid=rs.getInt("userid");
		    
		        System.out.println("login successfull as "+userid);
		        
		       return true;
		        
		    } else {
		        System.out.println("invalid credentials");
		        return false;
		    }
	}
	
	//AdminMenu
	public void  adminMenu() throws SQLException,ClassNotFoundException{
		while(true) {
			System.out.println("choose ypur operation ");
		    System.out.println("1.upadte userid");
			System.out.println("2. Update email");
            System.out.println("3. Update password");
            System.out.println("4. Update type");
            System.out.println("5. Find (show  details)");
            System.out.println("6. Delete  account");
            System.out.println("7. Logout");
            
            switch(sc.nextInt()) {
            case 1:adminUpdateById();
            	break;
            case 2:adminUpdateByEmail();
            	break;
            case 3:adminUpdateByPwd();
            	break;
            case 4:admiUpdateByRole();
            	break;
            case 5:adminFind();
            	break;
            case 6:adminDelete();
            	break;
            case 7:System.out.println("logout successfull");
             	landing();
             	break;
             default:System.out.println("invalid option");
            }
            
		}
		
	}
	//admin Methods
	public void adminUpdateById() throws SQLException {
		sc.nextLine();
		System.out.println("Enter old id of user");
		int old=sc.nextInt();
		System.out.println("Enter new id of user");
		int neww=sc.nextInt();
		ps=con.prepareStatement("update logindetails set userid=? where userid=?");
		ps.setInt(1, neww);
		ps.setInt(2, old);
		int c=ps.executeUpdate();
		if(c>0) {
			System.out.println("id updated successfully old id"+old+" to newId:-"+neww);
		}else {
			System.out.println("user not found to update");
		}
	}
	
	public void adminUpdateByPwd()throws SQLException {
		
		System.out.println("enter userid of record you want to update");
		int oid=sc.nextInt();
		sc.nextLine();
		System.out.println("enter new password");
         String newpass=sc.nextLine();
		ps=con.prepareStatement("update logindetails set password=? where userid=?");
	    ps.setString(1, newpass);
	    ps.setInt(2, oid);
		int updated=ps.executeUpdate();
		if(updated>0) {
			System.out.println("succesfully updated the Email of "+oid+ " password to " +newpass);
		}
		else {
			System.out.println("no such record found to update");
		}
		
	}
	
	public void adminUpdateByEmail() throws SQLException {
		
		System.out.println("enter userid of record you want to update");
		int oid=sc.nextInt();
		sc.nextLine();
		System.out.println("enter new Email");
         String newmail=sc.nextLine();
         ps=con.prepareStatement("update logindetails set email=? where userid=?");
         ps.setString(1, newmail);
         ps.setInt(2, oid);
         int c=ps.executeUpdate();
         if(c>0) {
 			System.out.println("succesfully updated the Email of "+oid+ " email  to " +newmail);
        	 
         }else {
 			System.out.println("no such record found to update");

         }
		
	}
	
	public void admiUpdateByRole()throws SQLException {
		
		System.out.println("enter userid of record you want to update");
		int oid=sc.nextInt();
		sc.nextLine();
		System.out.println("enter new type");
         String newtype=sc.nextLine();
		ps=con.prepareStatement("update logindetails set type=? where userid=?");
		ps.setString(1, newtype);
		ps.setInt(2, oid);
		int updated=ps.executeUpdate();
		if(updated>0) {
			System.out.println("succesfully updated the Email of "+oid+ "  type to " +newtype);
		}
		else {
			System.out.println("no such record found to update");
		}
		
	}
	
	public void adminFind()throws SQLException {
		sc.nextLine();
		System.out.println("enter userid of record you want to find");
		int oid=sc.nextInt();
		ps=con.prepareStatement("select userid,email,password,type from logindetails where userid=?");
    	ps.setInt(1, oid);
    	rs=ps.executeQuery();
    	if(rs.next()) {
    	System.out.println("userid ="+rs.getString("userid"));
    	System.out.println("email="+rs.getString("email"));
    	System.out.println("password"+rs.getString("password"));
    	System.out.println("type"+rs.getString("type"));
    	}
    	else {
    		System.out.println("no such id founnd ");
    	}
	}
	
	public void adminDelete()throws SQLException {
		sc.nextLine();
		System.out.println("enter userid of record you want to find");
		int oid=sc.nextInt();
		ps=con.prepareStatement("delete  from logindetails where userid=?");
		ps.setInt(1, oid);
		ps.executeUpdate();
		System.out.println("successfully deleted user  "+oid);
	}
	//UserMenu
	public void userMenu() throws SQLException,ClassNotFoundException{
		
		while(true) {
			System.out.println("choose your operation ");
		     System.out.println("1. Update email");
	            System.out.println("2. Update password");
	            System.out.println("3. Find (show my details)");
	            System.out.println("4. Delete my account");
	            System.out.println("5. Logout");
	            
	            int ch=sc.nextInt();
	            switch(ch) {
	            case 1:updateEmail();
	            		break;
	            case 2:updatePassword();
	                  break;
	            case 3:find();
	            		break;
	            
	            case 4:deleteUser();
	            		break;
	            case 5:
	                   System.out.println("success fully loggedd out");
	                   landing();
	                   
	                 break;
	            default :System.out.println("invalid otion");
	            }
	            
		}
		
	}
	
	
	//user Update pwd
	public void updatePassword()throws SQLException {
		sc.nextLine();
		ps=con.prepareStatement("update logindetails set password=? where userid=?");
		System.out.println("Enter new password");
		String pwd=sc.nextLine();
		System.out.println("Enter your empid");
		int eid=sc.nextInt();
		ps.setString(1, pwd);
		ps.setInt(2, eid);
		ps.executeUpdate();
		if(ps.executeUpdate()>0) {
			System.out.println("updating password successful");
		}else {
			System.out.println("Updating failed");
		}
		ps.close();
	}
	//user Update Email
	public  void updateEmail() throws SQLException {
		
		sc.nextLine();
		ps=con.prepareStatement("update logindetails set email=? where userid=?");
		System.out.println("Enter new email");
		String nmail=sc.nextLine();
		ps.setString(1, nmail);
		ps.setInt(2, this.userid);
		int count=ps.executeUpdate();
		if(count>0) {
			System.out.println("updating success");
		}else {
			System.out.println("updating failed");
		}
		ps.close();
	}
	
	//user Find
	public void find()throws SQLException {
		sc.nextLine();
		ps=con.prepareStatement("Select * from logindetails where userid=?");
		ps.setInt(1, this.userid);
		rs=ps.executeQuery();
		if(rs.next()) {
			System.out.println("userid ="+rs.getString("userid"));
	    	System.out.println("email="+rs.getString("email"));
	    	System.out.println("password="+rs.getString("password"));
	    	System.out.println("type="+rs.getString("type"));
		}
	}
	
	//delete user
	public void deleteUser()throws SQLException,ClassNotFoundException {
		System.out.println("Are you sure \n1.Yes\n2.No");
		int sel=sc.nextInt();
		switch(sel) {
			case 1:ps=con.prepareStatement("delete from logindetails where userid=?");
				   ps.setInt(1,this.userid);
				   int c=ps.executeUpdate();
				   if(c>0) {
					   System.out.println("user Deleted successsfully");
				   }else {
					   System.out.println("Invalid details");
				   }
				   break;
				   
			case 2: userMenu();
		}
	}
	



}
