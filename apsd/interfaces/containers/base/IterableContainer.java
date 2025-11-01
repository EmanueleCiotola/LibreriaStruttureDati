package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

import java.util.Objects; //! TODO verificare che si possa usare

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data> {

  ForwardIterator<Data> FIterator();

  BackwardIterator<Data> BIterator();

  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null) return false;
    if (this == other) return true;
    if (!Size().equals(other.Size())) return false;

    ForwardIterator<Data> it1 = FIterator();
    ForwardIterator<Data> it2 = other.FIterator();

    while (it1.IsValid() && it2.IsValid()) {
      if (!Objects.equals(it1.GetCurrent(), it2.GetCurrent()))
        return false;
      it1.Next();
      it2.Next();
    }
    return true;
  }
  //! TODO se non si può usare Objects, sostituire while con:
  /*
    while (it1.IsValid() && it2.IsValid()) {
      Data a = it1.GetCurrent();
      Data b = it2.GetCurrent();
      if (a == null) {
        if (b != null) return false;
      } else {
        if (!a.equals(b)) return false;
      }
      it1.Next();
      it2.Next();
    }
  */

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
