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
    }

    static void inicializarTabuleiro(char[][] tab) {
        for (int i = 0; i < TAMANHO; i++) {
            Arrays.fill(tab[i], AGUA);
        }
    }
}