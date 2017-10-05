package infnet.edu.br.tp7android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import infnet.edu.br.tp7android.R;
import infnet.edu.br.tp7android.util.SaveOrOpenTextFile;

public class ResultActivity extends AppCompatActivity {

    private TextView edit_salary_with_descount;
    private TextView edit_total_discount;
    private TextView edit_discount_inss;
    private TextView edit_discount_irpf;
    private TextView edit_txt;
    private Button btn_back;
    private Button btn_delete;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        edit_salary_with_descount       = (TextView) findViewById(R.id.text_salary_with_descount);
        edit_total_discount             = (TextView) findViewById(R.id.text_total_descount);
        edit_discount_inss              = (TextView) findViewById(R.id.text_descount_porcentage_inss);
        edit_discount_irpf              = (TextView) findViewById(R.id.text_descount_porcentage_irpf);
        edit_txt                        = (TextView) findViewById(R.id.text_txt);
        btn_back                        = (Button) findViewById(R.id.btn_back);
        btn_delete                      = (Button) findViewById(R.id.btn_delete);

        // recovering data from MainActivity
        double salary           = getIntent().getExtras().getDouble("salary");
        double discount         = getIntent().getExtras().getDouble("discount");
        double inss             = getIntent().getExtras().getDouble("inss_discount_percentage");
        double irpf             = getIntent().getExtras().getDouble("irpf_discount_percentage");

        // inserting data saved in txt in the TextView
        edit_salary_with_descount.setText(String.valueOf(salary));
        edit_total_discount.setText(String.valueOf(discount));
        edit_discount_inss.setText(String.valueOf(inss) + "%");
        edit_discount_irpf.setText(String.valueOf(irpf) + "%");

        // Opening saved txt
        final SaveOrOpenTextFile saveOrOpenTextFile = new SaveOrOpenTextFile();
        edit_txt.setText(saveOrOpenTextFile.openTxt(getApplicationContext()));

        // Delete txt
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting up alert asking if he/she is sure to delete
                AlertDialog.Builder deletar = new AlertDialog.Builder(ResultActivity.this);

                // Alert title
                deletar.setTitle("Deletar .txt");
                // alert message
                deletar.setMessage("Você tem certeza que deseja deletar este arquivo .txt? " +
                        "Não será possível recuperar");

                // if user pressed delete
                deletar.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        saveOrOpenTextFile.deleteTxt(getApplicationContext());
                        edit_txt.setText(saveOrOpenTextFile.openTxt(getApplicationContext()));

                        Toast.makeText(getApplicationContext(),
                                       "txt apagada com sucesso!",
                                       Toast.LENGTH_SHORT)
                                       .show();
                    }
                }); // End PositiveButton

                // if user pressed cancel
                deletar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }); // End NegativeButton

                deletar.create();
                deletar.show();

            } // End OnClick
        }); // End btn_delete onClickListener

        // Go back for MainActivity
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } // end OnClick
        }); // End btn_back onClickListener

    }
}
