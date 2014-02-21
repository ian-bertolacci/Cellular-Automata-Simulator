package cas.parser.parser_containers;
public class Pair<T,K> {
	T first;
	K second;
	
	public Pair(T first, K second){
		this.first = first;
		this.second = second;
	}
	
	public T getFirst(){
		return this.first;
	}
	
	public K getSecond(){
		return this.second;
	}
}
