package infnet.edu.br.tp7android.util;

import android.util.Log;

/**
 * Created by joaoluisdomingosxavier on 03/10/17.
 */

public class Calculate_irpf {

    private double first_aliquota = 0;
    private double second_aliquota = 0.075;
    private double third_aliquota = 0.15;
    private double fourth_aliquota = 0.225;
    private double fifth_aliquota = 0.275;
    private double discount_percentage;

    // CONSTRUCTOR
    public Calculate_irpf(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    //GETTER
    public double getFirst_aliquota() {
        return first_aliquota;
    }

    public double getSecond_aliquota() {
        return second_aliquota;
    }

    public double getThird_aliquota() {
        return third_aliquota;
    }

    public double getFourth_aliquota() {
        return fourth_aliquota;
    }

    public double getFifth_aliquota() {
        return fifth_aliquota;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    // SETTER
    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public double calculate(float salary) {
        double aliquota;
        if (salary <= 1903.98) {
            aliquota = getFirst_aliquota();
            setDiscount_percentage(getFirst_aliquota());

        } else if (salary > 1903.99 && salary < 2826.65 ) {
            aliquota = salary * getSecond_aliquota();
            setDiscount_percentage(getSecond_aliquota());

        } else if (salary > 2826.66 && salary < 3751.05 ) {
            aliquota = salary * getThird_aliquota();
            setDiscount_percentage(getFourth_aliquota());

        } else if (salary > 3751.06 && salary < 4664.68 ) {
            aliquota = salary * getFourth_aliquota();
            setDiscount_percentage(getFourth_aliquota());

        } else {
            aliquota = salary * getFifth_aliquota();
            setDiscount_percentage(getFifth_aliquota()
            );
        }
        Log.i("joao", "IRPF: " + aliquota);
        return aliquota;
    }
}
