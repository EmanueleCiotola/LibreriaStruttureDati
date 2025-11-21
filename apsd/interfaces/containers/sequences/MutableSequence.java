package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> {

  default void SetAt(Data data, Natural position) {
    long index = ExcIfOutOfBound(position);
    MutableForwardIterator<Data> it = FIterator();
    it.Next(index);
    it.SetCurrent(data);
  }

  default Data GetNSetAt(Data data, Natural position) {
    long index = ExcIfOutOfBound(position);
    MutableForwardIterator<Data> it = FIterator();
    it.Next(index);
    Data old = it.GetCurrent();
    it.SetCurrent(data);
    return old;
  }

  default void SetFirst(Data data) { SetAt(data, Natural.ZERO); }

  default Data GetNSetFirst(Data data) { return GetNSetAt(data, Natural.ZERO); }

  default void SetLast(Data data) {
    if (Size().IsZero()) throw new IndexOutOfBoundsException("SetLast on empty sequence"); //? in questo caso si potrebbe inserire l'elemento come primo della sequenza, ma per evitare comportamenti indesiderati escludo questa possibilità
    SetAt(data, Size().Decrement());
  }

  default Data GetNSetLast(Data data) {
    if (Size().IsZero()) throw new IndexOutOfBoundsException("GetNSetLast on empty sequence");
    return GetNSetAt(data, Size().Decrement());
  }

  default void Swap(Natural pos1, Natural pos2) {
    long idx1 = ExcIfOutOfBound(pos1);
    long idx2 = ExcIfOutOfBound(pos2);
    if (idx1 != idx2) {
      Data data1 = GetAt(pos1);
      Data data2 = GetAt(pos2);
      SetAt(data2, pos1);
      SetAt(data1, pos2);
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  MutableSequence<Data> SubSequence(Natural from, Natural to);

}
