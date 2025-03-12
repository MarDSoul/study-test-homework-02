package study.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetDeliveryCostTest {

    private GetDeliveryCost getDeliveryCost;

    @BeforeAll
    void setUpAll() {
        getDeliveryCost = new GetDeliveryCostImpl();
    }

        /*
        Pairwise positive  distance <2
        distance,   packageSize,    isFragile,  workload
        <=2,         big,            true,       normal
        <=2,         small,          false,      not very high
        <=2,         big,            true,       high
        <=2,         small,          false,      very high
    */

    static Stream<Pair<Integer, Object[]>> paramsDistanceBefore2() {
        Pair<Integer, Object[]> pair1 =
                new Pair<>(550, new Object[]{0, PackageSize.BIG, true, Workload.NORMAL});
        Pair<Integer, Object[]> pair2 =
                new Pair<>(400, new Object[]{0, PackageSize.SMALL, false, Workload.NOT_VERY_HIGH});
        Pair<Integer, Object[]> pair3 =
                new Pair<>(770, new Object[]{0, PackageSize.BIG, true, Workload.HIGH});
        Pair<Integer, Object[]> pair4 =
                new Pair<>(400, new Object[]{0, PackageSize.SMALL, false, Workload.VERY_HIGH});
        return Stream.of(pair1, pair2, pair3, pair4);
    }

    @ParameterizedTest
    @MethodSource("paramsDistanceBefore2")
    @Tag("Positive")
    @DisplayName("Tests with distance <=2")
    void getDeliveryCost_distanceBefore2_positive_test(Pair<Integer, Object[]> params) throws Exception {
        positiveAssert(params);
    }

    /*
        Pairwise positive  distance <10
        distance,   packageSize,    isFragile,  workload
        <=10,         small,         true,       very high
        <=10,         big,           false,      normal
        <=10,         small,         true,       not very high
        <=10,         big,           false,      high
    */

    static Stream<Pair<Integer, Object[]>> paramsDistanceBefore10() {
        Pair<Integer, Object[]> pair1 =
                new Pair<>(800, new Object[]{10, PackageSize.SMALL, true, Workload.VERY_HIGH});
        Pair<Integer, Object[]> pair2 =
                new Pair<>(400, new Object[]{10, PackageSize.BIG, false, Workload.NORMAL});
        Pair<Integer, Object[]> pair3 =
                new Pair<>(600, new Object[]{10, PackageSize.SMALL, true, Workload.NOT_VERY_HIGH});
        Pair<Integer, Object[]> pair4 =
                new Pair<>(420, new Object[]{10, PackageSize.BIG, false, Workload.HIGH});
        return Stream.of(pair1, pair2, pair3, pair4);
    }

    @ParameterizedTest
    @MethodSource("paramsDistanceBefore10")
    @Tag("Positive")
    @DisplayName("Tests with distance <=10")
    void getDeliveryCost_distanceBefore10_positive_test(Pair<Integer, Object[]> params) throws Exception {
        positiveAssert(params);
    }

    /*
        Pairwise positive  distance <30
        distance,   packageSize,    isFragile,  workload
        <=30,         big,           true,       high
        <=30,         small,         false,      very high
        <=30,         big,           true,       normal
        <=30,         small,         false,      not very high
    */

    static Stream<Pair<Integer, Object[]>> paramsDistanceBefore30() {
        Pair<Integer, Object[]> pair1 =
                new Pair<>(980, new Object[]{30, PackageSize.BIG, true, Workload.HIGH});
        Pair<Integer, Object[]> pair2 =
                new Pair<>(480, new Object[]{30, PackageSize.SMALL, false, Workload.VERY_HIGH});
        Pair<Integer, Object[]> pair3 =
                new Pair<>(700, new Object[]{30, PackageSize.BIG, true, Workload.NORMAL});
        Pair<Integer, Object[]> pair4 =
                new Pair<>(400, new Object[]{30, PackageSize.SMALL, false, Workload.NOT_VERY_HIGH});
        return Stream.of(pair1, pair2, pair3, pair4);
    }

    @ParameterizedTest
    @MethodSource("paramsDistanceBefore30")
    @Tag("Positive")
    @DisplayName("Tests with distance <=30")
    void getDeliveryCost_distanceBefore30_positive_test(Pair<Integer, Object[]> params) throws Exception {
        positiveAssert(params);
    }

    /*
        Pairwise positive  distance >30
        distance,   packageSize,    isFragile,  workload
        >30,         big,           false,      high
        >30,         big,           false,      normal
    */

    static Stream<Pair<Integer, Object[]>> paramsDistanceAbove30() {
        Pair<Integer, Object[]> pair1 =
                new Pair<>(700, new Object[]{31, PackageSize.BIG, false, Workload.HIGH});
        Pair<Integer, Object[]> pair2 =
                new Pair<>(500, new Object[]{31, PackageSize.BIG, false, Workload.NORMAL});
        return Stream.of(pair1, pair2);
    }

    @ParameterizedTest
    @MethodSource("paramsDistanceAbove30")
    @Tag("Positive")
    @DisplayName("Tests with distance >30")
    void getDeliveryCost_distanceAbove30_positive_test(Pair<Integer, Object[]> params) throws Exception {
        positiveAssert(params);
    }

    private void positiveAssert(Pair<Integer, Object[]> params) throws Exception {
        int expected = params.getFirst();
        int distance = (int) params.getSecond()[0];
        PackageSize packageSize = (PackageSize) params.getSecond()[1];
        boolean isFragile = (boolean) params.getSecond()[2];
        Workload workload = (Workload) params.getSecond()[3];
        assertEquals(
                expected,
                getDeliveryCost.invoke(distance, packageSize, isFragile, workload),
                String.format(
                        "Something wrong with params: distance = %s, packageSize = %s, isFragile = %s, workload = %s",
                        distance, packageSize.name(), isFragile, workload.name()
                )
        );
    }

    @Test
    @Tag("Negative")
    @DisplayName("Get exception with negative distance")
    void getDeliveryCost_negativeDistance_negative_test() throws Exception {
        Exception thrown =
                assertThrows(
                        Exception.class,
                        () -> getDeliveryCost.invoke(-1, PackageSize.SMALL, false, Workload.NORMAL)
                );
        assertEquals(ErrorMessages.NEGATIVE_DISTANCE_ERROR, thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Get exception with fragile package")
    void getDeliveryCost_fragilePackage_negative_test() throws Exception {
        Exception thrown =
                assertThrows(
                        Exception.class,
                        () -> getDeliveryCost.invoke(31, PackageSize.SMALL, true, Workload.NORMAL)
                );
        assertEquals(ErrorMessages.FRAGILE_PACKAGE_ERROR, thrown.getMessage());
    }

    static Stream<Object[]> paramsNullValues() {
        Object[] nullPackageSize = {31, null, true, Workload.NORMAL};
        Object[] nullWorkload = {31, PackageSize.SMALL, true, null};
        return Stream.of(nullPackageSize, nullWorkload);
    }

    @ParameterizedTest
    @MethodSource("paramsNullValues")
    @Tag("Negative")
    @DisplayName("Get exception with null params")
    void getDeliveryCost_nullParams_negative_test(int distance, PackageSize packageSize, boolean isFragile, Workload workload) throws Exception {
        Exception trowSizeNull =
                assertThrows(
                        Exception.class,
                        () -> getDeliveryCost.invoke(distance, packageSize, isFragile, workload)
                );
        assertEquals(ErrorMessages.PARAMS_NULL_ERROR, trowSizeNull.getMessage());

        Exception trowWorkloadNull =
                assertThrows(
                        Exception.class,
                        () -> getDeliveryCost.invoke(distance, packageSize, isFragile, workload)
                );
        assertEquals(ErrorMessages.PARAMS_NULL_ERROR, trowWorkloadNull.getMessage());
    }
}
