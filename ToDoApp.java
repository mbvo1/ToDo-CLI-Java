import java.io.*;
import java.util.*;

public class ToDoApp {
    static final String ARQUIVO = "tarefas.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;










        





        do {
            System.out.println("\n--- GERENCIADOR DE TAREFAS ---");
            System.out.println("1. Adicionar tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Marcar tarefa como concluída");
            System.out.println("4. Remover tarefa");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar o buffer

            switch (opcao) {
                case 1:
                    adicionarTarefa(sc);
                    break;
                case 2:
                    listarTarefas();
                    break;
                case 3:
                    marcarComoConcluida(sc);
                    break;
                case 4:
                    removerTarefa(sc);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }

    static void adicionarTarefa(Scanner sc) {
        System.out.print("Digite a nova tarefa: ");
        String tarefa = sc.nextLine();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write("[ ] " + tarefa);
            bw.newLine();
            System.out.println("Tarefa adicionada!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo.");
        }
    }

    static void listarTarefas() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            int i = 1;
            System.out.println("\n--- LISTA DE TAREFAS ---");
            while ((linha = br.readLine()) != null) {
                System.out.println(i + ". " + linha);
                i++;
            }
            if (i == 1) {
                System.out.println("Nenhuma tarefa encontrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    static void marcarComoConcluida(Scanner sc) {
        List<String> tarefas = lerTarefas();
        if (tarefas.isEmpty()) return;

        listarTarefas();
        System.out.print("Número da tarefa para marcar como concluída: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index >= 1 && index <= tarefas.size()) {
            String tarefa = tarefas.get(index - 1);
            if (tarefa.startsWith("[ ]")) {
                tarefas.set(index - 1, tarefa.replace("[ ]", "[X]"));
                salvarTarefas(tarefas);
                System.out.println("Tarefa marcada como concluída!");
            } else {
                System.out.println("Essa tarefa já está concluída.");
            }
        } else {
            System.out.println("Número inválido.");
        }
    }

    static void removerTarefa(Scanner sc) {
        List<String> tarefas = lerTarefas();
        if (tarefas.isEmpty()) return;

        listarTarefas();
        System.out.print("Número da tarefa para remover: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index >= 1 && index <= tarefas.size()) {
            tarefas.remove(index - 1);
            salvarTarefas(tarefas);
            System.out.println("Tarefa removida!");
        } else {
            System.out.println("Número inválido.");
        }
    }

    static List<String> lerTarefas() {
        List<String> tarefas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                tarefas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
        return tarefas;
    }

    static void salvarTarefas(List<String> tarefas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (String tarefa : tarefas) {
                bw.write(tarefa);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo.");
        }
    }
}
