package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Stack;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data> {

  protected final List<Data> list;

  public WStack() { this.list = new VList<Data>(); }
  public WStack(List<Data> list) { this.list = list; }
  public WStack(TraversableContainer<Data> con) { this.list = new VList<Data>(con); }
  public WStack(List<Data> list, TraversableContainer<Data> container) { //TODO
    this(list != null ? list : new VList<Data>());
    if (container == null) return;
    container.TraverseForward(data -> {
      if (data != null) this.list.InsertLast(data);
      return false;
    });
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
  /* Override specific member functions from Stack                            */
  /* ************************************************************************ */
  
  @Override
  public Data Top() {
    if (list.IsEmpty()) return null;
    return list.GetFirst();
  }

  @Override
  public void Pop() {
    if (list.IsEmpty()) return;
    list.RemoveFirst();
  }

  @Override
  public Data TopNPop() {
    Data top = Top();
    Pop();
    return top;
  }

  @Override
  public void SwapTop(Data data) {
    if (!list.IsEmpty()) { Pop(); }
    Push(data);
  }

  @Override
  public Data TopNSwap(Data data) {
    Data top = Top();
    SwapTop(data);
    return top;
  }

  @Override
  public void Push(Data data) { list.InsertFirst(data); }

}
