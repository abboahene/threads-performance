import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        // To create a list of 'n' integers each with a value of 1
        Function<Integer, List<Integer>> generateIntegers = (n) -> {
            List<Integer> integers = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                integers.add(1);
            }
            return integers;
        };

        // To calculate sum of all integers in list without concurrency
        Consumer<List<Integer>> sumOfIntegersSequentially = (l) ->{
            double total = l.stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            System.out.printf("Total: %,.2f%n", total);
        };

        // To calculate sum of all integers in list making use of concurrency
        Consumer<List<Integer>> sumOfIntegersConcurrently = (l) ->{
            double total = l.parallelStream()
                    .mapToInt(Integer::intValue)
                    .sum();
            System.out.printf("Total: %,.2f%n", total);
        };

        // To invoke and output the execution time(ms) of the sum operations
        BiConsumer<Consumer<List<Integer>>, List<Integer>> getTimePerformance = (c, list) -> {
            long startTime = System.currentTimeMillis();

            c.accept(list);

            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;

            System.out.printf("Took %dms to complete%n", timeTaken);
        };

        // Set up varying input data
        List<Integer> integers40k = generateIntegers.apply(40_000);
        List<Integer> integers800k = generateIntegers.apply(800_000);
        List<Integer> integers10M = generateIntegers.apply(10_000_000);
        List<Integer> integers100M = generateIntegers.apply(100_000_000);

        // Output
        System.out.println();
        System.out.println("40k Integers (Sequential)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersSequentially, integers40k);
        System.out.println();
        System.out.println("40k Integers (Concurrent)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersConcurrently, integers40k);

        System.out.println();
        System.out.println();

        System.out.println("800k Integers (Sequential)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersSequentially, integers800k);
        System.out.println();
        System.out.println("800k Integers (Concurrent)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersConcurrently, integers800k);

        System.out.println();
        System.out.println();

        System.out.println("10M Integers (Sequential)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersSequentially, integers10M);
        System.out.println();
        System.out.println("10M Integers (Concurrent)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersConcurrently, integers10M);

        System.out.println();
        System.out.println();

        System.out.println("100M Integers (Sequential)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersSequentially, integers100M);
        System.out.println();
        System.out.println("100M Integers (Concurrent)");
        System.out.println("------------------------------");
        getTimePerformance.accept(sumOfIntegersConcurrently, integers100M);

    }
}
