package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore in avanti. */
public interface ForwardIterator<Data> extends Iterator<Data> {

  default void Next() { Next(1); }
  default void Next(Natural steps) {
    if (steps == null) throw new NullPointerException("The number of steps cannot be null!");
    Next(steps.ToLong());
  }
  default void Next(long steps) {
    if (steps < 0) throw new IllegalArgumentException("The number of steps cannot be negative!");
    for (; steps > 0 && IsValid(); steps--) DataNNext();
  }

  Data DataNNext();

  default boolean ForEachForward(Predicate<Data> fun) {
    if (fun == null) throw new NullPointerException("Predicate cannot be null!");
    while (IsValid()) if (fun.Apply(DataNNext())) return true;
    return false;
  }

}
