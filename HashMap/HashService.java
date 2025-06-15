package HashMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HashService {
    HashMap hash;

    public HashService() {
        try (FileInputStream fileIn = new FileInputStream("dados/objs/hash.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            hash = (HashMap) in.readObject();
            System.out.println("Hash carregada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de Hash não encontrado, iniciando novo Hash.");
            hash = new HashMap();
        }
    }

    public void addword(String word, String title, String ocur) {
        hash.addWord(word, title, ocur);
    }

    public void removeWord(String word, String title) {
        hash.removeWord(word, title);
    }

    public String wordOcur(String word) {
        return hash.wordOccurrences(word);
    }

    public String hashString() {
        return hash.hashArray();
    }

    public String saveHash() {
        try (FileOutputStream fileOut = new FileOutputStream("dados/objs/hash.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(hash);
            return "HashMap salvo com sucesso!";
        } catch (IOException e) {
            return "Problemas ao salvar o HashMap!";
        }
    }
}
