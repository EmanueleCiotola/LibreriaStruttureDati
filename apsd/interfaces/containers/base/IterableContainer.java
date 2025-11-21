package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data> {

  ForwardIterator<Data> FIterator();

  BackwardIterator<Data> BIterator();

  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null || !Size().equals(other.Size())) return false;
    if (this == other) return true;

    ForwardIterator<Data> it1 = FIterator();
    ForwardIterator<Data> it2 = other.FIterator();

    while (it1.IsValid() && it2.IsValid()) {
      if (!it1.DataNNext().equals(it2.DataNNext())) return false;
    }
    
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */

  @Override
  default boolean TraverseForward(Predicate<Data> predicate) {
    if (predicate == null) return false;
    ForwardIterator<Data> it = FIterator();
    while (it.IsValid()) {
      if (predicate.Apply(it.GetCurrent())) return true;
      it.Next();
    }
    return false;
  }

  @Override
  default boolean TraverseBackward(Predicate<Data> predicate) {
    if (predicate == null) return false;
    BackwardIterator<Data> it = BIterator();
    while (it.IsValid()) {
      if (predicate.Apply(it.GetCurrent())) return true;
      it.Prev();
    }
    return false;
  }

}
