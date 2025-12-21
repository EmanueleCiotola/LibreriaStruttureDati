package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data>, Set<Data> {

  default boolean InsertIfAbsent(Data data) { return !Exists(data) ? Insert(data) : false; }

  default void RemoveOccurrences(Data data) { Filter(elem -> !elem.equals(data)); }

  default Chain<Data> SubChain(Natural start, Natural end) { return (Chain<Data>) SubSequence(start, end); }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) {
    if (data == null) return null;
    return RemovableAtSequence.super.Search(data);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null) return false;
    if (this == other) return true;
    if (this.Size().compareTo(other.Size()) != 0) return false;

    ForwardIterator<Data> it1 = this.FIterator();
    ForwardIterator<Data> it2 = other.FIterator();

    while (it1.IsValid() && it2.IsValid()) {
      Data data1 = it1.DataNNext();
      Data data2 = it2.DataNNext();

      if ((data1 == null && data2 != null) || (data1 != null && !data1.equals(data2))) return false;
    }

    return true; 
  }

}
