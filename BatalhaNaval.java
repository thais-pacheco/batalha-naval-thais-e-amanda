import java.util.*;

public class BatalhaNaval {
    static final int TAMANHO = 10;
    static final char AGUA = '~';
    static final char NAVIO = 'N';
    static final char TIRO = 'X';
    static final char ERRO = 'O';


    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();

    static String nome1, nome2;
    static boolean contraComputador = false;

    public static void main(String[] args) {
        char[][] tab1 = new char[TAMANHO][TAMANHO];
        char[][] tab2 = new char[TAMANHO][TAMANHO];
        char[][] visivel1 = new char[TAMANHO][TAMANHO];
        char[][] visivel2 = new char[TAMANHO][TAMANHO];

        inicializarTabuleiro(tab1);
        inicializarTabuleiro(tab2);
        inicializarTabuleiro(visivel1);
        inicializarTabuleiro(visivel2);

        System.out.println("=== BATALHA NAVAL ===");
        System.out.print("Digite o nome do Jogador 1: ");
        nome1 = scanner.nextLine();

        System.out.print("Deseja jogar contra a máquina? (s/n): ");
        String escolha = scanner.nextLine().trim().toLowerCase();
        contraComputador = escolha.equals("s");

        if (contraComputador) {
            nome2 = "Computador";
        } else {
            System.out.print("Digite o nome do Jogador 2: ");
            nome2 = scanner.nextLine();
        }

        System.out.println("\nPosicionando navios para " + nome1 + "...");
        posicionarNavios(tab1);
        System.out.println("Navios posicionados!\n");

        System.out.println("Posicionando navios para " + nome2 + "...");
        posicionarNavios(tab2);
        System.out.println("Navios posicionados!\n");

        boolean jogoAtivo = true;

        while (jogoAtivo) {
            System.out.println("\n===== Vez de " + nome1 + " =====");
            mostrarTabuleiro(visivel1, nome2);
            turno(tab2, visivel1, nome1);

            if (verificarFim(tab2)) {
                System.out.println("🎉 " + nome1 + " venceu!");
                jogoAtivo = false;
                continue;
            }

            System.out.println("\n===== Vez de " + nome2 + " =====");
            if (contraComputador) {
                turnoComputador(tab1, visivel2);
            } else {
                mostrarTabuleiro(visivel2, nome1);
                turno(tab1, visivel2, nome2);
            }

            if (verificarFim(tab1)) {
                System.out.println("🎉 " + nome2 + " venceu!");
                jogoAtivo = false;
            }
        }

        System.out.println("\n=== Fim de jogo ===");
    }

    static void inicializarTabuleiro(char[][] tab) {
        for (int i = 0; i < TAMANHO; i++) {
            Arrays.fill(tab[i], AGUA);
        }
    }

    static void mostrarTabuleiro(char[][] tab, String alvo) {
        System.out.println("Tabuleiro de ataque a " + alvo + ":");
        System.out.print("   ");
        for (char letra = 'A'; letra < 'A' + TAMANHO; letra++) {
            System.out.print(letra + " ");
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void posicionarNavios(char[][] tab) {
        int[] tamanhos = {5, 4, 3, 3, 2};
        for (int tamanho : tamanhos) {
            boolean colocado = false;
            while (!colocado) {
                int linha = rand.nextInt(TAMANHO);
                int coluna = rand.nextInt(TAMANHO);
                boolean horizontal = rand.nextBoolean();

                if (podeColocar(tab, linha, coluna, tamanho, horizontal)) {
                    for (int i = 0; i < tamanho; i++) {
                        int l = linha + (horizontal ? 0 : i);
                        int c = coluna + (horizontal ? i : 0);
                        tab[l][c] = NAVIO;
                    }
                    colocado = true;
                }
            }
        }
    }

    static boolean podeColocar(char[][] tab, int l, int c, int tamanho, boolean h) {
        if ((h && c + tamanho > TAMANHO) || (!h && l + tamanho > TAMANHO)) return false;

        for (int i = 0; i < tamanho; i++) {
            int linha = l + (h ? 0 : i);
            int coluna = c + (h ? i : 0);
            if (tab[linha][coluna] != AGUA) return false;
        }
        return true;
    }

    static void turno(char[][] inimigo, char[][] visivel, String jogador) {
        int linha = -1, coluna = -1;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print("Informe a posição (ex: A5): ");
                String entrada = scanner.nextLine().trim().toUpperCase();

                if (entrada.length() < 2 || entrada.length() > 3) {
                    System.out.println(" Entrada inválida. Use formato como B7.");
                    continue;
                }

                char letra = entrada.charAt(0);
                if (letra < 'A' || letra > 'J') {
                    System.out.println("Coluna inválida. Use letras de A a J.");
                    continue;
                }
                coluna = letra - 'A';

                String numStr = entrada.substring(1);
                linha = Integer.parseInt(numStr) - 1;
                if (linha < 0 || linha >= TAMANHO) {
                    System.out.println("⚠ Linha inválida. Use números de 1 a 10.");
                    continue;
                }

                if (visivel[linha][coluna] == TIRO || visivel[linha][coluna] == ERRO) {
                    System.out.println(" Posição já atingida!");
                } else {
                    valido = true;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }

        if (inimigo[linha][coluna] == NAVIO) {
            System.out.println(" " + jogador + " acertou um navio!");
            inimigo[linha][coluna] = TIRO;
            visivel[linha][coluna] = TIRO;
        } else {
            System.out.println(" " + jogador + " errou.");
            inimigo[linha][coluna] = ERRO;
            visivel[linha][coluna] = ERRO;
        }
    }

    static void turnoComputador(char[][] jogador, char[][] visivel) {
        int linha, coluna;
        boolean valido = false;

        while (!valido) {
            linha = rand.nextInt(TAMANHO);
            coluna = rand.nextInt(TAMANHO);

            if (visivel[linha][coluna] == TIRO || visivel[linha][coluna] == ERRO) {
                continue;
            }

            char letraColuna = (char) ('A' + coluna);
            int linhaHumana = linha + 1;

            if (jogador[linha][coluna] == NAVIO) {
                System.out.println(" O computador acertou em " + letraColuna + linhaHumana);
                jogador[linha][coluna] = TIRO;
                visivel[linha][coluna] = TIRO;
            } else {
                System.out.println(" O computador errou em " + letraColuna + linhaHumana);
                jogador[linha][coluna] = ERRO;
                visivel[linha][coluna] = ERRO;
            }

            valido = true;
        }
    }

    static boolean verificarFim(char[][] tab) {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tab[i][j] == NAVIO) return false;
            }
        }
        return true;
    }
}
