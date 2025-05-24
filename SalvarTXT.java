import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class SalvarTXT {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        List lista = new List();
        try (FileInputStream fileIn = new FileInputStream("lista.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            lista = (List) in.readObject();  // Corrigido: não redeclarar
            System.out.println("Lista carregada com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de lista não encontrado, iniciando nova lista.");
        }

        while (true) {
            System.out.println("======================================");
            System.out.println("= 1 - Cadastrar novo documento       =");
            System.out.println("= 2 - Listar documentos cadastrados  =");
            System.out.println("= 3 - Buscar por palavra-chave       =");
            System.out.println("= 4 - Estatísticas e ordenação       =");
            System.out.println("= 5 - Gerenciar índice ou compressão =");
            System.out.println("= 0 - Sair                           =");
            System.out.println("======================================");
            int opt = scan.nextInt();
            scan.nextLine();

            if (opt == 1) {
                System.out.println(" = Digite o nome do arquivo: ");
                String nomeDoArquivo = scan.nextLine();

                try {
                    System.out.println("=================");
                    System.out.println("= Conteudo  TXT =");
                    System.out.println("=================");
                    String conteudo = scan.nextLine();
                    
                    System.out.println("=======================================");
                    System.out.println("= Você confirma a criação do arquivo? =");
                    System.out.println("= 1 - Sim                             =");
                    System.out.println("= 2 - Não                             =");
                    System.out.println("=======================================");
                    int conf = scan.nextInt();
                    if (conf == 1) {
                        
                        FileWriter escritor = new FileWriter("dados/"+nomeDoArquivo);
                        escritor.write(conteudo);
                        escritor.close();

                        System.out.println("=================");
                        System.out.println("= S U C E S S O =");
                        System.out.println("=================");

                        lista.addNode(nomeDoArquivo);
                        
                    } else {
                        
                        System.out.println("=================");
                        System.out.println("=   F A L H A   =");
                        System.out.println("=================");
                    }

                } catch (IOException e) {
                    System.out.println("=================");
                    System.out.println("=   F A L H A   =");
                    System.out.println("=================");
                    e.printStackTrace();
                }
            }
            else if (opt == 2) {
                lista.showList();
            }
            else if (opt == 3) {
                System.out.println("Digite a palavra-chave:");
                String palavra = scan.nextLine().toLowerCase();

                No temp = lista.start;
                boolean achou = false;

                while (temp != null) {
                    try {
                        Scanner leitor = new Scanner(new File("dados/" + temp.indice));
                        while (leitor.hasNextLine()) {
                            String linha = leitor.nextLine().toLowerCase();
                            if (linha.contains(palavra)) {
                                System.out.println("Palavra encontrada em: " + temp.indice);
                                achou = true;
                                break;
                            }
                        }
                        leitor.close();
                    } catch (IOException e) {
                        System.out.println("Erro ao abrir: " + temp.indice);
                    }
                    temp = temp.next;
                }

                if (!achou) {
                    System.out.println("Palavra não encontrada em nenhum arquivo.");
                }
            }
            else if (opt == 4) {
                System.out.println("=== Estatísticas ===");

                // Contagem
                int total = 0;
                No temp = lista.start;
                while (temp != null) {
                    total++;
                    temp = temp.next;
                }

                System.out.println("Total de documentos: " + total);

                // Cópia da lista para ordenação (por nome)
                No ordenada = null;
                temp = lista.start;
                while (temp != null) {
                    No novo = new No(temp.indice);
                    if (ordenada == null || novo.indice.compareTo(ordenada.indice) < 0) {
                        novo.next = ordenada;
                        ordenada = novo;
                    } else {
                        No atual = ordenada;
                        while (atual.next != null && novo.indice.compareTo(atual.next.indice) > 0) {
                            atual = atual.next;
                        }
                        novo.next = atual.next;
                        atual.next = novo;
                    }
                    temp = temp.next;
                }

                // Impressão dos arquivos ordenados
                No atual = ordenada;
                while (atual != null) {
                    File f = new File("dados/" + atual.indice);
                    long tamanho = f.length();
                    System.out.println("- " + atual.indice + " (" + tamanho + " bytes)");
                    atual = atual.next;
                }
            }
            else if (opt == 5) {
                System.out.println("=== Gerenciamento de Compressão ===");

                No temp = lista.start;
                int count = 0;
                while (temp != null) {
                    File f = new File("dados/" + temp.indice);
                    long size = f.length();
                    System.out.println("Simulando compressão de: " + temp.indice + " (" + size + " bytes)");
                    count++;
                    temp = temp.next;
                }

                if (count == 0) {
                    System.out.println("Nenhum documento para comprimir.");
                } else {
                    System.out.println("Simulação de compressão concluída.");
                }
            }
            else if (opt == 0) {
                break;
            }
            else {
                System.out.println("==================");
                System.out.println("= VALOR INVALIDO =");
                System.out.println("==================");
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("lista.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(lista);  // serializa o objeto lista completo
            System.out.println("=================");
            System.out.println("= S U C E S S O =");
            System.out.println("=================");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}