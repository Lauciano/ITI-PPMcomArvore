
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Leitor {

    private DataInputStream data;
    private byte[] simbolo;
    private static boolean proximo = true;
    private static byte bitfinal;
    private static int bitfinal_aux = -1;
    private static int index = 0;
    private static final int size_simbolo = 1000;
    private String nomeArq;

    public Leitor(String nomeArq) throws FileNotFoundException, IOException {

        this.nomeArq = nomeArq;
        data = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArq)));
        simbolo = new byte[5];

    }

    public ArrayList<Byte> getAlfabeto() throws FileNotFoundException, IOException {

        int i, j;
        byte[] simbolo_aux = new byte[1000];
        ArrayList<Byte> alfabeto = new ArrayList<Byte>();

        DataInputStream data_aux = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArq)));

        while ((i = data_aux.read(simbolo_aux)) > -1) {
            for (j = 0; j < i; j++) {
                if (!alfabeto.contains(simbolo_aux[j])) {
                    alfabeto.add((byte) (simbolo_aux[j] & 0xff));
                }

            }
        }

        Collections.sort(alfabeto);

        data_aux.close();

        return alfabeto;

    }

    public Byte getNextByte() throws IOException {

        if (proximo) {
            if ((bitfinal = (byte) data.read(simbolo)) > -1) {
                index = -1;
                proximo = false;
            } else {
                data.close();
                return null;
            }
        }
        
        if (index < size_simbolo-2) {
            if (bitfinal != size_simbolo) {
                if (bitfinal_aux < bitfinal-1) {
                    bitfinal_aux++;
                    return simbolo[bitfinal_aux];
                } else {
                    data.close();
                    return null;
                }
            }
            index++;
            //System.out.println((char) simbolo[index]);
            return simbolo[index];
            
        }
        index++;
        proximo = true;
        //System.out.println((char) simbolo[index]);
        
        return simbolo[index];
        

    }
}
