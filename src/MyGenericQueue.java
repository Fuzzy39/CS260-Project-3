
public class MyGenericQueue<E> extends MyLinkedList<E>
{

		// head of queue is first in list.
	
		public E dequeue() 
		{
			E toReturn = this.get(0);
			if(toReturn == null) return null;
			
			this.remove(0);
			return toReturn;
		}

		public void enqueue(E toQueue)
		{
			this.add(toQueue);
		}
}
