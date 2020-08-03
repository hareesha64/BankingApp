import java.sql.Statement;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.CallableStatement;
@WebServlet(urlPatterns = "/Onlinebanking")
public class OnlineBanking extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		
		request.getRequestDispatcher("firstpage.jsp").forward(request,response);

	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		
		ArrayList<Integer> trans_list=new ArrayList<Integer>();
		
		String amount=request.getParameter("amount");
		Connection mycon=null;
		Statement mystmt=null,mystmt1=null;
		ResultSet myrs=null,myrs1=null,myrs2=null;
		CallableStatement callmystmt=null;
		
		if(amount=="")
		{
			request.getSession().setAttribute("errmsg", "amount should not be empty");
			request.getRequestDispatcher("firstpage.jsp").forward(request, response);
			return;
		}
		else
		{
		try {
			
			mycon=DriverManager.getConnection("jdbc:mysql://localhost:3306/hareesha","student","student");
			mystmt=mycon.createStatement();
			mystmt1=mycon.createStatement();
			
			myrs=mystmt.executeQuery("select balance,acc_id from bankdetails");
			while(myrs.next()) {
				int updatebalance=myrs.getInt("balance");
				int accountid=myrs.getInt("acc_id");
				
				if(Integer.parseInt(amount)<=updatebalance) {
				updatebalance=updatebalance-Integer.parseInt(amount);
				PreparedStatement preparestmt=mycon.prepareStatement("update bankdetails set balance=? where acc_id=?");
				preparestmt.setInt(1,updatebalance);
				preparestmt.setInt(2, accountid);
				preparestmt.executeUpdate();
				myrs2=mystmt1.executeQuery("select count(*) as countlist from translist");
				while(myrs2.next()) {
				int count=myrs2.getInt("countlist");
				PreparedStatement preparestmt1=mycon.prepareStatement("insert into translist(serialno,acc_id,amount)values(?,?,?)");
				preparestmt1.setInt(2,accountid);
				preparestmt1.setInt(3, Integer.parseInt(amount));
				preparestmt1.setInt(1, count+1);
				preparestmt1.executeUpdate();
				}
				callmystmt=mycon.prepareCall("{call get_transcation_list(?)}");
				callmystmt.setInt(1,accountid);
				callmystmt.execute();
				myrs1=callmystmt.getResultSet();
				while(myrs1.next()) {
					int  trans_amount=myrs1.getInt("amount");
					trans_list.add(trans_amount);
					
				}
				
					request.setAttribute("transferlist", trans_list);
					request.setAttribute("Availablebalance", updatebalance);
					request.getRequestDispatcher("OnlineBanking.jsp").forward(request, response);
					

				
				}
				else {
					request.getSession().setAttribute("errormsg", "You don't have sufficient balance to transfer");
					request.getRequestDispatcher("firstpage.jsp").forward(request, response);
				}
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(mycon!=null)
				try {
					mycon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(mystmt!=null)
				try {
					mystmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(myrs!=null)
				try {
					myrs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		}
	}


}