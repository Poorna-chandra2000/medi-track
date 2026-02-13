package meditrack.utils;

import java.util.*;
import java.io.*;
public class CSVUtils {
    
    public static void writeLines(String file,List<String> lines){
        try(PrintWriter p=new PrintWriter(new FileWriter(file))){
            lines.forEach(p::println);
        }
         catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static List<String[]> read(String file){
        List<String[]> out=new ArrayList<>();
        try(BufferedReader br =new BufferedReader(new FileReader(file))) {
            String line;
            while ((line=br.readLine())!=null) {
                out.add(line.split(","));
            }
        } catch (Exception e) {
            System.out.println("No file found"+file);
        }
        return out;
    }
}
