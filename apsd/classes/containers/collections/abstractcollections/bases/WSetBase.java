package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WSetBase<Data, chain extends Chain<Data>> implements Set<Data> {

  protected chain chain;

  public WSetBase() { ChainAlloc(); }
  public WSetBase(chain chain) { this.chain = chain; }
  public WSetBase(TraversableContainer<Data> container) {
    ChainAlloc();
    container.TraverseForward(data -> {
      chain.InsertIfAbsent(data);
      return false;
    });
  }
  public WSetBase(chain chain, TraversableContainer<Data> container) {
    this.chain = chain;
    container.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }

  abstract protected void ChainAlloc();

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() { return chain.Size(); }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() { chain.Clear(); }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  public boolean Insert(Data data) { return chain.InsertIfAbsent(data); }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove(Data data) { return chain.Remove(data); }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public ForwardIterator<Data> FIterator() { return chain.FIterator(); }

  @Override
  public BackwardIterator<Data> BIterator() { return chain.BIterator(); }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  @Override
  public boolean Filter(Predicate<Data> pred) { return chain.Filter(pred); }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  @Override
  public void Intersection(Set<Data> other) {
    if (other == null) return;
    chain.Filter(elem -> other.Exists(elem));
  }

}
