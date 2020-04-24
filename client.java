import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

class client {

    DataInputStream cinput;
    PrintStream coutput;
    Socket clisoc;
    Scanner scan = new Scanner( System.in );

    @SuppressWarnings("deprecation")
    public client() {
        try {
            System.out.println("Digite o ip do servidor a se conectar:");
            String ip = scan.nextLine();
            System.out.println("Digite a porta do servidor a se conectar:");
            int porta = Integer.parseInt(scan.nextLine());
            clisoc = new Socket(ip,porta);
            cinput = new DataInputStream(clisoc.getInputStream());
            coutput = new PrintStream(clisoc.getOutputStream());

			//LOGIN
			boolean login_errado = true;
			while(login_errado) {
				System.out.println(cinput.readLine());//Digite o usuario
				String usuario = scan.nextLine();
				coutput.println(usuario);
				System.out.println(cinput.readLine());//Digite a senha
				String senha = scan.nextLine();
				coutput.println(senha);
				if(cinput.readLine().equals("foi")) {
					login_errado = false;
				} else {
					login_errado = true;
					System.out.println("Usuário ou senha incorretos!");
				}
			}
			//FIM LOGIN

                        System.out.println("Conectado ao servidor:"+ ip + ":" + porta);
			boolean jogo_rodando = true;
			while(jogo_rodando) {
				String digiInicio = cinput.readLine(); // Recebe dados do servidor
				System.out.println(digiInicio); // Digite iniciar para começar

				String inicio = scan.nextLine(); // leu iniciar lado cliente
				coutput.println(inicio); // enviou iniciar para servidor
				String sair = cinput.readLine();
				if(sair.equals("sairjogo"))
					break;

				int tentativa;
				String ganhou = "";
				while ((tentativa = Integer.parseInt(cinput.readLine())) > 0){ //tentativa
					System.out.print("Tentativa(s): ");
					System.out.println(tentativa);

					System.out.println(cinput.readLine()); //palavra com o #

					ganhou = cinput.readLine(); // bool ganhou

					if (ganhou.equals("ganhou")){
						System.out.println("Parabéns, você venceu!");
						break;
					}

					String digite = cinput.readLine(); // Texto digite letra
					System.out.println(digite); // Digite a letra
					String str = scan.nextLine();
					coutput.println(str);
					String perdeu = cinput.readLine();
					if(perdeu.equals("perdeu")) {
						System.out.println("Que pena, você perdeu!");
						break;
					}
				}
			}
			String str2 = cinput.readLine(); // Texto final do jogo
			System.out.println(str2);
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    public static void main(String args[]){
        new client();
    }
}
