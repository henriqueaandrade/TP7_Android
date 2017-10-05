package infnet.edu.br.tp7android.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by joaoluisdomingosxavier on 03/10/17.
 */

public class SaveOrOpenTextFile {

    private static final String FILE_NAME = "resultados.txt";
    private String UTF8 = "utf8";
    private int BUFFER_SIZE = 8192;
  //  private FileOutputStream fileOutputStream;

    public void saveTxt(int day, int month, int year, int hour, int minute, int dependents, float alimore,
            float healthy, float other, double salary, double discount_sum, double inss,
                        double irpf, Context context) {

        try {

            if (!isExternalStorageWritable()) {
                Toast.makeText(context.getApplicationContext(),
                                "Não foi possível obter permissões de leitura e/ou escrita.",
                                Toast.LENGTH_LONG)
                                .show();
            } else {

                // get the directory
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS), FILE_NAME);
                // if the directory is not created, so it will create
                if (!file.exists()) {
                    file.mkdir();
                }

                FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, UTF8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter, BUFFER_SIZE);

                bufferedWriter.write("Data: " + day);
                bufferedWriter.write("/" + month);
                bufferedWriter.write("/" + year);
                bufferedWriter.write(" Hora: " + hour);
                bufferedWriter.write(" e " + minute + " minutos.");
                bufferedWriter.write(" Dependentes: " + dependents);
                bufferedWriter.write(" Pensão alimentícia: " + alimore);
                bufferedWriter.write(" Plano de saúde: " + healthy);
                bufferedWriter.write(" Outro: " + other);
                bufferedWriter.write(" Salário Líquido: " + salary);
                bufferedWriter.write(" Total de Descontos: " + discount_sum);
                bufferedWriter.write(" Porcentagem de desconto INSS: " + inss);
                bufferedWriter.write(" Porcentagem de desconto IRPF: " + irpf);

                bufferedWriter.close();
            } // end else isExternalStorageWritable()

        } catch (IOException e) {
            Log.i("Erro: ", e.toString());
            Toast.makeText(
                    context.getApplicationContext(),
                    "Não foi possível salvar txt " + e.toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    } // end saveTxt()

    public String openTxt(Context context) {
        String result = "";
        try {

            InputStream inputStream = context.openFileInput(FILE_NAME);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                // generate buffer
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // recover data
                String lineFile = "";
                while ((lineFile = bufferedReader.readLine()) != null) {
                    result = lineFile;
                }
                inputStream.close();
            }

        } catch (IOException e) {
            Log.i("Erro ao ler txt", e.toString());
        }

        return result;
    } // End openTxt()

    public void deleteTxt(Context context){
        context.deleteFile(FILE_NAME);
    }

    // check if external storage is available for write and read
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
