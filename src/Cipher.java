import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cipher {
    private Lfsr lfsr = new Lfsr();
    private TestingSeq testingSeq = new TestingSeq();
    private String seq = lfsr.generationM("key.txt", 200000,100);

    public String encryption(String textPath, String pathOut) throws IOException {

        String textBin = readBin(textPath);
        String resultBin = encrypt(textBin, seq);
        String resultOut = binaryToString(resultBin);
        writeFile(pathOut,resultOut);

        testingSeq.serialTest(textBin, 2);
        testingSeq.corTest(textBin, 2);
        testingSeq.pokerTest(textBin, 10);

        return resultOut;
    }

    public void task6(String textPath, String textOut) throws IOException {

        String textBin = readBin(textPath);
        String seq8 = seq.substring(0,8);
        String seqM = "";
        for (int i = 0; i < textBin.length() / 8; i++) {
            seqM+=seq8;
        }
//        if ( textBin.length() % 8> 0) {
//            seqM+=seq8.substring(0,textBin.length() % 8);
//        }

        String resultBin = encrypt(textBin, seqM); //зашифр.
        String resultOut = binaryToString(resultBin);
        writeFile(textOut,resultOut);

        double [] rk = new double [17];
        for (int i = 0; i < rk.length; i++) {
            rk[i] = testingSeq.corTest(resultBin, i);
        }
        Chart chart = new Chart(rk);
        chart.createGraph();
    }

    private String encrypt(String textBin, String seq){
        String resultBin = "";
        for (int i = 0; i < textBin.length(); i++) {
            resultBin+=(Character.getNumericValue(textBin.charAt(i)) ^ Character.getNumericValue(
                    seq.charAt(i)));
        }
       return resultBin;
    }

    private String readBin(String path) throws IOException {
        StringBuilder binaryStringBuilder = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            int c;
            while((c=reader.read())!=-1){
                String binary = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');
                binaryStringBuilder.append(binary);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return binaryStringBuilder.toString();
    }

    public String binaryToString(String binaryInput) {
        StringBuilder textBuilder = new StringBuilder();

        for (int i = 0; i < binaryInput.length(); i += 8) {
            String binaryChunk = binaryInput.substring(i, Math.min(i + 8, binaryInput.length()));
            int charCode = Integer.parseInt(binaryChunk, 2);
            char character = (char) charCode;
            textBuilder.append(character);
        }
        return textBuilder.toString();
    }

    public void writeFile(String filename, String text){
        try (FileWriter fileWriter = new FileWriter(filename, StandardCharsets.UTF_8)){
            fileWriter.write(text);
            fileWriter.flush();
        }catch(
                IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
