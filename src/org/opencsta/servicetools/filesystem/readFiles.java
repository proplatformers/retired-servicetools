/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
See Chris Mylonas - this software is not to be distributed illwillfully.
 */

package org.opencsta.servicetools.filesystem;

import java.io.File;
import java.io.IOException;

public class readFiles {
  public static void main(String[] a)throws IOException{
    showDir(1, new File("/dev/disk/by-uuid/"));
  }
  static void showDir(int indent, File file) throws IOException {
    for (int i = 0; i < indent; i++)
      System.out.print('-');
    String abs = file.getAbsolutePath() ;
    String can = file.getCanonicalPath() ;
    if( abs.equalsIgnoreCase(can)){
        ;//not a link
    }
    else{
        System.out.println(abs + " links to " + can ) ;
    }
    File pwd = new File(".") ;
    System.out.println( "Current dir is: " + pwd.getAbsolutePath() ) ;
    File[] roots  =  pwd.listRoots() ;
    for( int i = 0 ; i < roots.length ; i++){
        System.out.println("Root is: " + roots[i].toString() ) ;
    }
    System.out.println(" Root dir is: " + pwd.listRoots() );
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++)
        showDir(indent + 4, files[i]);
    }
  }
}