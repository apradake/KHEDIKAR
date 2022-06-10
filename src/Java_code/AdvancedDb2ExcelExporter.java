/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java_code;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lichadejewellers.ConnectionProvider;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AdvancedDb2ExcelExporter {
 
  /* public static void main(String[] args)
    {
        AdvancedDb2ExcelExporter exporter = new AdvancedDb2ExcelExporter();
      //  exporter.export("final_invoice");
       // exporter.export("Product");
    }
 */
    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeInfo = dateFormat.format(new Date());
        return baseName.concat(String.format("_%s.xlsx", dateTimeInfo));
    }
 
    public void export(String table, java.sql.Date from_date, java.sql.Date to_date )
    {
       
        String currentDirectory = System.getProperty("user.dir");
        String excelFilePath = currentDirectory+"\\Invoice_report\\Revenue_"+getFileName(table.concat("_Export"));
 
        try {
            Connection cons= ConnectionProvider.getCon();
            
            String sql = "SELECT * FROM ".concat(table)+ " where invoice_billing_date between "+"'"+from_date+"'"+" and "+"'"+to_date+"'"; 
 
         //   System.out.println(sql);
            Statement statement = cons.createStatement();
 
            ResultSet result = statement.executeQuery(sql);
 
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(table);
 
            writeHeaderLine(result, sheet);
 
            writeDataLines(result, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
 
            statement.close();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
    
    
     public void exportGST(String table, java.sql.Date from_date, java.sql.Date to_date )
    {
       
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("current directory "+currentDirectory);
        String excelFilePath = currentDirectory+"\\GST_report\\GST_"+getFileName(table.concat("_Export"));
 
        try {
            Connection cons= ConnectionProvider.getCon();
            
            String sql = "select sum(invoice_bill_amount), sum(invoice_gst_paid) from ".concat(table)+ " where invoice_billing_date between "+"'"+from_date+"'"+" and "+"'"+to_date+"'"+ "and bill_status!='RETURNED'"; 
 
         //   System.out.println(sql);
            Statement statement = cons.createStatement();
 
            ResultSet result = statement.executeQuery(sql);
 
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(table);
 
            writeHeaderLine(result, sheet);
                  
            
            writeDataLines(result, workbook, sheet);
            
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
 
            statement.close();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
 
    private void writeHeaderLine(ResultSet result, XSSFSheet sheet) throws SQLException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        
        int numberOfColumns = metaData.getColumnCount();
 
        Row headerRow = sheet.createRow(0);
 
      
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            Cell headerCell = headerRow.createCell(i - 1);
            headerCell.setCellValue(columnName);
        }
    }
 
    private void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet)
            throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
 
        int rowCount = 1;
 
        while (result.next()) {
            Row row = sheet.createRow(rowCount++);
 
            for (int i = 1; i <= numberOfColumns; i++) {
                Object valueObject = result.getObject(i);
 
                Cell cell = row.createCell(i - 1);
 
                if (valueObject instanceof Boolean)
                    cell.setCellValue((Boolean) valueObject);
                else if (valueObject instanceof Double)
                    cell.setCellValue((double) valueObject);
                else if (valueObject instanceof Float)
                    cell.setCellValue((float) valueObject);
                else if (valueObject instanceof Date) {
                    cell.setCellValue((Date) valueObject);
                    formatDateCell(workbook, cell);
                } else cell.setCellValue((String) valueObject);
 
            }
 
        }
    }
 
    private void formatDateCell(XSSFWorkbook workbook, Cell cell) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        cell.setCellStyle(cellStyle);
    }
}