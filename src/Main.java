
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Lfsr lfsr = new Lfsr();
        String seq = lfsr.generationM(200000,1000);//генерация последовательности

        TestingSeq testingSeq = new TestingSeq();//тестирование последовательности
        testingSeq.pokerTest(seq, 10);//покер тест
        testingSeq.serialTest(seq, 2);//сериальный тест
        testingSeq.corTest(seq, 2);//корреляционный тест

        Cipher cipher = new Cipher();
        cipher.encryption("text.txt", "encryption.txt"); //шифрование текста
        cipher.task6("text.txt", "1.txt");// график автокорреляционной функции R[k] с глубиной статистической связи

    }
}
