package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> {

  public VSortedChain() { super(); }
  public VSortedChain(TraversableContainer<Data> container) { super(container); }
  public VSortedChain(VSortedChain<Data> chain) { super(chain.vec); }
  protected VSortedChain(DynVector<Data> vec) { super(vec); }

  @Override
  protected VChainBase<Data> NewChain(DynVector<Data> vec) { return new VSortedChain<>(vec); }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  public boolean Insert(Data data) {
    if (data == null) return false;
    Natural position = SearchSuccessor(data);

    if (position == null) vec.InsertAt(data, vec.Size());
    else vec.InsertAt(data, position);
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  @Override
  public boolean InsertIfAbsent(Data data) {
    if (data == null) return false;

    Natural succ = SearchSuccessor(data);
    long position = (succ == null) ? vec.Size().ToLong() : succ.ToLong();

    if (position > 0) {
      Data prev = vec.GetAt(Natural.Of(position - 1));
      if (prev != null && prev.compareTo(data) == 0) return false;
    }

    vec.InsertAt(data, Natural.Of(position));
    return true;
  }

  @Override
  public void RemoveOccurrences(Data data) {
    if (data == null) return;

    Natural first = Search(data);
    if (first == null) return;

    Natural succ = SearchSuccessor(data);
    long LFirst = first.ToLong();
    long LLast = (succ == null) ? vec.Size().ToLong() : succ.ToLong();
    long count = LLast - LFirst;

    if (count > 0) vec.ShiftLeft(Natural.Of(LFirst), Natural.Of(count));
  }

}
