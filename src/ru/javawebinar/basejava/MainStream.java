package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainStream {

    public static void main(String[] args) {
        Random random = new Random();
        int[] values = Stream.generate(() -> random.nextInt(8) + 1).limit(5).mapToInt(value -> value).toArray();
        System.out.println(minValue(values));
        System.out.println(oddOrEven(Arrays.stream(values).
                mapToObj(value -> Integer.valueOf(value)).
                collect(Collectors.toList())));
    }

    private static int minValue(int[] values) {
        values = Arrays.stream(values).distinct().sorted().toArray();
        int[] i = new int[] {values.length - 1};
        return Arrays.stream(values).map(value -> (int) (value * Math.pow(10, i[0]--))).sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum =  integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().
                map(integer -> new int[] {integer}).
                flatMap(ints -> (sum % 2 == 0 && ints[0] % 2 == 0) || (sum % 2 != 0 && ints[0] % 2 != 0) ?
                        Stream.of() : Stream.of(ints[0])).
                collect(Collectors.toList());
    }
}
