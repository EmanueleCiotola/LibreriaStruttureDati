package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Queue;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data> {

  protected final List<Data> lst;

  public WQueue() { this.lst = new VList<>(); }
  public WQueue(List<Data> lst) { this.lst = lst; }
  public WQueue(TraversableContainer<Data> container) { this.lst = new VList<>(container); }
  public WQueue(List<Data> lst, TraversableContainer<Data> container){
    this.lst = lst;
    container.TraverseForward(data -> {this.lst.Insert(data); return false;}); //TODO forse InsertLast?
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  
  @Override
  public Natural Size() { return lst.Size(); }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  
  @Override
  public void Clear() { lst.Clear(); }

  /* ************************************************************************ */
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */
  
  @Override
  public Data Head() {
    if (lst.IsEmpty()) return null;
    return lst.GetFirst();
  }

  @Override
  public void Dequeue() { lst.RemoveFirst(); }

  @Override
  public Data HeadNDequeue() { return lst.FirstNRemove(); }

  @Override
  public void Enqueue(Data data) { lst.InsertLast(data); }

}
