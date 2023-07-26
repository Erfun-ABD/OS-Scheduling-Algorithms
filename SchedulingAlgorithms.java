import java.util.Queue;
import java.util.LinkedList;
import java.util.TreeSet;

public class SchedulingAlgorithms {
    public static Queue<ExecutedProcess> fifo(Queue<Process> processes) {
        Queue<ExecutedProcess> epQueue = new LinkedList<ExecutedProcess>();

        int time = 0;
        while (!processes.isEmpty()) {
            Process p = processes.poll();

            if (p.arrivalTime > time) {
                time = p.arrivalTime;
            }

            epQueue.add(new ExecutedProcess(p.name, time, p.burstTime));

            p.remainedTime -= p.burstTime;
            time += p.burstTime;
        }

        return epQueue;
    }

    public static Queue<ExecutedProcess> roundrobin(Queue<Process> processes, int quantum) {
        Queue<ExecutedProcess> epQueue = new LinkedList<ExecutedProcess>();

        Queue<Process> pQueue = new LinkedList<Process>();
        pQueue.add(processes.poll());

        int time = 0;
        while (!pQueue.isEmpty()) {
            
            Process p = pQueue.poll();

            if (p.arrivalTime > time) {
                time = p.arrivalTime;
            }


            int dedicatedTime = 0;
            if (p.remainedTime > quantum) {
                dedicatedTime = quantum;
            } else {
                dedicatedTime = p.remainedTime;
            }

            epQueue.add(new ExecutedProcess(p.name, time, dedicatedTime));

            p.remainedTime -= dedicatedTime;
            time += dedicatedTime;

            while (!processes.isEmpty()) {
                Process process = processes.peek();
                
                if (time >= process.arrivalTime) {
                    pQueue.add(processes.poll());
                } else {
                    break;
                }
            }

            if (p.remainedTime > 0) {
                pQueue.add(p);
            }
        }

        return epQueue;
    }

    public static Queue<ExecutedProcess> edf(TreeSet<Process> processes, int maxTime) {
        Queue<ExecutedProcess> epQueue = new LinkedList<ExecutedProcess>();
        ExecutedProcess ep = null;
        
        Process p = null;
        int time = 0;
        while (time < maxTime) {
            int earliestDeadline = processes.first().nextDeadline;

            for (Process process: processes) {
                if (process.remainedTime != 0) {
                    p = process;
                    break;
                }
            }
        
            if (p == null) {
                time = earliestDeadline;
                while (!processes.isEmpty()) {
                    if (processes.first().nextDeadline == earliestDeadline) {
                        Process firstP = processes.pollFirst();
                        firstP.remainedTime = firstP.burstTime;
                        firstP.nextDeadline += firstP.periodTime;
                        processes.add(firstP);
                    } else {
                        break;
                    }
                }
                continue;
            }

            int dedicatedTime = 0;
            if (time + p.remainedTime > maxTime) {
                dedicatedTime = maxTime - time;
            } else if (time + p.remainedTime > earliestDeadline) {
                dedicatedTime = earliestDeadline - time;
            } else {
                dedicatedTime = p.remainedTime;
            }
            
            if (ep == null || !ep.name.equals(p.name)) {
                ep = new ExecutedProcess(p.name, time);
                epQueue.add(ep);
            }
            ep.increaceDuration(dedicatedTime);
            
            p.remainedTime -= dedicatedTime;
            
            time += dedicatedTime;
            if (processes.last().remainedTime == 0) {
                time = earliestDeadline;
            }
            if (time == earliestDeadline) {
                while (!processes.isEmpty()) {
                    if (processes.first().nextDeadline <= earliestDeadline) {
                        Process firstP = processes.pollFirst();
                        firstP.remainedTime = firstP.burstTime;
                        firstP.nextDeadline += firstP.periodTime;
                        processes.add(firstP);
                    } else {
                        break;
                    }
                }
            
            }
        }

       return epQueue;
    }
}

class ExecutedProcess {
    final String name;
    final Integer startTime;
    Integer duration;

    public ExecutedProcess(String name, int startTime) {
        this.name = name;
        this.startTime = startTime;
        this.duration = 0;
    }

    public ExecutedProcess(String name, int startTime, int duration) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
    }    

    public void increaceDuration(int d) {
        duration += d;
    }

    @Override
    public String toString() {
        return (name + " (" + startTime + " - " + (startTime + duration) + ")");
    }
}