package comn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingRules {

    //check out item list
    private static Map<String, Integer> itemList;

    public String[] SKU = {"ipd", "mbp", "atv", "vga"};

    private static HashMap<String, Double> item_costMap;
    private static HashMap<String, List<Integer>> x_for_y_deal;
    private static HashMap<String, List<Double>> bulkDiscount;
    private static HashMap<String, List<String>> buy_x_get_y_free;

    static {
        item_costMap = new HashMap<>();
        item_costMap.put("ipd", (double) 549.99);
        item_costMap.put("mbp", (double) 1399.99);
        item_costMap.put("atv", (double) 109.50);
        item_costMap.put("vga", (double) 30.00);

        //apple tv 3 for 2
        x_for_y_deal = new HashMap<>();
        List<Integer> atv_deal = Arrays.asList(3 , 2);
        x_for_y_deal.put("atv", atv_deal);

        //Please remind that this is a single direction logic
        //That is, buy mbp get vga free, but buy vga won't get mbp for free
        buy_x_get_y_free = new HashMap<>();
        List<String> mbp_deal = Arrays.asList("1", "vga" , "1");
        buy_x_get_y_free.put("mbp", mbp_deal);

        //bundle
        bulkDiscount = new HashMap<>();
        List<Double> ipd_deal =  Arrays.asList(4.0 , 499.99);
        bulkDiscount.put("ipd", ipd_deal);
    }


    public void set_bulkDiscount(String item, int x, double y){
        bulkDiscount.put(item, (List<Double>) Arrays.asList( Double.parseDouble(String.valueOf(x)) , y));
    }

    public HashMap<String, List<Double>> get_bulkDiscount(){
        return bulkDiscount;
    }


    public void set_X_for_Y_Deal(String item, int x, String gift, int y){
        buy_x_get_y_free.put(item, (List<String>) Arrays.asList(String.valueOf(x) , gift, String.valueOf(y)));
    }

    public HashMap<String, List<String>> get_buy_x_get_y_free(){
        return buy_x_get_y_free;
    }


    public void set_X_for_Y_Deal(String item, int x, int y){
        x_for_y_deal.put(item, (List<Integer>) Arrays.asList(x , y));
    }

    public HashMap<String, List<Integer>> get_X_for_Y_Deal(){
        return x_for_y_deal;
    }

    public void set_Item_and_Price(String item, double price){
        item_costMap.put(item, price);
    }

    public HashMap<String, Double> get_Item_and_Price(){
        return item_costMap;
    }
}
