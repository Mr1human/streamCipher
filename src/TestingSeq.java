import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestingSeq {
    private DecimalFormat df = new DecimalFormat("#.#####");

    public void serialTest(String seq, int k){
        Map<String, Integer> series = new HashMap<>();
        double refFreq = seq.length()/(k*Math.pow(2,k));
        double criterionPear = 0;

        for (int i = 0; i <=seq.length()-k; i+=k) {
            String substring = seq.substring(i,i+k);

            if (series.containsKey(substring)){
                int count = series.get(substring);
                series.put(substring, count+1);
            }else{
                series.put(substring, 1);
            }
        }

        for(String s: series.keySet()){
            criterionPear += Math.pow(series.get(s)-refFreq,2)/refFreq;
        }

        if(series.size()<Math.pow(2,k)){
            for (int i = 0; i < Math.pow(2,k) - series.size(); i++) {
                criterionPear += Math.pow(0-refFreq,2)/refFreq;
            }
        }

        System.out.println("\n" + "-------Serial test-------");
        System.out.println("Sequence length = " + seq.length());
        System.out.println("Emp. series frequencies:" + series);
        System.out.println("Pearson's Criterion:" + df.format(criterionPear));
        System.out.println("alpha_min = 0.1; alpha_max = 0.9");

        //альфа_мин = 0,1; альфа_мах = 0.9

        switch (k){
            case 2:
                if((0.584 <= criterionPear) && (criterionPear <= 6.251)){
                    System.out.println(df.format(criterionPear) + " belongs to the interval [0.584, 6.251]");
                    System.out.println("Ho - test is passed");
                } else {
                    System.out.println(df.format(criterionPear) + " doesn't belong to the interval [0.584, 6.251]");
                    System.out.println("H1 - test failed");
                }
                break;

            case 3:
                if((2.833 <= criterionPear) && (criterionPear <= 12.017)){
                    System.out.println(df.format(criterionPear) + " belongs to the interval [2.833, 12.017]");
                    System.out.println("Ho - test is passed");
                } else {
                    System.out.println(df.format(criterionPear) + " doesn't belong to the interval [2.833, 12.017]");
                    System.out.println("H1 - test failed");
                }
                break;

            case 4:
                if((8.547 <= criterionPear) && (criterionPear <= 22.307)){
                    System.out.println(df.format(criterionPear) + " belongs to the interval [8.547, 22.307]");
                    System.out.println("Ho - test is passed");
                } else {
                    System.out.println(df.format(criterionPear) + " doesn't belong to the interval [8.547, 22.307]");
                    System.out.println("H1 - test failed");
                }
                break;
        }
        System.out.println();
    }

    public double corTest(String seq, int k){
        int N = seq.length();

        double mi = 0;
        for (int i = 0; i < N-k; i++) {
            mi+=Integer.valueOf(seq.charAt(i));
        }
        mi = mi*(1.0/(N-k));

        double mik = 0;
        for (int i = k+1; i < N; i++) {
            mik+=Integer.valueOf(seq.charAt(i));
        }
        mik = mik*(1.0/(N-k));

        double dispX =0;
        for (int i = 0; i < N-k; i++) {
            dispX+=Math.pow(Integer.valueOf(seq.charAt(i)) - mi,2);
        }
        dispX = dispX*(1.0/(N-k-1));

        double dispXk =0;
        for (int i = k+1; i < N; i++) {
            dispXk+=Math.pow(Integer.valueOf(seq.charAt(i)) - mik,2);
        }
        dispXk = dispXk*(1.0/(N-k-1));

        double rk = 0;
        for (int i = 0; i < N-k; i++) {
            rk+=((Integer.valueOf(seq.charAt(i)) - mi) *
                    (Integer.valueOf(seq.charAt(i+k)) - mik));
        }
        rk = Math.abs((rk * (1.0/(N-k)) *  (1/Math.sqrt(dispX*dispXk))));
        double criticalR = (1.0 / (N - 1)) + (2.0 / (N - 2)) * Math.sqrt((double) N * (N - 3) / (N + 1));
        //double criticalR = 0.00448;
        System.out.println("-------Correlation test-------");
        System.out.println("Rk = " + df.format(rk));
        System.out.println("R_critical = "+ df.format(criticalR));

        if ( rk <= criticalR){
            System.out.println(df.format(rk) + " <= " + df.format(criticalR) +'\n'+ "Ho - test is passed");
        }else if(rk > criticalR){
            System.out.println(df.format(rk) + " > " + df.format(criticalR) +'\n'+ "H1 - test failed");
        }
        return rk;
    }

    public void pokerTest(String seq, int q){
        //int [] ui = new int[seq.length()/32];
        String res = "";
        int j = 0;
        for (int i = 0; i < seq.length()/32; i++) {
            String substring = seq.substring(j,j+32);
            int xi = Integer.parseUnsignedInt(substring, 2);
            double ri = xi/(Math.pow(2,32)-1);
            //ui[i] = (int) Math.floor(q*(ri+0.5));
            res+=(int) Math.floor(q*(ri+0.5));
            j+=32;
            //System.out.print(ui[i]+" ");
        }

        System.out.println("\n" +"-----------------Poker test---------------");
        //System.out.println(res);

        int [] quintets = new int[7]; // нумерация как в методичке
        for (int i = 0; i <=res.length()-5; i+=5) {
            String substring = res.substring(i,i+5);
            int dif = (int)substring.chars().distinct().count();

            if (dif == 5){
                quintets[0]++;
            } else if (dif == 4) {
                quintets[1]++;
            } else if (dif == 3) {
                if (maxInput(substring) == 2){
                    quintets[2]++;
                } else{
                    quintets[3]++;
                }
            } else if (dif == 2) {
                if (maxInput(substring) == 3){
                    quintets[4]++;
                } else{
                    quintets[5]++;
                }
            }else if (dif == 1){
                quintets[6]++;
            }
        }
        System.out.println(Arrays.toString(quintets));

        double [] quintetsRef = new double[7];
        quintetsRef[0] = (res.length()/5)*((q-1)*(q-2)*(q-3)*(q-4))/Math.pow(q,4);
        quintetsRef[1] = (res.length()/5)*10*((q-1)*(q-2)*(q-3))/Math.pow(q,4);
        quintetsRef[2] = (res.length()/5)*15*((q-1)*(q-2))/Math.pow(q,4);
        quintetsRef[3] = (res.length()/5)*10*((q-1)*(q-2))/Math.pow(q,4);
        quintetsRef[4] = (res.length()/5)*10*(q-1)/Math.pow(q,4);
        quintetsRef[5] = (res.length()/5)*5*(q-1)/Math.pow(q,4);
        quintetsRef[6] = (res.length()/5)*1/Math.pow(q,4);

        double crPear = 0;
        for (int i = 0; i < quintets.length; i++) {
            crPear+=Math.pow((quintets[i] - quintetsRef[i]),2)/quintetsRef[i];
        }
        System.out.println(crPear);

        //αmin = 0,10 и верхний αmax = 0,9
        if (crPear>=2.200 && crPear<=10.640){
            System.out.println("Ho - test is passed " + "\n" +df.format(crPear)+
                    " belongs to the interval [2.200,10.640]");
        }else {
            System.out.println("H1 - test failed "+ "\n" +df.format(crPear)+
                    " doesn't belong to the interval [2.200,10.640]");
        }
    }

    private int maxInput(String string){
        int max =0;
        for (int k = 0; k < string.length(); k++) {
            int count = 0;
            for (int l = 0; l < string.length(); l++) {
                if (string.charAt(k) == string.charAt(l)) count++;
            }
            if (count>max) max = count;
        }
        return max;
    }

}

