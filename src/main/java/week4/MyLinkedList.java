package week4;

import java.util.AbstractList;

/** A class that implements a doubly linked list
 * UC San Diego Intermediate Programming MOOC team
 * Completed by Aleksandra Globa
 * Date: July 14, 2020
 */

public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	public MyLinkedList() {
		this.head = new LLNode<E>();
		this.tail = null;
		this.size = 0;
	}

	private void checkElement(E element) throws NullPointerException {
		if (element == null) {
			throw new NullPointerException("Invalid element");
		}
	}

	private void checkIndex(int index) throws IndexOutOfBoundsException {
		if ((index < 0 || index >= size) && (index != 0 || size != 0))  {
			throw new IndexOutOfBoundsException("Invalid index");
		}
	}

	private LLNode<E> findLast() {
		LLNode<E> tmp = head;
		while (tmp.getNext() != null) {
			tmp = tmp.getNext();
		}
		return tmp;
	}

	private LLNode<E> findByIndex(int index)  {
		checkIndex(index);

		LLNode<E> nodeAtIndex = head;
		for (int i = 0; i < index; i++) {
			nodeAtIndex = nodeAtIndex.getNext();
		}
		return nodeAtIndex;
	}

	// Appends an element to the end of the list
	public boolean add(E element) {
		checkElement(element);

		if (size == 0) {
			head.setData(element);
		} else {
			LLNode<E> lastNode = findLast();
			LLNode<E> newNode = new LLNode<E>(element, lastNode, null);
			lastNode.setNext(newNode);
			tail = newNode;
		}

		size++;
		return true;
	}

	// Get the element at position index IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) {
		checkIndex(index);

		LLNode<E> node = findByIndex(index);
		return node.getData();
	}

	// Add an element to the list at the specified index( where the element should be added)
	public void add(int index, E element) {
		checkElement(element);
		checkIndex(index);

		LLNode<E> nodeAtIndex = findByIndex(index);
		LLNode<E> prevNode = nodeAtIndex.getPrev();
		LLNode<E> newNode = new LLNode<E>(element, prevNode, nodeAtIndex);

		if (prevNode != null) {
			prevNode.setNext(newNode);
		} else {
			head.setNext(newNode);
		}

		nodeAtIndex.setPrev(newNode);

		if (index == size - 1) {
			tail = newNode;
		}

		if (index == 0) {
			head = newNode;
		}

		size++;
	}

	public int size() {
		return size;
	}

	// Remove a node at the specified index and return its data element.
	public E remove(int index) {
		checkIndex(index);

		LLNode<E> nodeToDel = findByIndex(index);
		LLNode<E> prevNode = nodeToDel.getPrev();
		LLNode<E> nextNode = nodeToDel.getNext();

		nodeToDel.setPrev(null);
		nodeToDel.setNext(null);

		if (prevNode != null) {
			prevNode.setNext(nextNode);
		}

		if (nextNode != null) {
			nextNode.setPrev(prevNode);
		}

		if (index == 0) {
			head = nextNode != null ? nextNode : new LLNode<>();
		}

		if (index == size - 1) {
			tail = nextNode;
		}

		size--;
		return nodeToDel.data;
	}

	// Set an index position in the list to a new element
	public E set(int index, E element) {
		checkElement(element);
		checkIndex(index);

		LLNode<E> nodeToChange = findByIndex(index);
		E oldValue = nodeToChange.data;
		nodeToChange.setData(element);
		return oldValue;
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode() {
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E nodeData, LLNode prevNode, LLNode nextNode) {
		this.data = nodeData;
		this.prev = prevNode;
		this.next = nextNode;
	}

	public E getData() {
		return data;
	}

	public void setData(E newData) {
		data = newData;
	}

	public LLNode<E> getNext() {
		return next;
	}

	public void setNext(LLNode<E>nextNode){
		next = nextNode;
	}

	public LLNode<E> getPrev() {
		return prev;
	}

	public void setPrev(LLNode<E> prevNode){
		prev = prevNode;
	}
}