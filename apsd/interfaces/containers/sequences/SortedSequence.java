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
    long size = Size().ToLong();

    long left = 0;
    long right = size;

    while (left < right) {
      long mid = left + (right - left) / 2;
      Data midVal = GetAt(Natural.Of(mid));
      int cmp = midVal.compareTo(data);
      if (cmp < 0) left = mid + 1;
      else right = mid;
    }

    if (left < size) {
      Data natVal = GetAt(Natural.Of(left));
      if (natVal.compareTo(data) == 0) return Natural.Of(left);
    }
    
    return null;
  }

}
