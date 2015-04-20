package aspects;
import java.util.LinkedList;


import java.util.List;
import java.util.Iterator;
import java.util.WeakHashMap;

import org.aspectj.lang.annotation.*;

@Aspect
public abstract class ObserverPattern 
{    
	public interface Subject {
	}
	
	public interface Observer {
		public void update(Subject subject);
	}
  
    private WeakHashMap<Subject, List<Observer>> perSubjectObservers;
    protected List<Observer> getObservers(Subject s){
    	if(perSubjectObservers==null){
    		perSubjectObservers = new WeakHashMap<Subject, List<Observer>>();
    	}
    	List<Observer> observers = perSubjectObservers.get(s);
    	if(observers == null){
    		observers = new LinkedList<Observer>();
    		perSubjectObservers.put(s, observers);
    	}
		return observers;
    }
    public boolean isEmpty(Subject s){
    	if(perSubjectObservers==null)
    		return true;
    	else
    	return perSubjectObservers.get(s).isEmpty();
    }
    public void addObserver(Subject s,Observer o){
    	getObservers(s).add(o);
    }
    
    public void removeObserver(Subject s,Observer o){
    	getObservers(s).remove(o);
    }
    
    protected void updateObserver(Subject s,Observer o){
    	o.update(s);
    }
    @Pointcut("")
    abstract protected void subjectChange(Subject s);
     
    @After("subjectChange(s)")
    public void ObsUpdate(Subject s){
    	Iterator<Observer> iter=getObservers(s).iterator();
    	while(iter.hasNext()){
    		updateObserver(s,iter.next());
    	}
    } 
    
}
