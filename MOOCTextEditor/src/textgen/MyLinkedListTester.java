/**
 *
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}


	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}

		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}

		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}

	}


	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());

		// TODO: Add more tests here

		String val = shortList.remove(0);
		assertEquals("Removing 0th index", "A", val);
		assertEquals("Checking shortList size", 1, shortList.size());

		try
		{
			String val1 = shortList.remove(5);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}

		try
		{
			String val2 = shortList.remove(-1);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}

		int llval = longerList.remove(6);
		assertEquals("Removing 6th index from longerList", 6, llval);

	}

	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		list1.add(100);
		int valueGot = list1.get(list1.size-1);
		assertEquals("Add at the end", 100,valueGot);

		list1.add(105);
		int valueGot1 = list1.get(list1.size-1);
		assertEquals("Add at the end", 105, valueGot1);
		int prevNodeValue = list1.get(list1.size()-2);
		assertEquals("Checking the list1 size", 5, list1.size());
		assertEquals("Checking prev Node value", 100, prevNodeValue);


		try
		{
			longerList.add(100, 100);;
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}
	}


	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test

		assertEquals("Short List size", 2, shortList.size());
		assertEquals("Longer List size", 10, longerList.size());
		assertEquals("Empty List Size", 0, emptyList.size());
		assertEquals("List1 Size", 3, list1.size());
	}



	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		longerList.add(10, 10);
		int prevNodeValue = longerList.get(9);
		int at10 = longerList.get(10);
		assertEquals("LongerList index 10", 10, at10);
		assertEquals("Longer list size", 11, longerList.size());
		assertEquals("Prev Element",9, prevNodeValue);

		longerList.add(0, 1000);
		int at0 = longerList.get(0);
		int nextNodeValue = longerList.get(1);
		assertEquals("Checking next node", 0, nextNodeValue);

		longerList.add(6, 100);
		int at6 = longerList.get(6);
		int prev = longerList.get(5);
		int next = longerList.get(7);
		assertEquals("checking current node", 100, at6);
		assertEquals("Checking prev node", 4, prev);
		assertEquals("Checking next node", 5, next);


		shortList.add(2, "C");
		String c = shortList.get(2);
		assertEquals("Short list index 2", "C", c);

		try
		{
			longerList.add(-1, 100);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}

		try
		{
			shortList.add(100,  "X");
			fail("Check index out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}

		try
		{
			longerList.add(2, null);
			fail("Check null pointer");
		}
		catch(NullPointerException ex)
		{

		}
	}

	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		longerList.set(0, 1000);
		int valAt0 = longerList.get(0);
		int valAt1 = longerList.get(1);
		int size = longerList.size();
		assertEquals("Setting 0th index", 1000, valAt0);
		assertEquals("checking next node", 1, valAt1);
		assertEquals("Checking size", 10, size);

		try
		{
			shortList.set(5, "Z");
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}

		try
		{
			shortList.set(-1, "Y");
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException ex)
		{

		}

		shortList.set(1, "Z");
		int lSize = shortList.size();
		String prevNode = shortList.get(0);
		assertEquals("Checking set in ShortList", "Z", shortList.get(1));
		assertEquals("Checking size", 2, lSize);
		assertEquals("Checking prev node", "A", prevNode);

		try
		{
			longerList.set(5, null);
			fail("Check null pointer");
		}
		catch(NullPointerException ex)
		{

		}

	}


	// TODO: Optionally add more test methods.

}
