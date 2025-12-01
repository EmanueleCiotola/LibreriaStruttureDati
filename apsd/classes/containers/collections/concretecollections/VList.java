package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on (dynamic circular) vector. */
public class VList<Data> extends VChainBase<Data> implements List<Data> {

  public VList() { super(); }
  public VList(TraversableContainer<Data> container) { super(container); }
  protected VList(DynVector<Data> vec) { super(vec); }

  // NewChain
  @Override
  protected VChainBase<Data> NewChain(DynVector<Data> vec) { return new VList<>(vec); }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  @Override
  public MutableForwardIterator<Data> FIterator() { return vec.FIterator(); }

  @Override
  public MutableBackwardIterator<Data> BIterator() { return vec.BIterator(); }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  public void SetAt(Data data, Natural position) { 
    if (position == null) throw new NullPointerException("Position cannot be null!");
    vec.SetAt(data, position); 
  }

  @Override
  public MutableSequence<Data> SubSequence(Natural from, Natural to) { 
    if (from == null || to == null) throw new NullPointerException("Indices cannot be null!");
    return (MutableSequence<Data>) vec.SubVector(from, to); 
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  public void InsertAt(Data data, Natural position) { 
    if (position == null) throw new NullPointerException("Position cannot be null!");
    vec.InsertAt(data, position); 
  }

}
