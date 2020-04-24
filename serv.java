import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
// ******************** Aplica��o Entregue em 29/06/2005 **********

public class serv {

    int pt;
    ServerSocket ssocket;
    String lercaixa;
    PrintStream soutput;
    DataInputStream sinput;
    Socket csk, clisoket;

    @SuppressWarnings("deprecation")
    public serv() {
        //Programa Servidor
        pt = 4321;
        try {
            ssocket = new ServerSocket(pt);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        System.out.println("Servidor Ativo ..... ");

        try {
            while (true) {

                String[] palavras = {"Wagner", "CocaCola", "Leandro", "Mariana", "Amadeu"};
                int quant_palavras = palavras.length;
                int N_vida = 6;
                clisoket = ssocket.accept();
                csk = clisoket;
                soutput = new PrintStream(csk.getOutputStream());  // Envia uma sequencia de bytes para Cliente
                sinput = new DataInputStream(csk.getInputStream()); // Captura uma sequencia de bytes enviada pelo cliente

                //LOGIN
                boolean login_errado = true;
                String usuario = "";
                String senha = "";

                while (login_errado) {
                    soutput.println("Digite o usuario");
                    usuario = sinput.readLine();
                    soutput.println("Digite a senha");
                    senha = sinput.readLine();
                    if (usuario.equals("redes") && senha.equals("univap")) {
                        soutput.println("foi");
                        login_errado = false;
                    } else {
                        soutput.println("naofoi");
                        login_errado = true;
                    }
                }

                File file = new File("./registro.txt");

                if (file.createNewFile()) {
                    System.out.println("Arquivo de registro criado com sucesso");
                } else {
                    System.out.println("Arquivo de registro existente");
                }

                Writer outputStream = new BufferedWriter(new FileWriter("registro.txt", true));

                outputStream.append("Usuario:");
                outputStream.append(usuario);
                outputStream.append("\n");
                outputStream.append("Data e hora:");
                outputStream.append(getCurrentTimeStamp());
                outputStream.append("\n");
                outputStream.close();

                Random gerador = new Random();
                soutput.println("Digite iniciar para começar");

                String mensagem = sinput.readLine(); // pega a linha Enviada pelo Cliente
                while ("iniciar".equals(mensagem)) {
                    N_vida = 6;
                    soutput.println("naosair");
                    String palavra = palavras[gerador.nextInt(quant_palavras)].toUpperCase();
                    String hash = String.format("%" + palavra.length() + "s", " ").replace(' ', '#');
                    while (N_vida > 0) {
                        soutput.println(N_vida);
                        soutput.println(hash);

                        if (hash.indexOf("#") == -1) {
                            soutput.println("ganhou");
                            break;
                        } else {
                            soutput.println("naoganhou");
                        }

                        soutput.println("Digite a letra");
                        String tentativa = sinput.readLine().toUpperCase(); //aqui entra a letra e converte para MAIUSCULA
                        int index = 0;
                        String temp = hash;
                        while ((index = palavra.indexOf(tentativa)) > -1) {
                            hash = hash.substring(0, index) + tentativa + hash.substring(index + 1);
                            palavra = palavra.substring(0, index) + ' ' + palavra.substring(index + 1);
                        }
                        if (temp.equals(hash)) {
                            N_vida--;
                        }
                        if (N_vida == 0) {
                            soutput.println("perdeu");
                        } else {
                            soutput.println("naoperdeu");
                        }
                    }
                    soutput.println("Digite iniciar para jogar novamente ou sair para finalizar:");
                    mensagem = sinput.readLine();
                }
                soutput.println("sairjogo");
                soutput.println("Obrigado por jogar! Espero que tenha se divertido!");

                csk.close();
            }

            // System.exit(0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getCurrentTimeStamp() {
        //SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static void main(String args[]) {
        new serv();
    }

}
