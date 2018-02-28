import ucar.multiarray.MultiArray;
import ucar.netcdf.Attribute;
import ucar.netcdf.Netcdf;
import ucar.netcdf.NetcdfFile;
import ucar.netcdf.Variable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class NetCdftoCSV {

    static String filename = "/Users/arunsharma/Downloads/air.mon.mean.nc";

    public static void main(String[] args) {

        if (args.length > 0)
            filename = args[0];
        try {
            Netcdf nc = new NetcdfFile(filename,true);
            Variable lat = nc.get("lat");
            Variable lon = nc.get("lon");
            Variable time = nc.get("time");
            Variable nair = nc.get("air");


            HashMap<String, List<Float>> map = new HashMap<String, List<Float>>();

            for(int i=0; i<lon.getLengths()[0];i++){
                for (int j=0; j<lat.getLengths()[0];j++){
                    String key = Float.toString(lon.getFloat(new int[] {i}))+";"+Float.toString(lat.getFloat(new int[] {j}));
                    List<Float> airvalues = new ArrayList<Float>();
                    for (int k=0; k<time.getLengths()[0]; k++){
                        airvalues.add(k,nair.getFloat(new int[] {k,j,i}));
                    }
                    map.put(key,airvalues);

                }
            }

            PrintWriter writer = new PrintWriter("/Users/arunsharma/Desktop/air.csv", "UTF-8");

            for (Map.Entry<String, List<Float>> entry : map.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
                writer.println(entry.getKey()+","+entry.getValue());
//                writer.println(entry.getKey()+" : "+entry.getValue());
            }


        } catch (IOException e){
        e.printStackTrace();}
    }


}
