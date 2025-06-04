package SimuladorSistemaArquivos;

import java.util.Scanner;

public class Shell {
    private final FileSystemTree fs;
    private final Scanner scanner;

    public Shell() {
        this.fs = new FileSystemTree();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("📂 Simulador de Sistema de Arquivos - Modo Shell");
        System.out.println("Digite 'exit' para sair.\n");

        System.out.println("Comandos disponíveis:");
        System.out.println("  mkdir <caminho>         → Cria um diretório");
        System.out.println("  touch <caminho>         → Cria um arquivo");
        System.out.println("  ls <caminho>            → Lista conteúdo do diretório");
        System.out.println("  rm <caminho>            → Remove arquivo ou diretório (use / no fim p/ pastas)");
        System.out.println("  mv <caminhoAntigo> <novoNome> → Renomeia arquivo ou diretório");
        System.out.println("  exit                    → Encerra o simulador\n");

        while (true) {
            System.out.print("> ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("exit")) {
                System.out.println("Encerrando o shell...");
                break;
            }

            interpretarComando(entrada);
        }
    }

    private void interpretarComando(String entrada) {
        String[] partes = entrada.split(" ");
        if (partes.length < 2) {
            System.out.println("❌ Comando incompleto.");
            return;
        }

        String comando = partes[0];

        switch (comando) {
            case "cp" -> {
                if (partes.length < 3) {
                    System.out.println("❌ Comando cp requer dois argumentos.");
                    return;
                }
                String parent = obterDiretorioPai(partes[1]);
                String nomeOrigem = obterNomeFinal(partes[1]);
                String nomeDestino = partes[2];
                fs.copiarArquivo(parent, nomeOrigem, nomeDestino);
            }

            case "mkdir" -> {
                String parent = obterDiretorioPai(partes[1]);
                String nome = obterNomeFinal(partes[1]);
                fs.criarDiretorio(parent, nome);
            }
            case "touch" -> {
                String parent = obterDiretorioPai(partes[1]);
                String nome = obterNomeFinal(partes[1]);
                fs.criarArquivo(parent, nome);
            }
            case "ls" -> fs.listar(partes[1]);
            case "rm" -> {
                String parent = obterDiretorioPai(partes[1]);
                String nome = obterNomeFinal(partes[1]);
                if (partes[1].endsWith("/")) {
                    fs.removerDiretorio(parent, nome);
                } else {
                    fs.removerArquivo(parent, nome);
                }
            }
            case "mv" -> {
                if (partes.length < 3) {
                    System.out.println("❌ Comando mv requer dois argumentos.");
                    return;
                }
                String parent = obterDiretorioPai(partes[1]);
                String antigo = obterNomeFinal(partes[1]);
                String novo = partes[2];

                if (partes[1].endsWith("/")) {
                    fs.renomearDiretorio(parent, antigo, novo);
                } else {
                    fs.renomearArquivo(parent, antigo, novo);
                }
            }
            default -> System.out.println("❌ Comando não reconhecido: " + comando);
        }
    }

    private String obterDiretorioPai(String caminho) {
        int ultimaBarra = caminho.lastIndexOf('/');
        if (ultimaBarra == -1) return "root";
        return "root/" + caminho.substring(0, ultimaBarra);
    }

    private String obterNomeFinal(String caminho) {
        int ultimaBarra = caminho.lastIndexOf('/');
        if (ultimaBarra == -1) return caminho;
        return caminho.substring(ultimaBarra + 1).replace("/", "");
    }
}