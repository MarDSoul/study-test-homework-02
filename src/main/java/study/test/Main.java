package study.test;

public class Main {
    public static void main(String[] args) throws Exception {
        GetDeliveryCost getDeliveryCost = new GetDeliveryCostImpl();
        int cost = getDeliveryCost.invoke(30, PackageSize.BIG, true, Workload.NORMAL);
        System.out.println("Delivery cost: \u20BD" + cost);
    }
}