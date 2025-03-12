package study.test;

public class GetDeliveryCostImpl implements GetDeliveryCost {

    private final int MIN_COST = 400;


    @Override
    public int invoke(int distance, PackageSize packageSize, boolean isFragile, Workload workload) throws Exception {
        checkParamsNotNull(new Object[]{packageSize, workload});
        if (distance < 0) throw new Exception(ErrorMessages.NEGATIVE_DISTANCE_ERROR);
        if (isFragile && distance > 30) throw new Exception(ErrorMessages.FRAGILE_PACKAGE_ERROR);
        int cost = calcByDistance(distance) + calcByPackageSize(packageSize) + calcByFragile(isFragile);
        return Math.max(calcByWorkload(cost, workload), MIN_COST);
    }

    private void checkParamsNotNull(Object[] arrayObj) {
        for (Object obj : arrayObj) {
            if (obj == null)
                throw new IllegalArgumentException(ErrorMessages.PARAMS_NULL_ERROR);
        }
    }

    private int calcByDistance(int distance) {
        if (distance > 30) {
            return 300;
        } else if (distance > 10) {
            return 200;
        } else if (distance > 2) {
            return 100;
        }
        return 50;
    }

    private int calcByPackageSize(PackageSize packageSize) {
        if (packageSize == PackageSize.BIG) {
            return 200;
        }
        return 100;
    }

    private int calcByFragile(boolean isFragile) {
        if (isFragile) {
            return 300;
        }
        return 0;
    }

    private int calcByWorkload(int cost, Workload workload) {
        return (int) (cost * workload.getMultiplier());
    }
}