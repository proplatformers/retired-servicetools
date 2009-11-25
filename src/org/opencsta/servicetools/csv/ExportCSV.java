/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.servicetools.csv;

import org.opencsta.servicetools.database.Database;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.log4j.Logger;

/**
 *
 * @author cm
 */
public class ExportCSV {
    private static Logger csvlogger = Logger.getLogger(ExportCSV.class);
    private FileOutputStream fos ;
    private PrintStream ps ;
    private String filename ;

    public ExportCSV(){

    }

    public ExportCSV(String _filename){
        setFilename(_filename) ;
    }

    public void openFile(){
        try {
            if( getFilename() == null ){
                throw new IOException("No filename set") ;
            }
            File file= new File(getFilename());
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHeaders(String[] headers){
        String row = new String();
        for(int i = 0 ; i < headers.length ; i++ ){
            row += "\"" +headers[i]+ "\"" ;
            if( (i+1) == headers.length ){
                continue ;
            }
            row += "," ;
        }
        csvlogger.info("Writing Headers to file (" + getFilename() + "):" + row) ;
        ps.println(row);
    }

    public void writeRow(String[] rowData){
        String row = new String();
            for(int i = 0 ; i < rowData.length ; i++ ){
                row += "\"" +rowData[i]+ "\"" ;
                if( (i+1) == rowData.length ){
                    continue ;
                }
                row += "," ;
            }
        csvlogger.info("Writing Row Data to file ("+getFilename()+"):" + row) ;
        ps.println(row);
    }

    public void closeFile(){
        try{
            fos.close() ;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

}
