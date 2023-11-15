package application.orowan_3;

//This class is used to rewrite the files about reels so that they become compatible with the software "orowan"

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NewFile {
    //ArrayList<Float> roll = new ArrayList<Float>();
    public NewFile() {
    }

    // write in the file for the reel 2
    public static void F2() throws Exception {
        //Read the file
        File file = new File("D:/S8/8.4/Fichiers/Krakov/1939351_F2.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = br.readLine();
        String stringFormat = "%s %s %s %s %s %s %s %s %s %s %s%n";
        FileWriter intoFileWriter = new FileWriter("D:/S8/8.4/Fichiers/Model/inv_cst.txt");
        intoFileWriter.write(String.format(stringFormat, "Cas", "He", "Hs", "Te", "Ts", "Diam_WR", "WRyoung", "offset ini", "mu_ini", "Force", "G"));
        int i = 1;
        int j = 0;
        System.out.println(intoFileWriter);
        //For each line of the file, rewrite using the format specified
        while (str != null) {
            String[] f = str.split("; ");
            intoFileWriter.write(String.format(stringFormat, i, f[4].replace(',', '.'), f[5].replace(',', '.'), f[6].replace(',', '.'), f[7].replace(',', '.'), f[10].replace(',', '.'), f[12].replace(',', '.'), f[17].replace(',', '.'), f[15].replace(',', '.'), f[8].replace(',', '.'), f[9].replace(',', '.')));
            DataBase.getInstance().storeInput(Integer.toString(i), f[4].replace(',', '.'), f[5].replace(',', '.'), f[6].replace(',', '.'), f[7].replace(',', '.'), f[10].replace(',', '.'), f[12].replace(',', '.'), f[17].replace(',', '.'), f[15].replace(',', '.'), f[8].replace(',', '.'), f[9].replace(',', '.'));
            //roll.add(Float.parseFloat(f[23].replace(',', '.')));
            j++;
            if (j > 5) {
                j = 0;
                i++;
            }
            str = br.readLine();
        }
        intoFileWriter.close();
    }

    //write in the file for the reel 3
    public static void F3() throws Exception {
        File file = new File("D:/S8/8.4/Fichiers/Krakov/1939351_F3.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = br.readLine();
        String formatStr = "%s %s %s %s %s %s %s %s %s %s %s%n";
        FileWriter myWriter = new FileWriter("D:/S8/8.4/Fichiers/Model/inv_cst.txt");
        myWriter.write(String.format(formatStr, "Cas", "He", "Hs", "Te", "Ts", "Diam_WR", "WRyoung", "offset ini", "mu_ini", "Force", "G"));
        int i = 1;
        int j = 0;
        System.out.println(myWriter);
        while (str != null) {
            String[] f = str.split("\\s+");
            myWriter.write(String.format(formatStr, i, f[4].replace(',', '.'), f[5].replace(',', '.'),
                    f[6].replace(',', '.'), f[7].replace(',', '.'), f[10].replace(',', '.'),
                    f[12].replace(',', '.'), f[17].replace(',', '.'), f[15].replace(',', '.'),
                    f[8].replace(',', '.'), f[9].replace(',', '.')));
            DataBase.getInstance().storeInput(Integer.toString(i), f[4].replace(',', '.'), f[5].replace(',', '.'),
                    f[6].replace(',', '.'), f[7].replace(',', '.'), f[10].replace(',', '.'),
                    f[12].replace(',', '.'), f[17].replace(',', '.'), f[15].replace(',', '.'),
                    f[8].replace(',', '.'), f[9].replace(',', '.'));
            //roll.add(Float.parseFloat(f[23].replace(',', '.')));
            j++;
            if (j > 5) {
                j = 0;
                i++;
            }
            str = br.readLine();
        }
        myWriter.close();
    }


}
