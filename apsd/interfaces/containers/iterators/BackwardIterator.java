package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data> {

  default void Prev() { Prev(1); }
  default void Prev(Natural steps) {
    if (steps == null) throw new NullPointerException("The number of steps cannot be null!");
    Prev(steps.ToLong());
  }
  default void Prev(long steps) {
    if (steps < 0) throw new IllegalArgumentException("The number of steps cannot be negative!");
    for (; steps > 0 && IsValid(); steps--) DataNPrev();
  }

  Data DataNPrev();

  default boolean ForEachBackward(Predicate<Data> fun) {
    if (fun == null) throw new NullPointerException("Predicate cannot be null!");
    while (IsValid()) if (fun.Apply(DataNPrev())) return true;
    return false;
  }

}
