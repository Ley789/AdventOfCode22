
public class Range {
    long min;
    long max;
    Range(long s, long e){
    	if (s > e) throw new IllegalArgumentException();
    	min = s;
    	max = e;
    }
    
    public boolean contains(Range other) {
    	return this.min <= other.min && this.max >= other.max;
    }
    
    public boolean overlap(Range other) {
    	return
    	  this.min <= other.min && this.max >= other.min ||
          this.min <= other.max && this.max >= other.max;
    }
    
    @Override
    public boolean equals(Object other) {
    	if(!(other instanceof Range)) return false;
    	Range o = (Range) other;
        return this.min == o.min && this.max == o.max;
    }
}
