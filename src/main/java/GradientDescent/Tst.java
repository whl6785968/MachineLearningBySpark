package GradientDescent;

public class Tst {
    public static void main(String[] args) {
        Double poi = 8.0/13.0;

        Double ent_p = Math.log(poi)/Math.log(2.0);
        Double neg = 5.0/13.0;
        Double ent_n = Math.log(neg)/Math.log(2.0);

        Double ent = -(poi*ent_p + neg*ent_n);
        Double gain = 0.998 - (13.0/17.0)*ent;

        System.out.println(gain);
    }
}
