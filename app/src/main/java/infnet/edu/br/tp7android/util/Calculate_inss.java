package infnet.edu.br.tp7android.util;

import android.util.Log;

/**
 * Created by joaoluisdomingosxavier on 03/10/17.
 */

public class Calculate_inss {

    private double fist_value = 0.08;
    private double second_value = 0.09;
    private double third_value = 0.011;
    private double fourth_value = 608.44;
    private double discount_percentage;

    // CONSTRUCTOR
    public Calculate_inss(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    // GETTER
    public double getFist_value() {
        return fist_value;
    }

    public double getSecond_value() {
        return second_value;
    }

    public double getThird_value() {
        return third_value;
    }

    public double getFourth_value() {
        return fourth_value;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    //SETTER
    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public double calculate (double salary) {
        double discount;
        if (salary <= 1659.38) {
            discount = salary * getFist_value();
            setDiscount_percentage(getFist_value());

        } else if (salary > 1659.39 && salary < 2765.66 ) {
            discount = salary * getSecond_value();
            setDiscount_percentage(getSecond_value());

        } else if (salary > 2765.67 && salary < 5531.31 ) {
            discount = salary * getThird_value();
            setDiscount_percentage(getThird_value());

        } else {
            discount = getFourth_value();
            setDiscount_percentage( ( salary / discount)  * 100);

        }
        Log.i("joao", "INSS: " + discount);
        return discount;
    }
}
