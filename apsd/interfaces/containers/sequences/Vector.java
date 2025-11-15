package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ReallocableContainer;

public interface Vector<Data> extends ReallocableContainer, MutableSequence<Data> {

  default void ShiftLeft(Natural position) { ShiftLeft(position, Natural.ONE); }
  default void ShiftLeft(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      long iniwrt = idx;
      long wrt = iniwrt;
      for (long rdr = wrt + len; rdr < size; rdr++, wrt++) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
      }
      for (; wrt - iniwrt < len; wrt++) {
        SetAt(null, Natural.Of(wrt));
      }
    }
  }

  default void ShiftFirstLeft() { ShiftLeft(Natural.ZERO, Natural.ONE); }

  default void ShiftLastLeft() {
    if (Size().IsZero()) return;
    ShiftLeft(Size().Decrement(), Natural.ONE);
  }

  default void ShiftRight(Natural position) { ShiftRight(position, Natural.ONE); }
  default void ShiftRight(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      long endwrt = size - 1;
      long wrt = endwrt;
      for (long rdr = wrt - len; rdr >= idx; rdr--, wrt--) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
      }
      for (; endwrt - wrt < len; wrt--) {
        SetAt(null, Natural.Of(wrt));
      }
    }
  }

  default void ShiftFirstRight() { ShiftRight(Natural.ZERO, Natural.ONE); }

  default void ShiftLastRight() {
    if (Size().IsZero()) return;
    ShiftRight(Size().Decrement(), Natural.ONE);
  }

  default Vector<Data> SubVector(Natural start, Natural end) {
    long startIndex = ExcIfOutOfBound(start);
    long endIndex = ExcIfOutOfBound(end);
    if (startIndex > endIndex) throw new IllegalArgumentException("Start index cannot be greater than end index.");
    return (Vector<Data>) SubSequence(start, end);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  default Natural Size() { return ReallocableContainer.super.Size(); }

}
