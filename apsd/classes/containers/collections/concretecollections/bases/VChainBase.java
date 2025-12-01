package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data> {

  protected final DynVector<Data> vec;

  protected VChainBase() { vec = new DynCircularVector<>(); }
  protected VChainBase(DynVector<Data> vec) { this.vec = vec; }
  public VChainBase(TraversableContainer<Data> con) { vec = new DynCircularVector<>(con); }
  
  protected abstract VChainBase<Data> NewChain(DynVector<Data> vec);

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() { return vec.Size(); }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() { vec.Clear(); }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
    public boolean Remove (Data data) {
    if (data == null) return false;
    Natural position = vec.Search(data);
    if (position == null) return false;
    vec.ShiftLeft(position);
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public ForwardIterator<Data> FIterator() { return vec.FIterator(); }

  @Override
  public BackwardIterator<Data> BIterator() { return vec.BIterator(); }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural index) {
    if (index == null) throw new NullPointerException("Natural number cannot be null!");
    return vec.GetAt(index);
  }

  @Override
  public Sequence<Data> SubSequence(Natural startIndex, Natural endIndex) { return vec.SubVector(startIndex, endIndex); }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  public Data AtNRemove(Natural index) { return vec.AtNRemove(index); }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  public boolean Filter(Predicate<Data> fun) {
    if (fun == null) return false;

    long removedCount = 0; //? conta gli elementi segnati per la rimozione
    MutableForwardIterator <Data> wrt = vec.FIterator();
    
    for (; wrt.IsValid();wrt.Next()){
      if(!fun.Apply(wrt.GetCurrent())){
        removedCount++;
        wrt.SetCurrent(null);
      }
    }
    if (removedCount > 0) {
      wrt.Reset();
      MutableForwardIterator <Data> rdr = vec.FIterator();
      for(; rdr.IsValid(); rdr.Next()){
        if(rdr.GetCurrent() != null){
          Data data = rdr.GetCurrent();
          rdr.SetCurrent(null);
          wrt.SetCurrent(data);
          wrt.Next();
        }
      }
      vec.Reduce(Natural.Of(removedCount));
    }
    return removedCount > 0;
  }

}
