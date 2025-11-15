package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data> {

  default Data GetAt(Natural index) {
    long idx = ExcIfOutOfBound(index);
    ForwardIterator<Data> iterator = FIterator();
    iterator.Next(idx);
    return iterator.GetCurrent();
  }

  default Data GetFirst() {
    return GetAt(Natural.ZERO);
  }

  default Data GetLast() {
    if (Size().IsZero()) throw new IndexOutOfBoundsException("GetLast on empty sequence");
    return GetAt(Size().Decrement());
  }

  default Natural Search(Data data) {
    final Box<Long> index = new Box<>(0L);
    final Box<Boolean> found = new Box<>(false);

    TraverseForward(elem -> {
      if (data == null ? elem == null : data.equals(elem)) {
        found.Set(true);
        return true;
      }
      index.Set(index.Get() + 1);
      return false;
    });

    return found.Get() ? Natural.Of(index.Get()) : null;
  }

  default boolean IsInBound(Natural position) {
    if (position == null) return false;
    long index = position.ToLong();
    return index < Size().ToLong();
  }

  default long ExcIfOutOfBound(Natural num) {
    if (num == null) throw new NullPointerException("Natural number cannot be null!");
    long index = num.ToLong();
    if (index >= Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + index + "; Size: " + Size() + "!");
    return index;
  }

  Sequence<Data> SubSequence(Natural from, Natural to);

}
