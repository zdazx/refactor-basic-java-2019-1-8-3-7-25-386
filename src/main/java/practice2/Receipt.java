package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);

        subTotal = calculateOrderItemOfProductsTotal(products,items,subTotal);

        BigDecimal grandTotal = calculateTax(subTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public BigDecimal calculateOrderItemOfProductsTotal(List<Product> products, List<OrderItem> items, BigDecimal subTotal){
        for (Product product : products) {
            OrderItem curItem = findOrderItemByProduct(items, product);

            BigDecimal reducedPrice = product.getPrice()
                    .multiply(product.getDiscountRate())
                    .multiply(new BigDecimal(curItem.getCount()));

            subTotal = subTotal.subtract(reducedPrice);
        }
        return subTotal;
    }

    public BigDecimal calculateTax(BigDecimal subTotal){
        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);
        return grandTotal;
    }

    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getCount()));
            subTotal = subTotal.add(itemTotal);
        }
        return subTotal;
    }
}
