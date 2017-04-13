package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 *
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		size = 0;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element )
	{
		// TODO: Implement this method
		boolean isAdded = false;
		if (element != null)
		{
			LLNode<E> toBeAdded = new LLNode<E>(element);
			toBeAdded.next = tail.prev.next;
			toBeAdded.prev = toBeAdded.next.prev;
			toBeAdded.prev.next = toBeAdded;
			toBeAdded.next.prev = toBeAdded;
			size += 1;
			isAdded = true;
		}
		else
		{
			throw new NullPointerException("Null values cannot be added to linked list");
		}
		return isAdded;
	}

	/** Get the element at position index
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
		// TODO: Implement this method.
		E value = null;
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("Index value should be within the list length");
		}
		else
		{
			LLNode<E> currentNode = head.next;
			int i = 0;
			while(i != index)
			{
				currentNode = currentNode.next;
				i++;
			}
			value = currentNode.data;
		}
		return value;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element )
	{
		// TODO: Implement this method
		if(index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException("Index value should be within the list length");
		}
		else if (element == null)
		{
			throw new NullPointerException("Null values cannot be added to linked list");
		}
		else
		{
			LLNode<E> currentNode = head.next;
			int i = 0;
			while(i != index)
			{
				currentNode = currentNode.next;
				i++;
			}
			LLNode<E> newNode = new LLNode<E>(element);
			newNode.next = currentNode;
			newNode.prev = newNode.next.prev;
			newNode.prev.next = newNode;
			newNode.next.prev = newNode;
			size += 1;
		}
	}


	/** Return the size of the list */
	public int size()
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 *
	 */
	public E remove(int index)
	{
		// TODO: Implement this method
		E value = null;
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("Index value should be within the list length");
		}
		else
		{
			LLNode<E> currentNode = head.next;
			int i = 0;
			while(i != index)
			{
				currentNode = currentNode.next;
				i++;
			}
			currentNode.prev.next = currentNode.next;
			currentNode.next.prev = currentNode.prev;
			value = currentNode.data;
			size -= 1;
			currentNode.data = null;
			currentNode.next = null;
			currentNode.prev = null;
		}
		return value;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element)
	{
		// TODO: Implement this method
		E toBeReturned = null;
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Index value should be within the list length");
		}
		else if(element == null)
		{
			throw new NullPointerException("Null values cannot be added to linked list");
		}
		else
		{
			LLNode<E> currentNode = head.next;
			int i = 0;
			while(i != index)
			{
				currentNode = currentNode.next;
				i++;
			}
			toBeReturned = currentNode.data;
			currentNode.data = element;
		}
		return toBeReturned;
	}
}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	public LLNode()
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e)
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
