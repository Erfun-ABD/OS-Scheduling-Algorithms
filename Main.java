import java.util.Scanner;
import java.util.TreeSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Choose a schedule algorithm (FIFO, Round Robin, EDF): ");
        String schAlgo = sc.nextLine();
        schAlgo = schAlgo.strip();

        System.out.print("Enter Processes count: ");
        int processCount = sc.nextInt();
        sc.nextLine();

        int quantum = 0;
        if (schAlgo.equals("Round Robin")) {
            System.out.print("Enter quantum: ");
            quantum = sc.nextInt();
            sc.nextLine();
        }

        System.out.println("Enter each process info in a single line:");
        Queue<Process> processes = new PriorityQueue<Process>();
        for (int i = 0; i < processCount; i++) {
            String[] processInfo = sc.nextLine().split(" ");

            processes.add(switch (schAlgo) {
                    case "FIFO", "Round Robin" -> new Process(processInfo[0],
                                    Integer.parseInt(processInfo[1]),
                                    Integer.parseInt(processInfo[2]),
                                    null);
                    case "EDF" -> new Process(processInfo[0], 
                                    null,
                                    Integer.parseInt(processInfo[1]),
                                    Integer.parseInt(processInfo[2]));

                    default -> throw new IllegalArgumentException("Invalid scheduling algorithm");
            });
        }
        // System.err.println(processes);

        System.out.println();
        Queue<ExecutedProcess> epQueue = switch (schAlgo) {
            case "FIFO" ->
                SchedulingAlgorithms.fifo(new LinkedList<Process>(processes));
            case "Round Robin" ->
                SchedulingAlgorithms.roundrobin(new LinkedList<Process>(processes), quantum);
            case "EDF" ->
                SchedulingAlgorithms.edf(new TreeSet<Process>(processes), 120); 
            default -> {sc.close(); 
                throw new IllegalArgumentException("Invalid scheduling algorithm");}
        };

        while (!epQueue.isEmpty()) {
            System.out.println(epQueue.poll().toString());
        }

        sc.close();
    }
}
