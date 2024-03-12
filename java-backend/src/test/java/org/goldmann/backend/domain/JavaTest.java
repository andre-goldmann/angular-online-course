package org.goldmann.backend.domain;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

public class JavaTest {

    protected static class MyMath{
        public static Integer triple(Integer x){
            return x* 3;
        }

        public static Integer timesTwo(Integer x){
            return x*2;
        }

        public static Integer timesFour(Integer x){
            return x*4;
        }

        public static Function<Integer,Integer> multiplier(Integer y){
            return (Integer x) -> x * y;
        }

    }
    @Test
    public void test(){
        Set<String> lset = new LinkedHashSet<>();
        lset.add("c");lset.add("b");lset.add("a");
        System.out.println(lset);

        Set<String> hset = new HashSet<>();
        hset.add("c");hset.add("b");hset.add("a");
        System.out.println(hset);

        Map<String, Integer> lmap = new LinkedHashMap<>();
        lmap.put("c", 3);lmap.put("b", 3);lmap.put("a", 3);
        System.out.println(lmap);
        Map<String, Integer> hmap = new HashMap<>();
        hmap.put("c", 3);hmap.put("b", 3);hmap.put("a", 3);
        System.out.println(hmap);



        /*final Function<Integer, Integer> myTriple = MyMath::triple;
        final Integer result = myTriple.apply(5);
        System.out.println(result);
        final BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        System.out.println(add.apply(32,32));

        final TriFunction<Integer, Integer, Integer, Integer> addThree = (x, y, z) -> x + y + z;
        System.out.println(addThree.apply(1,3, 6));

        final NoArgFunction<String> hello = ()-> "Hello";
        System.out.println(hello.apply());

        NoArgFunction<NoArgFunction<String>> createPrinter = () -> () -> "Hello World";
        System.out.println(createPrinter.apply().apply());
        Function<Integer, Integer> timesTwo = MyMath.multiplier(2);
        Function<Integer, Integer> timesThree = MyMath.multiplier(3);

        System.out.println(timesTwo.apply(6));
        System.out.println(timesThree.apply(6));

        NoArgFunction<NoArgFunction<String>> createGreeter = () -> {
            String name = "Shaun";
            return () -> "Hello, " + name;
        };
        final NoArgFunction<String> greeter = createGreeter.apply();
        System.out.println(greeter.apply());

        final BiFunction<Float, Float, Float> divide = (x, y) -> x/y;
        // With zero Check
        final BiFunction<Float, Float, Float> divideWithCheck = (x, y) -> {
          if (y == 0f){
              return 0f;
          }
          return x/y;
        };
        System.out.println(divideWithCheck.apply(10f, 0f));

        // With zero Check
        final Function<BiFunction<Float, Float,Float>, BiFunction<Float, Float, Float>> secondArgIsntZeroCheck =
                (func) -> (x, y) -> {
                    if (y == 0f){
                        return 0f;
                    }
                    return func.apply(x, y);
                };
        final BiFunction<Float, Float, Float> divideSafe = secondArgIsntZeroCheck.apply(divide);
        System.out.println(divideSafe.apply(10f, 0f));
        /////////////// STREAMS ///////////////
        final String[] wordsArr = {"Hello" ,"functional" ,"programming" ,"is" ,"cool"};
        final List<String> words = new ArrayList<>(Arrays.asList(wordsArr));

        final Function<Integer, Predicate<String>> createrLengthTest = (minLength) -> {
          return (str) -> str.length() > minLength;
        };

        final Predicate<String> isLongerThan3 = createrLengthTest.apply(3);
        final List<String> longWords = words.stream().filter(isLongerThan3).toList();
        System.out.println(longWords);

        Map<Integer, List<String>> wordsMap = words
                .stream()
                .collect(Collectors.groupingBy(
                        (word) -> word.length()
                ));
        System.out.println(wordsMap);

        Map<Boolean, List<String>> wordsMapPartioningy = words.stream()
                .collect(Collectors.partitioningBy(
                        (word) -> word.length() > 5
                ));
        System.out.println(wordsMapPartioningy);

        final Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final List<Integer> listOfIntegers = new ArrayList<>(Arrays.asList(intArray));

        BinaryOperator<Integer> getSum = (acc, x) -> {
            Integer r1 = acc + x;
            return r1;
        };
        final Integer sum = listOfIntegers.stream().reduce(0, getSum);
        System.out.println(sum);
        TrackerConfiguration configuration = TrackerConfiguration.builder()
                //.apiEndpoint(URI.create("https://172.17.134.42:9000/matomo/matomo.php"))
                //.apiEndpoint(URI.create("http://172.17.134.42:8080/matomo.php?idsite=2&amp;rec=1"))
                .apiEndpoint(URI.create("http://172.17.134.42:8080/matomo.php"))
                //
                .logFailedTracking(true)
                //Plfichtfeld
                .defaultSiteId(2) // if not explicitly specified by the request
                .build();

        MatomoTracker tracker = new MatomoTracker(configuration);



                //.ecommerceCartUpdate(50.0)
        /*MatomoRequest.request()
                //.ecommerceShippingCost(1.0)
                .ecommerceItems(EcommerceItems
                        .builder()
                        .item(EcommerceItem
                                .builder()
                                .sku("XYZ12345")
                                .name("Matomo - The big book about web analytics")
                                .category("Education & Teaching")
                                .price(23.1)
                                .quantity(2)
                                .build())
                        .item(EcommerceItem
                                .builder()
                                .sku("B0C2WV3MRJ")
                                .name("Matomo for data visualization")
                                .category("Education & Teaching")
                                .price(15.0)
                                .quantity(1).build())).build();

        MatomoRequest request = MatomoRequest.request()
                .actionUrl("http://172.17.134.42/")
                //.actionName("My Action")
                // Plfichtfeld
                .goalId(2)
                .ecommerceItems(
                        EcommerceItems
                                .builder()
                                .item(EcommerceItem
                                        .builder()
                                        .sku("B0C2WV3MRJ")
                                        .name("Matomo for data visualization")
                                        .category("Education & Teaching")
                                        .price(15.0)
                                        .build())
                                .build()
                )
            .build();

        MatomoRequest request = MatomoRequest.request()
                .siteId(2)
                //.actionName("My Action")
                //.event("Training","Workout completed","Bench press",60.0)
                .eventAction("login")
                .visitorId(VisitorId.fromString("some@email-adress.org"))
                .build();
        //tracker.sendBulkRequestAsync(request);
        tracker.sendRequest(request);*/
    }
}
