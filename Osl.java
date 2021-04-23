package comn;

public class Osl {
	public static void main(String[] args) {
		PricingRules pricingRules = new PricingRules();
		Checkout co = new Checkout(pricingRules);
		co.scan("mbp");
		co.scan("vga");
		co.scan("ipd");
		co.total();
	}
}
