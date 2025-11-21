package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.SortedSequence;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.BackwardIterator;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> { // Must extend OrderedChain and SortedSequence

  /* ************************************************************************ */
  /* Search predecessor / successor (return index as Natural)                 */
  /* ************************************************************************ */

  default Natural SearchPredecessor(Data dat) {
    if (dat == null) return null;
    final long[] idx = new long[]{0L};
    final long[] pred = new long[]{-1L};
    TraverseForward(cur -> {
      if (cur.compareTo(dat) < 0) { pred[0] = idx[0]; idx[0]++; return false; }
      return true; // stop when cur >= dat
    });
    return pred[0] >= 0 ? Natural.Of(pred[0]) : null;
  }

  default Natural SearchSuccessor(Data dat) {
    if (dat == null) return null;
    final long[] idx = new long[]{0L};
    final long[] succ = new long[]{-1L};
    TraverseForward(cur -> {
      if (cur.compareTo(dat) > 0) { succ[0] = idx[0]; return true; }
      idx[0]++;
      return false;
    });
    return succ[0] >= 0 ? Natural.Of(succ[0]) : null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) {
    if (data == null) return null;
    final long[] idx = new long[]{0L};
    final boolean[] found = new boolean[]{false};
    TraverseForward(cur -> {
      int cmp = cur.compareTo(data);
      if (cmp >= 0) { found[0] = (cmp == 0); return true; }
      idx[0]++;
      return false;
    });
    return found[0] ? Natural.Of(idx[0]) : null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  default void Intersection(SortedChain<Data> chn) {
    if (chn == null) { Clear(); return; }
    long i = 0L, j = 0L;
    while (i < Size().ToLong() && j < chn.Size().ToLong()) {
      Data a = GetAt(Natural.Of(i));
      Data b = chn.GetAt(Natural.Of(j));
      int cmp = a.compareTo(b);
      if (cmp < 0) {
        RemoveAt(Natural.Of(i));
        // do not increment i - elements shifted left
      } else if (cmp == 0) {
        i++; j++;
      } else { // cmp > 0
        j++;
      }
    }
    // remove any remaining elements in this chain beyond i
    while (i < Size().ToLong()) RemoveAt(Natural.Of(i));
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
  default Data Min() {
    ForwardIterator<Data> it = FIterator();
    return it.IsValid() ? it.GetCurrent() : null;
  }

  @Override
  default void RemoveMin() {
    if (Size().ToLong() == 0) return;
    RemoveAt(Natural.ZERO);
  }

  @Override
  default Data MinNRemove() {
    if (Size().ToLong() == 0) return null;
    Data d = Min();
    RemoveAt(Natural.ZERO);
    return d;
  }

  @Override
  default Data Max() {
    BackwardIterator<Data> it = BIterator();
    return it.IsValid() ? it.GetCurrent() : null;
  }

  @Override
  default void RemoveMax() {
    long sz = Size().ToLong();
    if (sz == 0) return;
    RemoveAt(Natural.Of(sz - 1));
  }

  @Override
  default Data MaxNRemove() {
    long sz = Size().ToLong();
    if (sz == 0) return null;
    Data d = Max();
    RemoveAt(Natural.Of(sz - 1));
    return d;
  }

  @Override
  default Data Predecessor(Data dat) {
    Natural idx = SearchPredecessor(dat);
    return idx == null ? null : GetAt(idx);
  }

  @Override
  default void RemovePredecessor(Data dat) {
    Natural idx = SearchPredecessor(dat);
    if (idx != null) RemoveAt(idx);
  }

  @Override
  default Data PredecessorNRemove(Data dat) {
    Natural idx = SearchPredecessor(dat);
    if (idx == null) return null;
    Data d = GetAt(idx);
    RemoveAt(idx);
    return d;
  }

  @Override
  default Data Successor(Data dat) {
    Natural idx = SearchSuccessor(dat);
    return idx == null ? null : GetAt(idx);
  }

  @Override
  default void RemoveSuccessor(Data dat) {
    Natural idx = SearchSuccessor(dat);
    if (idx != null) RemoveAt(idx);
  }

  @Override
  default Data SuccessorNRemove(Data dat) {
    Natural idx = SearchSuccessor(dat);
    if (idx == null) return null;
    Data d = GetAt(idx);
    RemoveAt(idx);
    return d;
  }

}
