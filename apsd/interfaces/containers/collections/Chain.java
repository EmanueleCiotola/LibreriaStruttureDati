package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data>, Set<Data> {

  default boolean InsertIfAbsent(Data data) { return !Exists(data) ? Insert(data) : false; }

  default void RemoveOccurrences(Data data) { Filter(elem -> !elem.equals(data)); }

  default Chain<Data> SubChain(Natural from, Natural to) { return (Chain<Data>) SubSequence(from, to); }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) {
    if (data == null) return null;
    return RemovableAtSequence.super.Search(data);
  }

}
