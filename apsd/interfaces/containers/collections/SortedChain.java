package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.SortedSequence;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> { // Must extend OrderedChain and SortedSequence

  /* ************************************************************************ */
  /* Search predecessor / successor (return index as Natural)                 */
  /* ************************************************************************ */

  default Natural SearchPredecessor(Data data) {
    if (data == null || Size().IsZero()) return null;

    long left = 0;
    long right = Size().ToLong() - 1;
    long pred = -1;

    while (left <= right) {
      long mid = left + (right - left) / 2;
      Data midElem = GetAt(Natural.Of(mid));
      if (midElem.compareTo(data) < 0) {
        pred = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return pred >= 0 ? Natural.Of(pred) : null;
  }

  default Natural SearchSuccessor(Data data) {
    if (data == null || Size().IsZero()) return null;

    long left = 0;
    long right = Size().ToLong() - 1;
    long succ = -1;

    while (left <= right) {
      long mid = left + (right - left) / 2;
      Data midElem = GetAt(Natural.Of(mid));
      if (midElem.compareTo(data) > 0) {
        succ = mid;
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return succ >= 0 ? Natural.Of(succ) : null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) {
    if (data == null) return null;
    long size = Size().ToLong();
    if (size == 0) return null;

    long left = 0;
    long right = size;
    while (left < right) {
      long mid = left + (right - left) / 2;
      Data midElem = GetAt(Natural.Of(mid));
      int cmp = midElem.compareTo(data);
      if (cmp < 0) left = mid + 1;
      else right = mid;
    }

    if (left < size) {
      Data val = GetAt(Natural.Of(left));
      if (val.compareTo(data) == 0) return Natural.Of(left);
    }
    return null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  default void Intersection(SortedChain<Data> chn) {
    Natural i = Natural.ZERO, j = Natural.ZERO;
    while (i.compareTo(Size()) < 0 && j.compareTo(chn.Size()) < 0) {
      int cmp = GetAt(i).compareTo(chn.GetAt(j));
      if (cmp < 0) {
        RemoveAt(i);
      } else {
        j = j.Increment();
        if (cmp == 0) { i = i.Increment(); }
      }
    }
    while (i.compareTo(Size()) < 0) {
      RemoveAt(i);
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
  default Data Min() {
    if (Size().IsZero()) return null;
    return GetAt(Natural.ZERO);
  }

  @Override
  default void RemoveMin() {
    if (Size().IsZero()) return;
    RemoveAt(Natural.ZERO);
  }

  @Override
  default Data MinNRemove() {
    if (Size().IsZero()) return null;
    Data min = GetAt(Natural.ZERO);
    RemoveAt(Natural.ZERO);
    return min;
  }

  @Override
  default Data Max() {
    if (Size().IsZero()) return null;
    return GetAt(Size().Decrement());
  }

  @Override
  default void RemoveMax() {
    if (Size().IsZero()) return;
    RemoveAt(Size().Decrement());
  }

  @Override
  default Data MaxNRemove() {
    if (Size().IsZero()) return null;
    Natural lastIndex = Size().Decrement();
    Data max = GetAt(lastIndex);
    RemoveAt(lastIndex);
    return max;
  }

  @Override
  default Data Predecessor(Data data) {
    Natural index = SearchPredecessor(data);
    return index == null ? null : GetAt(index);
  }

  @Override
  default void RemovePredecessor(Data data) {
    Natural index = SearchPredecessor(data);
    if (index != null) RemoveAt(index);
  }

  @Override
  default Data PredecessorNRemove(Data data) {
    Natural index = SearchPredecessor(data);
    if (index == null) return null;
    Data predecessor = GetAt(index);
    RemoveAt(index);
    return predecessor;
  }

  @Override
  default Data Successor(Data data) {
    Natural index = SearchSuccessor(data);
    return index == null ? null : GetAt(index);
  }

  @Override
  default void RemoveSuccessor(Data data) {
    Natural index = SearchSuccessor(data);
    if (index != null) RemoveAt(index);
  }

  @Override
  default Data SuccessorNRemove(Data data) {
    Natural index = SearchSuccessor(data);
    if (index == null) return null;
    Data successor = GetAt(index);
    RemoveAt(index);
    return successor;
  }

}
