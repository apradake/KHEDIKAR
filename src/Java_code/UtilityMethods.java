/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java_code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lichadejewellers.ConnectionProvider;

/**
 *
 * @author radake_a
 */
public class UtilityMethods 

{
  
  public float returnMakingChargesIndividial(String product, float grams)  
    
  {
      float making_charge = 0;
      ResultSet rs= null;
      try
     {
     
     Connection con= ConnectionProvider.getCon();
     PreparedStatement making_chrge= con.prepareStatement("Select * from jewellery_type_list where product_description="+"'"+product+"'", rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
    
     rs=making_chrge.executeQuery();
     rs.first(); 
     float making_item_wise=rs.getFloat("making_charge_per_gm");       
     making_charge=grams*making_item_wise;
         
     }
      
      catch (Exception e)
      {
        e.printStackTrace();
       
      }
      
      
     return making_charge ;
      
     
      
  }
   
  
  
  public float returnCGST(float total_amount)
  {
      
      
      
      return (float) ((total_amount/100)*1.5);
      
  }
  public float returnSGST(float total_amount)
  {
      
      
      
      return (float) ((total_amount/100)*1.5);
      
  }  
  
  
  
  public float returnInvoiceAMount(float amount_without_gst, float making_charge, float cgst, float sgst )
  {
      
      return amount_without_gst+making_charge+cgst+sgst;
  }
  
  
  public float returnGST_Hallmarking(float hallmarking_amt)
  {
     
      
      float one_percent=hallmarking_amt/100;
      float gst=one_percent*9;
      
      return gst;
  }
    
}



