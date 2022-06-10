/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lichadejewellers;

import Java_code.Database_tables_configs;
import javax.swing.JOptionPane;

/**
 *
 * @author radake_a
 */
public class Khedikar_Jewellers
{
    
    public static void main (String[] args)
    {
        try{
        Database_tables_configs dbc= new Database_tables_configs();
        dbc.CreateDatabase();
        dbc.CreateTables();
        
        new Login().setVisible(true);
        }
        catch( Exception e)
        {
           JOptionPane.showMessageDialog(null, "SYSTEM CONFIGS ARE NOT DONE , PLEASE CHECK");
        }
    }
}
