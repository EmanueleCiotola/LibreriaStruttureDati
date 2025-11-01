package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data> {

  default void Prev() { Prev(Natural.ONE); }
  default void Prev(Natural steps) {
    if (steps == null) throw new NullPointerException("The number of steps cannot be null!");
    for (long cnt = steps.ToLong(); cnt > 0 && IsValid(); cnt--) DataNPrev();
  }

  Data DataNPrev();

  default boolean ForEachBackward(Predicate<Data> fun) {
    if (fun != null) {
      while (IsValid()) {
        if (fun.Apply(DataNPrev())) { return true; }
      }
    }
    return false;
  }

}
