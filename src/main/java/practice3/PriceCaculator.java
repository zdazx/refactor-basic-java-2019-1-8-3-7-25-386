package practice3;

import java.math.BigDecimal;
import java.util.List;

public class PriceCaculator {
    private BigDecimal subTotal;
    private BigDecimal tempTax;
    private List<OrderLineItem> orderLineItemList;
    private List<BigDecimal> discounts;
    private BigDecimal tax;

    PriceCaculator(){}

    public PriceCaculator(Order order){
        this.orderLineItemList = order.orderLineItemList;
        this.discounts = order.discounts;
        this.tax = order.tax;
        this.subTotal = new BigDecimal(0);
        this.tempTax = new BigDecimal(0);
    }

    public BigDecimal calculate(){
        calculateSubTotal();
        calculateDiscounts();
        calculateTax();
        calculateGrandTotal();
        return subTotal;
    }

    private void calculateSubTotal(){
        for (OrderLineItem lineItem : orderLineItemList) {
            subTotal = subTotal.add(lineItem.getPrice());
        }
    }

    private void calculateDiscounts(){
        for (BigDecimal discount : discounts) {
            subTotal = subTotal.subtract(discount);
        }
    }

    private void calculateTax(){
        tempTax = subTotal.multiply(this.tax);
    }

    private void calculateGrandTotal(){
        subTotal = subTotal.add(tempTax);
    }
}
