package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Stack;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data> {

  protected final List<Data> list;

  public WStack() { this.list = new VList<>(); }
  public WStack(List<Data> list) { this.list = list; }
  public WStack(TraversableContainer<Data> container) { this.list = new VList<>(container); }
  public WStack(List<Data> list, TraversableContainer<Data> container) {
    this.list = list;
    container.TraverseForward(data -> { this.list.InsertLast(data); return false; });
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
    return list.GetLast();
  }

  @Override
  public void Pop() { list.RemoveLast(); }

  @Override
  public Data TopNPop() { return list.LastNRemove(); }

  @Override
  public void SwapTop(Data data) {
    if (list.IsEmpty()) { Push(data); return; }
    list.SetLast(data);
  }

  @Override
  public Data TopNSwap(Data data) {
    if (list.IsEmpty()) {
      Push(data);
      return null;
    }
    return list.GetNSetLast(data);
  }

  @Override
  public void Push(Data data) { list.InsertLast(data); }

}
