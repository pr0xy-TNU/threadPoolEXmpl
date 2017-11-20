package multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


class SumOperation implements Callable<Long> {

    private long from, to, localResult = 0;
    public SumOperation(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Long call() throws Exception {
        for (long i = from; i <= to; i++) {
            localResult += i;
        }
        return localResult;
    }
}

public class Main {

    private static final int NUMBER_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static long userNumber = 400_000_000;
    private static long totalSum = 0;

    public static void main(String[] args) {

        ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_PROCESSORS);
        long division = userNumber / NUMBER_OF_PROCESSORS;
        List<Future<Long>> total = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_PROCESSORS; i++) {
            long from = division * i + 1;
            long to = division * (i + 1);
            Future<Long> temp = executors.submit(new SumOperation(from, to));
            total.add(temp);
        }

        for (Future<Long> item : total
                ) {
            try {
                totalSum += item.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(totalSum);
    }


}
