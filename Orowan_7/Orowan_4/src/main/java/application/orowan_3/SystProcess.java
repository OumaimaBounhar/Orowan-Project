package application.orowan_3;

//This class calls Orowan and calculate the average of the 5 values we get

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SystProcess {
    //Calling Orowan
    public void syst_Process() throws Exception {
        String[] cmd = {"D:/S8/8.4/Fichiers/Model/Orowan_x64.exe.exe"};
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd);
        InputStream processInputStream = process.getInputStream();
        OutputStream processOutputStream = process.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(processInputStream));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(processOutputStream));

        //System.out.println(reader.readLine());
        writer.write("i\n");
        writer.flush();
        writer.write("c\n");
        writer.flush();

        //System.out.println(reader.readLine());
        //System.out.println(reader.readLine());
        //System.out.println(reader.readLine());

        writer.write("D:/S8/8.4/Fichiers/Model/inv_cst.txt\n");
        writer.flush();

        //System.out.println(reader.readLine());

        writer.write("D:/S8/8.4/Fichiers/Model/output.txt\n");
        writer.flush();

        //System.out.println(reader.readLine());

        //Recuperer les données de friction du file output
        //System.out.println(friction);

        //Calculer les moyennes de chaque 5 veleurs
        //System.out.println(friction);
    }

    //Calculate the average of the 5 values of "sigma" indicators
    public ArrayList<Float> sigma() throws FileNotFoundException, InterruptedException, SQLException {
        ArrayList<Float> sigma = get_param(1); //pk 1 ?
        int division = sigma.size() / 5;
        //System.out.println(division);
        int rest = sigma.size() % 5; //?? pk .size()
        //System.out.println(reste);
        ArrayList<Float> mean5 = new ArrayList<Float>();
        mean5.clear();
        for (int i = 0; i < division; i++) {
            float sum = 0;
            for (int j = 0; j < 5; j++) {
                sum += sigma.get(i + j);
            }
            mean5.add(sum / 5);
        }
        if (rest != 0) {
            float sum = 0;
            for (int i = sigma.size() - rest; i < sigma.size(); i++) {
                sum += sigma.get(i);
            }
            mean5.add(sum / rest);
        }
        //System.out.println(moy5);
        return mean5;
    }

    //Read the data thrown by Orowan and calculate the average of the 5 values of the "rolling speed" indicators
    public ArrayList<Float> roll() throws InterruptedException, SQLException, NumberFormatException, IOException {
        ArrayList<Float> rollSpeed = new ArrayList<Float>();
        File file = new File("D:/S8/8.4/Fichiers/Krakov/1939351_F3.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine();
        while (st != null) {
            String[] f = st.split("\\s+");
            rollSpeed.add(Float.parseFloat(f[23].replace(',', '.'))); //pk 23 ?
        }
        int division = rollSpeed.size() / 5;
        java.lang.System.out.println("Division" + division);
        int rest = rollSpeed.size() % 5;
        ArrayList<Float> mean5 = new ArrayList<Float>();
        mean5.clear();
        for (int i = 0; i < division; i++) {
            float sum = 0;
            for (int j = 0; j < 5; j++) {
                sum += rollSpeed.get(i + j);
            }
            mean5.add(sum / 5);
        }
        if (rest != 0) {
            float sum = 0;
            for (int i = rollSpeed.size() - rest; i < rollSpeed.size(); i++) {
                sum += rollSpeed.get(i);
            }
            mean5.add(sum / rest);
        }
        java.lang.System.out.println("mean list :" + mean5);
        return mean5;
    }

    //Calculate the average of the 5 values of the "friction coefficient" indicators
    public ArrayList<Float> frictionCoeff() throws FileNotFoundException, InterruptedException, SQLException {
        ArrayList<Float> friction = get_param(0); //get_param(0)
        int division = friction.size() / 5;
        //System.out.println(division);
        int reste = friction.size() % 5;
        //System.out.println(reste);
        ArrayList<Float> moy5 = new ArrayList<Float>();
        moy5.clear();
        for (int i = 0; i < division; i++) {
            float sum = 0;
            for (int j = 0; j < 5; j++) {
                sum += friction.get(i + j);
            }
            moy5.add(sum / 5);
        }
        if (reste != 0) {
            float sum = 0;
            for (int i = friction.size() - reste; i < friction.size(); i++) {
                sum += friction.get(i);
            }
            moy5.add(sum / reste);
        }
        java.lang.System.out.println("mean list :" + moy5);
        return moy5;
    }

    //Read the data of the file thrown by Orowan by the kind of integer shown, if it's sigma of friction coefficient
    public ArrayList<Float> get_param(int param) throws InterruptedException, FileNotFoundException, SQLException {
        ArrayList<String> lines = new ArrayList<String>(); //0
        ArrayList<Float> friction = new ArrayList<Float>(); //1
        ArrayList<Float> sigma = new ArrayList<Float>(); //2
        //ArrayList<Float> roll_speed = new ArrayList<Float>();
        lines.clear();
        friction.clear();

        TimeUnit.SECONDS.sleep(1);
        File file = new File("D:/S8/8.4/Fichiers/Model/output.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\Z");
        String st;
        String[] files;
        sc.nextLine();

        while (sc.hasNext()) {
            lines.clear();
            st = sc.nextLine();
            Scanner sc2 = new Scanner(st);
            sc2.useDelimiter("\\s");
            while (sc2.hasNext()) {
                String s = sc2.next();
                lines.add(s);
            }
            friction.add(Float.parseFloat(lines.get(3)));
            sigma.add(Float.parseFloat(lines.get(4)));
            //System.out.println(friction);
        }
        sc.close();
        if (param == 0) {
            return friction;
        } else {
            return sigma;
        }
        //file.delete();

    }
}