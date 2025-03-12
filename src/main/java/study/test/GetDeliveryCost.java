package study.test;

public interface GetDeliveryCost {
    int invoke(int distance, PackageSize packageSize, boolean isFragile, Workload workload) throws Exception;
}