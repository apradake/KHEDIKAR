/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lichadejewellers;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author radake_a
 */
public class ConnectionProvider
{

public static Connection getCon() 
        {
         
                try
                    
                {               
                    Class.forName("com.mysql.cj.jdbc.Driver");               
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/biling","root","sqlmaster");                  
                   
                    return con;  
                    
                }
                
                catch(Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            
        }
}
