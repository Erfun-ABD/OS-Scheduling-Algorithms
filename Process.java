public class Process implements Comparable<Process> {
    final String name;
    final Integer arrivalTime;
    final Integer burstTime;
    Integer remainedTime;
    final Integer periodTime;
    Integer nextDeadline;

    public Process(String name, Integer arrivalTime, Integer burstTime, Integer periodTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainedTime = burstTime;
        this.nextDeadline = periodTime;
        this.periodTime = periodTime;
    }

    @Override
    public int compareTo(Process p) {
        if (this.arrivalTime != null) {

            int timeCompare = this.arrivalTime.compareTo(p.arrivalTime);
            return ((timeCompare == 0) ? 1 : timeCompare);
        } else {

            int timeCompare = this.nextDeadline.compareTo(p.nextDeadline);
            return ((timeCompare == 0) ? 1 : timeCompare);
        }
    }

    @Override
    public String toString() {
        return String.join(" ", name, String.valueOf(nextDeadline), String.valueOf(remainedTime));
    }
}