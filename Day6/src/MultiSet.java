import java.util.HashMap; // import the HashMap class


public class MultiSet<K> {
	private HashMap<K, Long> multiSet = new HashMap<K, Long>();

	public long contains(K k) {
		if (!multiSet.containsKey(k)) return 0;
	    return multiSet.get(k);
	}
	
	public void add(K k) {
		var v = multiSet.getOrDefault(k, (long) 0);
		multiSet.put(k, v + 1);
	}
	
	public void remove(K k) {
       if (!multiSet.containsKey(k)) return;
	   var v = multiSet.get(k) - (long) 1;
       if (v == 0) {multiSet.remove(k);} else {multiSet.put(k, v);}
	}
	
	public int distinctElements() {
		return multiSet.keySet().size();
	}
}
