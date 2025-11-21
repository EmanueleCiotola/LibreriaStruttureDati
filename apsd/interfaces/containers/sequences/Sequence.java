package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data> {

  default Data GetAt(Natural index) {
    long idx = ExcIfOutOfBound(index);
    ForwardIterator<Data> it = FIterator();
    it.Next(idx);
    return it.GetCurrent();
  }

  default Data GetFirst() {
    return GetAt(Natural.ZERO);
  }

  default Data GetLast() {
    if (Size().IsZero()) throw new IndexOutOfBoundsException("GetLast on empty sequence");
    return GetAt(Size().Decrement());
  }

  default Natural Search(Data data) {
    final Box<Long> idx = new Box<>(-1L);
    if (TraverseForward(current -> {
      idx.Set(idx.Get() + 1);
      return (current == null && data == null) || (current.equals(data) && current != null && data != null);
    })) return Natural.Of(idx.Get());
    return null;
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
