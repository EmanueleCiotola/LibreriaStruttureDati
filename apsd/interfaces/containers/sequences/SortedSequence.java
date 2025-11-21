package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<? super Data>> extends Sequence<Data>, SortedIterableContainer<Data> {

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Exists(Data data) {
    return Search(data) != null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) {
    if (data == null) return null;
    final long size = Size().ToLong();
    if (size == 0) return null;

    long lower = 0;
    long higher = size - 1;

    while (lower <= higher) {
      long mid = (lower + higher) >>> 1; //? fa shift logico destro. Calcola quindi l’equivalente di (lower + higher) / 2 bitwise, ma evita possibile overflow
      Natural midNat = Natural.Of(mid);
      Data midVal = GetAt(midNat);
      int cmp = midVal.compareTo(data);
      if (cmp == 0) return Natural.Of(mid);
      if (cmp < 0) lower = mid + 1;
      else higher = mid - 1;
    }
    return null;
  }

}
