/*
 *   REMARKS: test1() is the sample test from the file unchanged. The other tests will test other parts of compareTo in the Hand class which compares
 * 			  and returns values based on which hand is stronger. These tests will evaluate how well the program can identify hands and their strengths
 * 			  as well as break ties when necessary.
 * 				NOTE: ALL TESTS IN THE PROGRAM ASSUME THAT THERE IS ONE 52 CARD STANDARD DECK (HENCE, NO COMPLICATED TIES FOR DOUBLE FULL HOUSES OR
 * 					  HANDS THAT REQUIRE 3/4 OF A CARD VALUE (EG. NO 3-3-3-2-2 VS 3-3-3-4-4 BECAUSE THERE WOULD BE SIX 3S)
 */


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JUnitTests {

    @Test
    public void test1() 
	{
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);
		
		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		
		assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    } //sample test


	/*
	 *	Tests a tie for an almost identical hand with only singles.
	 */

	@Test
	public void testHighCardTie(){
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.HEART),
				new Card(6, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(8, Cardable.Suit.HEART), new Card(7, Cardable.Suit.SPADE),
				new Card(6, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "8-7-6-5-3 (th2) beats 8-7-6-5-2 (th1)");
	}


	/*
	 * Tests a tie for Two Pair hands (testing single)
	 */

	@Test
	public void testTwoPairTie1(){
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART),
				new Card(6, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE),
				new Card(6, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "8-8-6-6-3 (th2) beats 8-8-6-6-2 (th1)");
	}

	/*
	 * Tests another tie for two pair hands (testing the smaller double)
	 */

	@Test
	public void testTwoPairTie2(){
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART),
				new Card(5, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE),
				new Card(6, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "8-8-6-6-3 (th1) beats 8-8-5-5-2 (th2)");
	}

	/*
	 * Making sure a straight using LOW Ace still is considered a Straight and is stronger than a triple.
	 */

	@Test
	public void testAceLowStraight(){
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.HEART),
				new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE),
				new Card(8, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "A-2-3-4-5 beats 8-8-8-6-3");
	}

	/*
	 * Testing low ace straight against high ace straight
	 */

	@Test
	public void testLowHighAceStraight(){
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.HEART),
				new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(13, Cardable.Suit.HEART), new Card(14, Cardable.Suit.SPADE),
				new Card(12, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(11, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);


		assertTrue(th1.compareTo(th2) < 0, "A-2-3-4-5 loses against 10-J-Q-K-A");
	}

	/*
	 * Testing a straight flush
	 */

	@Test
	public void testStraightFlush(){
		Cardable[] cards1 = {new Card(14, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.CLUB),
				new Card(12, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(13, Cardable.Suit.HEART), new Card(14, Cardable.Suit.SPADE),
				new Card(12, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(11, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "Straight Flush wins against Straight");

		Cardable[] cards3 = {new Card(2, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.CLUB),
				new Card(12, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB)};
		TestableHand th3 = new Hand();
		th3.addCards(cards3);

		assertTrue(th1.compareTo(th3) > 0, "Straight Flush wins against Flush");

	}

	/*
	 * Testing ties with both a single pair: tiebreaker is largest single
	 */

	@Test
	public void testSinglePairTie(){
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART),
				new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE),
				new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "2-2-3-4-6- loses against 2-2-3-4-6");
	}

	/*
	 * Testing full house and flush
	 */

	@Test
	public void testFullHouseVsFlush(){
		Cardable[] cards1 = {new Card(3, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.HEART),
				new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.HEART), new Card(2, Cardable.Suit.HEART),
				new Card(3, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "Full House wins against Flush");
	}

	/*
	 * testing full house and four of a kind
	 */

	@Test
	public void testFullHouseVsQuads(){
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART),
				new Card(3, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE),
				new Card(2, Cardable.Suit.HEART), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "Full House loses against Four of a Kind");
	}

	/*
	 * Using two exact same straights (except suits) to test a real tie.
	 */

	@Test
	public void testPerfectTieStraight(){
		Cardable[] cards1 = {new Card(5, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB),
				new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART),
				new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) == 0, "Tie: Both straights");

		Cardable[] cards3 = {new Card(5, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB),
				new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.CLUB)};
		TestableHand th3 = new Hand();
		th3.addCards(cards3);

		Cardable[] cards4 = {new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART),
				new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th4 = new Hand();
		th4.addCards(cards4);

		assertTrue(th3.compareTo(th4) == 0, "Tie: Both Straight Flush");

	}

	/*
	 * Test a real tie between two hands with only a double.
	 */

	@Test
	public void testPerfectTieSingleDouble(){
		Cardable[] cards1 = {new Card(5, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB),
				new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(5, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART),
				new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) == 0, "Tie: both with same one pair and same singles");
	}
}
