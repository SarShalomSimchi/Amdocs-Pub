package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amdocs.task.Pub;
import com.amdocs.task.beverages.Beer;
import com.amdocs.task.beverages.Beverage;
import com.amdocs.task.beverages.Cosmopolitan;
import com.amdocs.task.beverages.KopanganBucket;
import com.amdocs.task.beverages.Mochito;
import com.amdocs.task.machine.OrderMachine;

public class PubTests {
    
	private static final Map<Integer, Beverage> menu = Map.of(
			Mochito.ID, new Mochito(),
			Beer.ID, new Beer(),
			Cosmopolitan.ID, new Cosmopolitan()
	);
	
	
	private Pub pub;
	 
	@BeforeEach
	public void setUp() {
		OrderMachine orderMachine = new OrderMachine();
		pub = new Pub(orderMachine);
	}

	@Test
	public void invalidDrinkId_throwsException() 
	{
		Exception exception = assertThrows(Pub.InvalidDrinkIdExceprion.class, () -> {
			pub.orderDrink(5);
		});
	
		assertEquals("Invalid Drink Id", exception.getMessage());
	}
	
	@Test
	public void orderedDringIsNextToBeServed_NoPriority() {
		Pub.Bartender bartender = pub.new Bartender();
		
		menu.keySet().stream().forEach(drinkId -> 
			assserOrderedDringIsNextToBeServed(drinkId, bartender)
		);
	}

	private void assserOrderedDringIsNextToBeServed(int drinkId, Pub.Bartender bartender) {
		pub.orderDrink(drinkId); 
		
		Beverage orderedBeverage = bartender.getNextOrderedBeverage();
		Beverage beverage = fetchBeverage(drinkId);
		
		assertBeveragesAreEquals(beverage, orderedBeverage);
	}

	private void assertBeveragesAreEquals(Beverage beverage2, Beverage beverage1) {
		assertEquals(beverage2.getId(), beverage1.getId());
		assertEquals(beverage2.getName(), beverage1.getName());
		assertEquals(beverage2.getPriority(), beverage1.getPriority());
	}
	
	@Test
	public void kopanganBucketReturnsEncapsulatedBeverageDetails() {
		menu.keySet().stream().forEach(drinkId ->
			compareKopanganBucketDetailsToBeverage(drinkId)
		);
	}

	private void compareKopanganBucketDetailsToBeverage(int drinkId) {
		Beverage beverage = fetchBeverage(drinkId);
		Beverage kopanganBucket = new KopanganBucket(beverage);
		
		assertEquals(-beverage.getId(), kopanganBucket.getId());
		assertEquals(beverage.getPriority(), kopanganBucket.getPriority());
		assertEquals("KopanganBucket with a " + beverage.getName(), kopanganBucket.getName());
		
		testKopanganBucketPrepare(beverage, kopanganBucket);
	}

	private void testKopanganBucketPrepare(Beverage beverage, Beverage kopanganBucket) {
		PrintStream standardOut = System.out;
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		kopanganBucket.prepare();
		assertEquals("PREPARING a " + beverage.getName() + " in a Kopangan Bucket", 
				outputStreamCaptor.toString().trim());
		System.setOut(standardOut);
	}
	
	
	private Beverage fetchBeverage(int drinkId) {
		return menu.get(drinkId);
	}
	
}
