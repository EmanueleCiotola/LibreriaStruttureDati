package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

/** Interface: Dynamic Vector (resizable) with default positional mutators. */
public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> {

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  default void InsertAt(Data data, Natural position) {
    if (position == null) throw new NullPointerException("Natural number cannot be null!");
    if (position.compareTo(Size()) > 0) throw new IndexOutOfBoundsException("Index out of bounds: " + position + "; Size: " + Size() + "!"); //? controlla se posizione > size affidando conversioni a compareTo di Natural. Non uso ExcIfOutOfBound(position) perché per InsertAt è lecito usare position == Size()
    ShiftRight(position, Natural.ONE); //? ShiftRight espande automaticamente quando necessario
    SetAt(data, position);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  default Data AtNRemove(Natural position) {
    ExcIfOutOfBound(position);
    Data old = GetAt(position);
    ShiftLeft(position, Natural.ONE);
    return old;
  }

  /* ************************************************************************ */
  /* Specific member functions of dynamic Vector                              */
  /* ************************************************************************ */

  @Override
  default void ShiftLeft(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      long wrt = idx;
      for (long rdr = wrt + len; rdr < size; rdr++, wrt++) {
        SetAt(GetAt(Natural.Of(rdr)), Natural.Of(wrt));
        SetAt(null, Natural.Of(rdr));
      }
      Reduce(Natural.Of(len));
    }
  }

  @Override
  default void ShiftRight(Natural pos, Natural num) {
    if (pos == null || num == null) throw new NullPointerException("Natural number cannot be null!");
    long p = pos.ToLong();
    long add = num.ToLong();
    long size = Size().ToLong();
    if (p > size) throw new IndexOutOfBoundsException("Index out of bounds: " + p + "; Size: " + Size() + "!"); //? non uso ExcIfOutOfBound(pos) perché per ShiftRight è lecito usare pos == Size()
    if (add <= 0) return;
    Expand(Natural.Of(add));
    for (long rdr = size - 1, wrt = size + add - 1; rdr >= p; rdr--, wrt--) {
      SetAt(GetAt(Natural.Of(rdr)), Natural.Of(wrt));
      SetAt(null, Natural.Of(rdr));
    }
    for (long i = 0; i < add; i++) SetAt(null, Natural.Of(p + i));
  }

  /* ************************************************************************ */
  /* Subvector returning DynVector                                             */
  /* ************************************************************************ */

  default DynVector<Data> SubVector(Natural start, Natural end) {
    long startIndex = ExcIfOutOfBound(start);
    long endIndex = ExcIfOutOfBound(end);
    if (startIndex > endIndex) throw new IllegalArgumentException("Start index cannot be greater than end index.");
    return (DynVector<Data>) SubSequence(start, end); //TODO cast pericoloso e quasi sicuramente sbagliato
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  Natural Size();

}
