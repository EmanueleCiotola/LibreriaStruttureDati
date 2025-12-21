package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Queue;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data> {

  protected final List<Data> list;

  public WQueue() { this.list = new VList<Data>(); }
  public WQueue(List<Data> list) {
    if (list != null) this.list = list;
    else this.list = new VList<Data>();
  }
  public WQueue(TraversableContainer<Data> container) {
    this.list = new VList<>();
    if (container != null) {
      container.TraverseForward(data -> {
        if (data != null) this.list.InsertLast(data);
        return false;
      });
    }
  }
  public WQueue(List<Data> list, TraversableContainer<Data> container) {
    if (list != null) this.list = list;
    else this.list = new VList<Data>();
    if (container != null) {
      container.TraverseForward(data -> {
        if (data != null) this.list.InsertLast(data);
        return false;
      });
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  
  @Override
  public Natural Size() { return list.Size(); }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  
  @Override
  public void Clear() { list.Clear(); }

  /* ************************************************************************ */
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */
  
  @Override
  public Data Head() {
    if (list.IsEmpty()) return null;
    return list.GetFirst();
  }

  @Override
  public void Dequeue() { list.RemoveFirst(); }

  @Override
  public Data HeadNDequeue() { return list.FirstNRemove(); }

  @Override
  public void Enqueue(Data data) { list.InsertLast(data); }

}
