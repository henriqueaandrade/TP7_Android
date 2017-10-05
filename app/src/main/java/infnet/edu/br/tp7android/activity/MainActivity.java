package infnet.edu.br.tp7android.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import infnet.edu.br.tp7android.R;
import infnet.edu.br.tp7android.util.Calculate_inss;
import infnet.edu.br.tp7android.util.Calculate_irpf;
import infnet.edu.br.tp7android.util.SaveOrOpenTextFile;

public class MainActivity extends AppCompatActivity {

    private EditText edit_salary;
    private EditText edit_dependents;
    private EditText edit_alimory;
    private EditText edit_other;
    private EditText edit_healthy;
    private Button btn_calculate;

    private Calendar calendar;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_salary             = (EditText) findViewById(R.id.edit_salary);
        edit_dependents         = (EditText) findViewById(R.id.edit_dependents);
        edit_alimory            = (EditText) findViewById(R.id.edit_alimony);
        edit_healthy            = (EditText) findViewById(R.id.edit_healthy);
        edit_other              = (EditText) findViewById(R.id.edit_other);
        btn_calculate           = (Button) findViewById(R.id.btn_calcular);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Casting user settings
                final float salary            = Float.parseFloat(edit_salary.getText().toString());
                final int dependents          = Integer.parseInt(edit_dependents.getText().toString());
                final float alimory           = Float.parseFloat(edit_alimory.getText().toString());
                float healthy                 = Float.parseFloat(edit_healthy.getText().toString());
                float other                   = Float.parseFloat(edit_other.getText().toString());

                // Verify if salary is empy
                if (edit_salary.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Por favor preencha o salário bruto",
                            Toast.LENGTH_LONG).show();
                } else {
                    /* If salary have something...

                    This is the formula:
                    Salário Líquido = Salário Bruto – INSS – IRPF – Pensão Alimentícia – Número de dependentes * R$ 189,59
                    Calculating user salary with all discounts
                    Initializing methods                                                        */
                    Calculate_inss calculate_inss = new Calculate_inss(salary);
                    Calculate_irpf calculate_irpf = new Calculate_irpf(salary);
                    // Applying the formula
                    double salary_with_discount = salary - calculate_inss.calculate(salary) -
                            calculate_irpf.calculate(salary) - alimory - dependents * 189.59;

                    // Getting calendar info
                    calendar = Calendar.getInstance(TimeZone.getDefault());
                    day = calendar.get(Calendar.DATE);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);
                    hour = calendar.get(Calendar.HOUR_OF_DAY);
                    minute = calendar.get(Calendar.MINUTE);

                    double discount_sum = calculate_inss.calculate(salary) + calculate_irpf.calculate(salary);

                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

                    // Sending data for another activity
                    intent.putExtra("salary", salary_with_discount);
                    intent.putExtra("discount", discount_sum);
                    intent.putExtra("inss_discount_percentage", calculate_inss.getDiscount_percentage());
                    intent.putExtra("irpf_discount_percentage", calculate_irpf.getDiscount_percentage());

                    // Saving txt
                    SaveOrOpenTextFile saveOrOpenTextFile = new SaveOrOpenTextFile();
                    saveOrOpenTextFile.saveTxt(
                            day,
                            month,
                            year,
                            hour,
                            minute,
                            dependents,
                            alimory,
                            healthy,
                            other,
                            salary_with_discount,
                            discount_sum,
                            calculate_inss.getDiscount_percentage(),
                            calculate_irpf.getDiscount_percentage(),
                            getApplicationContext()
                    );

                    startActivity(intent);
                } // End Else

            } // end onClick
        }); // End btn_calculate

    } // End onCreate

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
