package br.com.faesa.monitorvacina.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import br.com.faesa.monitorvacina.controller.IController;
import br.com.faesa.monitorvacina.controller.UsuarioController;
import br.com.faesa.monitorvacina.model.Usuario;
import br.com.faesa.monitorvacina.controller.RelatorioController;

import br.com.faesa.monitorvacina.utils.SplashScreen;

public class Principal {

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        IController usuarioController = new UsuarioController();
        SplashScreen splash = new SplashScreen();
        splash.exibir();


        while (true) {
            
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1 - Inserir Usuário");
            System.out.println("2 - Atualizar Usuário");
            System.out.println("3 - Remover Usuário");
            System.out.println("4 - Listar todos os Usuários");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = teclado.nextInt();
            teclado.nextLine(); // Limpa o "enter" que ficou no buffer do teclado

            switch (opcao) {
                case 1: // INSERIR
                    Usuario novoUsuario = new Usuario();
                    
                    System.out.print("Digite o nome: ");
                    novoUsuario.setNome(teclado.nextLine());
                    
                    System.out.print("Digite o CPF: ");
                    novoUsuario.setCpf(teclado.nextLine());

                    System.out.print("Digite a data de nascimento (dd/mm/aaaa): ");
                    novoUsuario.setDataNascimento(LocalDate.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    
                    System.out.print("Digite o email: ");
                    novoUsuario.setEmail(teclado.nextLine());

                    System.out.print("Digite a senha: ");
                    novoUsuario.setSenha(teclado.nextLine());
                    
                    usuarioController.inserir(novoUsuario);
                    break;

                case 2: // ATUALIZAR
                    System.out.println("Listando usuários para atualização...");
                    List<Object> usuariosParaAtualizar = usuarioController.listarTodos();
                    for(Object obj : usuariosParaAtualizar) {
                        System.out.println(obj.toString());
                    }
                    
                    System.out.print("\nDigite o ID do usuário que deseja atualizar: ");
                    int idParaAtualizar = teclado.nextInt();
                    teclado.nextLine(); // Limpa o buffer
                    
                    Usuario usuarioAtualizado = new Usuario();
                    usuarioAtualizado.setIdUsuario(idParaAtualizar); // Coloca o ID no objeto
                    
                    System.out.print("Digite o NOVO nome: ");
                    usuarioAtualizado.setNome(teclado.nextLine());
                    
                    System.out.print("Digite o NOVO CPF: ");
                    usuarioAtualizado.setCpf(teclado.nextLine());

                    System.out.print("Digite a NOVA data de nascimento (dd/mm/aaaa): ");
                    usuarioAtualizado.setDataNascimento(LocalDate.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    
                    System.out.print("Digite o NOVO email: ");
                    usuarioAtualizado.setEmail(teclado.nextLine());

                    System.out.print("Digite a NOVA senha: ");
                    usuarioAtualizado.setSenha(teclado.nextLine());
                    
                    usuarioController.atualizar(usuarioAtualizado);
                    break;

                case 3: // REMOVER
                    System.out.println("Listando usuários para remoção...");
                    List<Object> usuariosParaRemover = usuarioController.listarTodos();
                    for(Object obj : usuariosParaRemover) {
                        System.out.println(obj.toString());
                    }

                    System.out.print("\nDigite o ID do usuário que deseja remover: ");
                    int idParaRemover = teclado.nextInt();
                    teclado.nextLine(); // Limpa o buffer
                    
                    Usuario usuarioParaRemover = new Usuario();
                    usuarioParaRemover.setIdUsuario(idParaRemover);
                    
                    usuarioController.remover(usuarioParaRemover);
                    break;

                case 4: // RELATÓRIOS
                    // cria o controller dos relatórios na hora que a gente precisa dele
                    RelatorioController relatorioController = new RelatorioController();
                    
                    // mostra um menu só para os relatórios
                    System.out.println("\nSUBMENU DE RELATÓRIOS");
                    System.out.println("1 - Listar Efeitos Adversos por Usuário (JOIN)");
                    System.out.println("2 - Contagem de Efeitos por Vacina (GROUP BY)");
                    System.out.print("Escolha um relatório: ");
                    
                    // lê a opção do relatório que o usuário digitou
                    int opcaoRelatorio = teclado.nextInt();
                    teclado.nextLine(); // Limpa o buffer
                    
                    // chama o método certo dependendo da escolha
                    if (opcaoRelatorio == 1) {
                        relatorioController.gerarRelatorioRegistrosPorUsuario();
                    } else if (opcaoRelatorio == 2) {
                        relatorioController.gerarRelatorioContagemPorVacina();
                    } else {
                        System.out.println("!! Opção de relatório inválida !!");
                    }
                    break;
                    
                case 5: // SAIR
                    System.out.println("Tchau! Saindo do sistema...");
                    teclado.close();
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("!! Opção inválida, digita um número do menu !!");
                    break;
            }
        }
    }
}