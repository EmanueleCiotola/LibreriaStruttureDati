package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.OrderedSet;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WOrderedSetBase<Data extends Comparable<? super Data>, Chain extends SortedChain<Data>> extends WSetBase<Data, Chain> implements OrderedSet<Data> {
  
  public WOrderedSetBase() { super(); }
  public WOrderedSetBase(Chain chain) { super(chain); }
  public WOrderedSetBase(TraversableContainer<Data> container) { super(container); }
  public WOrderedSetBase(Chain chain, TraversableContainer<Data> container) { super(chain, container); }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public boolean IsEqual(IterableContainer<Data> container) { return chain.IsEqual(container); }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
  public Data Min() { return chain.Min(); }

  @Override
  public Data Max() { return chain.Max(); }

  @Override
  public void RemoveMin() { chain.MinNRemove(); }

  @Override
  public void RemoveMax() { chain.MaxNRemove(); }

  @Override
  public Data MinNRemove() { return chain.MinNRemove(); }

  @Override
  public Data MaxNRemove() { return chain.MaxNRemove(); }

  @Override
  public Data Predecessor(Data data) { return chain.Predecessor(data); }

  @Override
  public void RemovePredecessor(Data data) { chain.RemovePredecessor(data); }

  @Override
  public void RemoveSuccessor(Data data) { chain.RemoveSuccessor(data); }

  @Override
  public Data Successor(Data data) { return chain.Successor(data); }

  @Override
  public Data PredecessorNRemove(Data data) { return chain.PredecessorNRemove(data); }

  @Override
  public Data SuccessorNRemove(Data data) { return chain.SuccessorNRemove(data); }

}
