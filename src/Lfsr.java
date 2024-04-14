import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Lfsr {
    private int[] register;
    private final int[] polynom = {31,6};
    private int [] startSeq;
    private int registerLength = polynom[0];

    private int xor;
    private String result = "";

    public String generationM(int n, int lengthOutput){
        generationStartSeq();
        register = startSeq;
        //register = new int[]{1,1,1,1};
        result ="";

        for (int i = 0; i < n; i++) {
            xor = 0;
            for (int j = 0; j < polynom.length; j++) {
                int index = registerLength-polynom[j];
                xor^=register[index];
            }
            result += register[registerLength-1];

            for (int j = registerLength-1; j >0; j--) {
                register[j] = register[j-1];
            }
            register[0] = xor;
        }

        System.out.print("M-sequence: ");
        for (int i = 0; i <lengthOutput ; i++) {
            System.out.print(result.charAt(i));
        }
        System.out.print("\n");

        return result;
    }

    public String generationM(String filename, int n, int lengthOutput){
        register = new int[registerLength];

        try(FileReader reader = new FileReader(filename))
        {
            int c; int i=0;
            while((c=reader.read())!=-1 && i < registerLength){
                register[i] = Character.getNumericValue(c);
                i++;
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        System.out.print("The starting sequence from the file: ");
        for (int i = 0; i < registerLength; i++) {
            System.out.print(register[i]);
        }
        System.out.println();

        result ="";
;
        for (int i = 0; i < n; i++) {
            xor = 0;
            for (int j = 0; j < polynom.length; j++) {
                int index = registerLength-polynom[j];
                xor^=register[index];
            }
            result += register[registerLength-1];

            for (int j = registerLength-1; j >0; j--) {
                register[j] = register[j-1];
            }
            register[0] = xor;
        }

        System.out.print("M - sequence: ");
        for (int i = 0; i <lengthOutput ; i++) {
            System.out.print(result.charAt(i));
        }
        System.out.print("\n");

        return result;
    }

    public void generationStartSeq(){
        Random random = new Random();
        int startSeqLength = polynom[0];
        startSeq = new int[startSeqLength];
        for (int i = 0; i < startSeqLength; i++) {
            startSeq[i] = random.nextInt(2);
        }

        System.out.print("The starting sequence: ");
        for (int i = 0; i < startSeqLength; i++) {
            System.out.print(startSeq[i]);
        }
        System.out.println();

        try (FileWriter fileWriter = new FileWriter("key.txt", StandardCharsets.UTF_8)){
            for (int i = 0; i < startSeq.length; i++) {
                fileWriter.write(Integer.toString(startSeq[i]));
                fileWriter.flush();
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
