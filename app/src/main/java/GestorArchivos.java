/**
 * Created by federicolizondo on 07/12/15.
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GestorArchivos {
    private static GestorArchivos ourInstance = new GestorArchivos();

    public static GestorArchivos getInstance() {
        return ourInstance;
    }


    private GestorArchivos() {

    }

    public static void setContexto(Context c) {

    }

    public static void guardarArchivo(JSONObject jsonObject, String nombreArchivo, String direccionArchivo, Context ctx) {
        if ((jsonObject != null || jsonObject.length() > 0) && ctx != null) {
            try {
                OutputStreamWriter osw;
                if (direccionArchivo != null && !direccionArchivo.isEmpty()) {
                    File tarjeta = Environment.getExternalStorageDirectory();
                    File file = new File(tarjeta.getAbsolutePath(), nombreArchivo);
                    osw = new OutputStreamWriter(new FileOutputStream(file));
                } else {
                    FileOutputStream fileOutputStream = ctx.openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
                    osw = new OutputStreamWriter(fileOutputStream);
                }

                osw.write(jsonObject.toString());
                osw.flush();
                osw.close();

            } catch (FileNotFoundException e) {
                Log.e("", e.getMessage());
            } catch (IOException e) {
                Log.e("", e.getMessage());
            }

        }
    }

    public static String cargarArchivo(String nombreArchivo, String direccionArchivo, Context ctx) {
        String s = "";
        try {

            FileInputStream fis;
            if (direccionArchivo != null && !direccionArchivo.isEmpty()) {
                File tarjeta = Environment.getExternalStorageDirectory();
                File file = new File(tarjeta.getAbsolutePath(), nombreArchivo);
                fis = new FileInputStream(file);

            } else {
                fis = ctx.openFileInput(nombreArchivo);
            }
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();
            while (linea != null) {
                s += linea;
                linea = br.readLine();
            }
            br.close();
            isr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
