<!-- About the Project -->
## Scheduling Algorithms

### Algorithms

| Functions  | Detail                                                                |
| ---------- | --------------------------------------------------------------------- |
| fifo       | first come first serve                                                |
| roundrobin | execute a quantum time of a process and if it remained enqueue again. |
| edf        | priority is given to the earliest deadline.                           |

```java
public static Queue<ExecutedProcess> fifo(Queue<Process> processes)
```
- processes are sorted by arrival time.

```java
public static Queue<ExecutedProcess> roundrobin(Queue<Process> processes, int quantum)
```
- processes are sorted by arrival time.

```java
public static Queue<ExecutedProcess> edf(TreeSet<Process> processes, int maxTime)
```
- processes are sorted by deadline.

### Auxilary Classes

```java
public class Process implements Comparable<Process> {
    final String name;
    final Integer arrivalTime;
    final Integer burstTime;
    Integer remainedTime;
    final Integer periodTime;
    Integer nextDeadline;
}
```

```java
class ExecutedProcess {
    final String name;
    final Integer startTime;
    Integer duration;
}
```
