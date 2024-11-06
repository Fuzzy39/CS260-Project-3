

import java.lang.reflect.Array;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements Cloneable, List<E> {

	private class ListNode
	{
		public E value;
		public ListNode next;
		
		public ListNode(E element, ListNode link)
		{
			this.value = element;
			this.next = link;
		}
		
		public ListNode(ListNode cloneFrom)
		{
			
			// can't clone this, so we don't.
			value = cloneFrom.value;
			
			next = null;
			if(cloneFrom.next!=null)
			{
				next = new ListNode(cloneFrom.next);
			}
		}
		
		public ListNode(E element)
		{
			this(element, null);
		}
		
		
		
	
	}
	

	public class MyItr implements ListIterator<E> 
	{
		private MyLinkedList<E> list;
		private int cursorIndex;
		private ListNode cursor;
		
		public MyItr(MyLinkedList<E> list) 
		{
			this.list = list;
			cursor = list.head;
			this.cursorIndex = 0;
		}
	
		@Override
		public boolean hasNext() 
		{
			return cursorIndex < list.size();
		}
	
		@Override
		public E next() {
			
			if(cursor == null) return null;
			E toReturn = cursor.value;
			cursor = cursor.next;
			cursorIndex++;
			return toReturn;
		}
	
		@Override
		public boolean hasPrevious() 
		{
			return cursorIndex!=0;
		}
	
		@Override
		public E previous()
		{
			
			// O(n).
			
			ListNode old = cursor;
			ListNode toReturn = list.head;
			
			if(toReturn == null ) return null;
			if(previousIndex()<0) throw new IndexOutOfBoundsException();
			
			while(toReturn.next!=old)
			{
				toReturn = toReturn.next;
			}
			
			cursor = toReturn;
			cursorIndex--;
			
			return toReturn.value;
		}
	
		@Override
		public int nextIndex()
		{
			if (!hasNext()) return cursorIndex;
			return cursorIndex + 1;
		
		}
	
		@Override
		public int previousIndex() {
			return cursorIndex-1;
		}
	
		@Override
		public void remove()
		{
			// O(n).
			if(cursor == null) throw new UnsupportedOperationException();
			
			ListNode toRemove = cursor;
			if(cursorIndex == 0)
			{
				cursor = cursor.next;
				list.head = cursor;
				return;
			}
			
			ListNode previous = list.head;
			while(previous.next!=toRemove)
			{
				previous = previous.next;
			}
			
			previous.next = toRemove.next;
			cursor = previous;
			cursorIndex--;
			
			
		}
	
		@Override
		public void set(E e) {
			// optional
			throw new UnsupportedOperationException();
		}
	
		@Override
		public void add(E e) 
		{
			// O(1).
			
			if(cursor == null)
			{
				list.head = new ListNode(e);
				cursor = list.head;
				cursorIndex = 1;
				return;
			}
			
			ListNode after = cursor.next;
			cursor.next = new ListNode(e, after);
			cursorIndex++;
			
		}

	}

	
	private ListNode head;
	
	
	public MyLinkedList() 
	{
		head = null;
	}

	@Override
	public int size() 
	{
		// should be O(n).
		
		if(isEmpty()) return 0;
		
		// iterate through all nodes, counting, until you can't.
		int size = 1;
		for(ListNode node = head; node.next!=null; node=node.next) size++;
		
		return size;
	}

	@Override
	public boolean isEmpty() 
	{
		return head == null;
	}

	@Override
	public boolean contains(Object o) {
		// should be O(n).
		
		// I can't exactly figure out why this line doesn't work, but it's not too important
		//if(!(o instanceof E)) return false;
		
		if(isEmpty()) return false;
		for(ListNode node = head; node.next!=null; node=node.next)
		{
			if(node.value.equals(o)) return true;
		}

		
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		
		return new MyItr(this);
	}

	@Override
	public Object[] toArray() 
	{
		// O(n)
		
		Object[] array = new Object[size()];
		
		// iterate through the list, copy to array.
		
		if(isEmpty()) return array;
		int index = 0;
		for(ListNode node = head; node!=null; node=node.next)
		{
			array[index] = node.value;
			index++;
		}
		
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) 
	{
		if(a == null) throw new NullPointerException();
		
		int size = size();
		if (a.length<size)
		{
			// I clearly don't understand generics that well.
			// I mean this is reflection as well.
			Class<?> type = a.getClass().getComponentType();
			// I'm... pretty sure this is fine. Maybe.
			a = (T[]) Array.newInstance(type, size);
			
			// I wanted to do a = new T[size], but you can't instantiate
			// a generic type array... unless you do what I did. Apparently.
			// I read that these sorts of things are because generics were not originally
			// part of java, and putting them in had some weird internal consequences.
		}
		
	
		
		if(isEmpty())
		{
			if( a.length==0) return a;
			
			// this seems odd, but I am meeting the specification in the documentation here.
			a[0] = null;
			return a;
		}
		
		// iterate through the list, copy to array.
		
		int index = 0;
		for(ListNode node = head; node!=null; node=node.next)
		{
			// we definitely can't guarantee that E can cast to T, but
			// Apparently it's not possible to check without recording E in the constructor.
			// either way, it's going to throw an exception, so whatever.
			a[index] = (T) node.value;
			index++;
		}
		
		if(a.length>size)
		{
			a[size]=null;
		}
		
		return a;
	}

	@Override
	public boolean add(E e) {
		// O(n)
		
		// find the last node.
		ListNode node = head;
		if(node == null)
		{
			head =  new ListNode(e, null);
			return true;
		}
		
		for(; node.next != null; node = node.next);
		
		// add a new node.
		ListNode toAdd = new ListNode(e, null);
		node.next = toAdd;
		
		return true;
	}

	@Override
	public boolean remove(Object o) 
	{
		// O(n)
		
		ListNode previous = null;
		ListNode node = head;
		for(; node.next != null; node = node.next)
		{
			if(node.value.equals(o))
			{
				remove(previous, node);
				return true;
			}
			
			previous = node;
		}
		
		return false;
	}

	private void remove(ListNode previous, ListNode toRemove)
	{
		// I thought this would be complicated
		// probably because last time I did this I had to free things I malloc'd
		// yay, not writing in C.
		ListNode after = toRemove.next;
		previous.next = after;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		// at least O(n^2), depending on how iterating over c works. 
		// Could probably be made better?
		
		for(Object obj : c)
		{
			if(!contains(obj))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) 
	{
		// You know, it would make a lot of sense if I added size as a field.
		// ah well.
		return addAll(size(), c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// this is optional, so.
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// this is optional, so.
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
			
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void clear() 
	{
		// after writing C this really feels like magic.
		head = null;
		
	}

	@Override
	public E get(int index) 
	{
		// O(n)
		return getNode(index).value;
	}
	
	private ListNode getNode(int index)
	{
		ListNode node = head;
		for(int i = 0; i< index; i++)
		{
			node = node.next;
			if(node == null)
			{
				throw new IndexOutOfBoundsException();
			}
		}		

		
		return node;
	}

	@Override
	public E set(int index, E element) 
	{
		// O(n)
		ListNode node = head;
		for(int i = 0; i< index; i++)
		{
			node = node.next;
			if(node == null) 
			{
				throw new IndexOutOfBoundsException();
			}
		}		

		
		node.value = element;
		return element;
		
	}

	@Override
	public void add(int index, E element) 
	{
		
		// O(n)
		
		ListNode previous = null;
		ListNode node = head;
		for(int i = 0; i< index; i++)
		{
			if(node == null) 
			{
				throw new IndexOutOfBoundsException();
			}
			previous = node;
			node = node.next;
			
			
		}		
		
		ListNode newNode = new ListNode(element, node);
		if(previous == null)
		{
			head = newNode;
			return;
		}
		
		previous.next = newNode;
		
	
	
		
	}

	@Override
	public E remove(int index) 
	{
		ListNode previous = null;
		ListNode node = head;
		for(int i = 0; i< index; i++)
		{
			if(node == null) 
			{
				throw new IndexOutOfBoundsException();
			}
			previous = node;
			node = node.next;
		}	
		
		ListNode toRemove = node;
		previous.next=toRemove.next;
		return toRemove.value;
	}

	@Override
	public int indexOf(Object o) {
		// You know, it occurs to me that making the iterator first might've been helpful, given how every single
		// method here iterates through the list
		ListNode node = head;
		for(int i = 0; node!=null; i++)
		{
			if(node.value.equals(o))
			{
				return i;
			}
			node = node.next;
		}		
		
		return -1; 
	}

	@Override
	public int lastIndexOf(Object o) 
	{
		int lastIndex = -1;
		ListNode node = head;
		for(int i = 0; node!=null; i++)
		{
			if(node.value.equals(o))
			{
				lastIndex = i;
			}
			node = node.next;
		}		
		
		return lastIndex; 
		
	}

	@Override
	public ListIterator<E> listIterator() {
		return new MyItr(this);
		
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		MyItr toReturn = new MyItr(this);
		for(int i = 0; i< index; i++)
		{
			toReturn.next();
		}
		return toReturn;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) 
	{
		// O(n), or probably about 2n.
		// could be made better
		
		MyLinkedList<E> toReturn = new MyLinkedList<E>();
		toReturn.head = new ListNode(get(fromIndex));
		
		// truncate the list to size.
		toReturn.getNode(toIndex-fromIndex-1).next = null;
	
		return toReturn;
		
	}
	
	@Override
	public String toString()
	{
		String toReturn = "MyLinkedList [ ";
		for(E obj: this)
		{
			toReturn +=obj.toString()+", ";
		}
		toReturn = toReturn.substring(0, toReturn.length()-2);
		return toReturn +" ]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		MyLinkedList<E> toReturn = (MyLinkedList<E>)super.clone();
		toReturn.head = new ListNode(head);
		return toReturn;
	}

}
