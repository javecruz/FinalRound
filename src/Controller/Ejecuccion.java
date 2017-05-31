package Controller;

import Model.Habitacion;
import Model.Hospital;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejecuccion {

    static Hospital hospi = new Hospital();

    static Habitacion[][] array = hospi.getHab();

    public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {

        //voy a cargar el fichero de datos en un objeto hospital que es un array de habitaciones
        try {
            FileReader flS = null;

            Scanner tec = new Scanner(System.in);

            flS = new FileReader("datos.txt");
            BufferedReader fS = new BufferedReader(flS);

            String linea;
            linea = fS.readLine();

            int numero;
            float temperatura;

            for (int dia = 0; dia < 7; dia++) {

                for (int habNumero = 0; habNumero < 10; habNumero++) {
                    StringTokenizer tken = new StringTokenizer(linea, ":");
                    numero = Integer.parseInt(tken.nextToken());
                    temperatura = Float.parseFloat(tken.nextToken());

                    Habitacion hab = new Habitacion(numero, temperatura);
                    array[dia][habNumero] = hab;

                    linea = fS.readLine();

                }

            }

            // AQUI EL EJERCICIO 1
            float min = 0;
            float max = 0;

            Boolean[] dife = new Boolean[10];

            for (int habNumero = 0; habNumero < 10; habNumero++) {

                for (int dia = 0; dia < 7; dia++) {
                    if (dia == 0) {
                        min = array[dia][habNumero].getTemperatura();
                        max = array[dia][habNumero].getTemperatura();
                    } else {

                        if (min > array[dia][habNumero].getTemperatura()) {

                            min = array[dia][habNumero].getTemperatura();

                        }

                        if (max < array[dia][habNumero].getTemperatura()) {

                            max = array[dia][habNumero].getTemperatura();

                        }

                    }
                }

                if ((max - min) >= 3) {

                    dife[habNumero] = true;
                } else {
                    dife[habNumero] = false;

                }

                min = 0;
                max = 0;
            }

            for (int i = 0; i < dife.length; i++) {

                if (dife[i] == true) {
                    System.out.println("La habitacion:" + (i + 1) + " ha superado una diferencia de la semana de más de 3 grados.");
                }
            }

            // AQUI EMPIEZA EL EJERCICIO 2

            Boolean sw = false;

            int contador = 0;
            for (int habNumero = 0; habNumero < 10; habNumero++) {
                contador = 0; // reinciio
                for (int dia = 0; dia < 7; dia++) {

                    if (array[dia][habNumero].getTemperatura() > 38) {

                        contador++;
                        if (contador == 3) {
                    System.out.println("La habitacion " + (habNumero+1) + " ha superado 38 grados durante 3 dias seguidos");
                            
                        }

                    } else {
                        contador = 0;

                    }
                    

                }
                

            }


            // TERCER EJERCICIO PARA GUARDAR EN BASE DE DATOS
            
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.20.189:3306/examenFinal";
            String user = "root";
            String pass = "";
            Connection con = DriverManager.getConnection(url, user, pass);
   
            String query = "insert into registro(dia,habitacion,temperatura) values (?,?,?)"; // clave primaria es auto incrementable asi que no hace falta ponerla

            PreparedStatement st = con.prepareStatement(query);
            
            
            
            for (int dia = 0; dia < 7; dia++) {

                for (int habNumero = 0; habNumero < 10; habNumero++) {
                    st.setInt(1, dia);
                    st.setInt(2, habNumero);
                    st.setFloat(3, array[dia][habNumero].getTemperatura());
                    st.executeUpdate();

                }

            }
            
            // en el examen los dias del fichero estan representados como los dias de 1 semana, si tuvieramos dia-mes-año, creariamos un date y lo insertariamos como date

            con.close();
            
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejecuccion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
