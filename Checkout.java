package comn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkout {

    //check out item list



    public String[] SKU = {"ipd", "mbp", "atv", "vga"};

    private PricingRules pricingRules;

    private static HashMap<String, Integer> itemList;
    private static HashMap<String, Double> item_costMap;
    private HashMap<String, List<Integer>> x_for_y_deal;
    private HashMap<String, List<String>> buy_x_get_y_free;
    private HashMap<String, List<Double>> bulkDiscount;

    public Checkout(PricingRules pricing_rules){
    	itemList = new HashMap<>();
        pricingRules = pricing_rules;
        item_costMap = pricingRules.get_Item_and_Price();
        x_for_y_deal = pricingRules.get_X_for_Y_Deal();
        bulkDiscount = pricingRules.get_bulkDiscount();
        buy_x_get_y_free = pricingRules.get_buy_x_get_y_free();
    }


    public void scan(String item){
    	
        if (itemList.get(item) == null){
            itemList.put(item, 1);
        }
        else{
            itemList.put(item, itemList.get(item) + 1);
        }

    }

    public void total(){
        double total_cost = 0;
        HashMap<String, Integer> itemList_checkout = deepCopy_int(itemList);
        HashMap<String, Double> item_costMap_checkout = deepCopy_double(item_costMap);
        //deal with x_for_y_deal first
        for (String item :x_for_y_deal.keySet()) {
            if (itemList.containsKey(item)) {
                int number_of_item = itemList.get(item);
                int count_after_discount = 0;
                count_after_discount = number_of_item % x_for_y_deal.get(item).get(0) +        // remaining one e.g. 5 % 3 = 2, 2 item need to pay normal price
                        number_of_item / x_for_y_deal.get(item).get(0) * x_for_y_deal.get(item).get(1);
                itemList_checkout.put(item, count_after_discount);
            }
        }

        for (String item :buy_x_get_y_free.keySet()){
            String free_item = buy_x_get_y_free.get(item).get(1);
            if (itemList.containsKey(item) && itemList.containsKey(free_item)) {
                //if mbp * 1 >= vga * 1 (note in buy_x_get_y_free principle), then all vga are free
                //if buy 2 get 1 free, then mbp * 1 >= vga *2, then all vga are free
                //if buy 1 get 2 free, then mbp * 2 >= vga *1, then all vga are free
                if (itemList.get(item) * Integer.parseInt(buy_x_get_y_free.get(item).get(2)) >=
                        itemList.get(free_item) * Integer.parseInt(buy_x_get_y_free.get(item).get(0))){

                    itemList_checkout.put(free_item, 0);

                }
                else {
                    int count = itemList.get(free_item) -
                            itemList.get(item)/Integer.parseInt(buy_x_get_y_free.get(item).get(0)) * Integer.parseInt(buy_x_get_y_free.get(item).get(2));

                    itemList_checkout.put(free_item, count);
                }
            }
        }
        
        //find the bulk discount
        for (String item : bulkDiscount.keySet()){
            if (itemList.containsKey(item)) {
                double item_needed = bulkDiscount.get(item).get(0);
                double new_price = bulkDiscount.get(item).get(1);
                double number_of_item = (double) itemList.get(item);
                if (number_of_item >= item_needed){
                    item_costMap_checkout.put(item, new_price);
                }
                
            }
        }
        
        //calculate the total
        for (String item : itemList_checkout.keySet()){
            int number_of_item = itemList_checkout.get(item);
            double price_of_item = item_costMap_checkout.get(item);
            total_cost += number_of_item * price_of_item;
        }
        
        System.out.println("Total expected: $"+ total_cost);
    }

    private static HashMap<String, Double> deepCopy_double(HashMap<String, Double> original) {
        HashMap<String, Double> copy = new HashMap<>();
        for (Map.Entry<String, Double> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    private static HashMap<String, Integer> deepCopy_int(HashMap<String, Integer> original) {
        HashMap<String, Integer> copy = new HashMap<>();
        for (Map.Entry<String, Integer> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }
}
