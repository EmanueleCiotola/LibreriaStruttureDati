package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.SortedSequence;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> {

  /* ************************************************************************ */
  /* Search predecessor / successor (return index as Natural)                 */
  /* ************************************************************************ */

  default Natural SearchPredecessor(Data data) { //TODO potrebbe casare errori insieme a searchsuccessor
    if (data == null || Size().IsZero()) return null;

    long left = 0;
    long right = Size().ToLong() - 1;
    long predecessor = -1;

    while (left <= right) {
      long mid = left + (right - left) / 2; //? utilizza la distanza per evitare overflow
      Natural midNat = Natural.Of(mid);
      Data midVal = GetAt(midNat);
      //TODO questo risolve un errore
      // if (midVal == null) { // skip holes that may appear after shifts
      //   right = mid - 1;
      //   continue;
      // }
      if (midVal.compareTo(data) < 0) {
        predecessor = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return predecessor >= 0 ? Natural.Of(predecessor) : null;
  }

  default Natural SearchSuccessor(Data data) {
    if (data == null || Size().IsZero()) return null;

    long left = 0;
    long right = Size().ToLong() - 1;
    long successor = -1;

    while (left <= right) {
      long mid = left + (right - left) / 2; //? utilizza la distanza per evitare overflow
      Natural midNat = Natural.Of(mid);
      Data midVal = GetAt(midNat);
      //TODO questo risolve un errore
      // if (midVal == null) { // skip holes that may appear after shifts
      //   right = mid - 1;
      //   continue;
      // }
      if (midVal.compareTo(data) > 0) {
        successor = mid;
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return successor >= 0 ? Natural.Of(successor) : null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) { return SortedSequence.super.Search(data); }

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
