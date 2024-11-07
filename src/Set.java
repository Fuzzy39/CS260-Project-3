import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Set<E> implements Collection<E>
{
	
	private class myItr implements Iterator<E>
	{

		private int cursor;
		private Set<E> set;
		
		public myItr(Set<E> set)
		{
			this.set = set;
			cursor = 0;
		}
		
		@Override
		public boolean hasNext() 
		{
			if(cursor>=set.size()) return false;
			return set.toArray()[cursor]!=null;
		}

		@Override
		public E next() {
			if(!hasNext()) return null;
			E toReturn = (E) set.toArray()[cursor];
			cursor++;
			return toReturn;
		}
		
	}

	private E[] contents;
	private int size;
	
	
	public Set() {
		clear();
		size = 0;
	}
	
	private void ensureCapacity(int capacity)
	{
		if(contents.length >= capacity) return;
		contents = Arrays.copyOf(contents, capacity*2);
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean contains(Object o) 
	{
		if(o == null) return false;
		
		for(int i = 0; i<size; i++)
		{
			if(o.equals(contents[i])) return true;
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() 
	{
		return new myItr(this);
	}

	@Override
	public Object[] toArray() 
	{
		return Arrays.copyOf(contents, size);
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return (T[])Arrays.copyOf(contents, size);
		
	}

	@Override
	public boolean add(E e) 
	{
		ensureCapacity(size+1);
		if(contains(e)) return false;
		contents[size] = e;
		return true;
	}

	@Override
	public boolean remove(Object o)
	{
		if(o == null) return false;
		
		int i  = 0;
		for(E obj : this)
		{
			if(o.equals(obj))
			{
				contents[i] = contents[size-1];
				size --;
				return true;
				
			}
			i++;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) 
	{

		for( Object obj : c)
		{
			if(!contains(obj)) return false;
		}
		
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for( Object obj : c)
		{
			if(!contains(obj)) return false;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) 
	{
		for(Object obj : c)
		{
			if(contains(obj))
			{
				remove(obj);
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) 
	{
		// TODO Auto-generated method stub
		// I don't want to do this.
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {		
		contents = (E[])(new Object[10]);
		// TODO Auto-generated method stub
		
	}

}
