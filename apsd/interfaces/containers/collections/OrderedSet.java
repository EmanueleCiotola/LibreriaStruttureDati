package apsd.interfaces.containers.collections;

public interface OrderedSet<Data extends Comparable<? super Data>> extends Set<Data> {

  default Data Min() { return FoldForward((data, min) -> (min == null || data.compareTo(min) < 0) ? data : min, null); }
  default void RemoveMin() {
    Data min = Min();
    if (min != null) Remove(min);
  }
  default Data MinNRemove() {
    Data min = Min();
    if (min == null) return null;
    Remove(min);
    return min;
  }

  default Data Max() { return FoldForward((dat, max) -> (max == null || dat.compareTo(max) > 0) ? dat : max, null); }
  default void RemoveMax() {
    Data max = Max();
    if (max != null) Remove(max);
  }
  default Data MaxNRemove() {
    Data max = Max();
    if (max == null) return null;
    Remove(max);
    return max;
  }

  default Data Predecessor(Data data) {
    return FoldForward((current, candidate) -> {
      return ((current.compareTo(data) < 0) && (candidate == null || current.compareTo(candidate) > 0)) ? current : candidate;
    }, null);
  }
  default void RemovePredecessor(Data data) {
    Data pre = Predecessor(data);
    if (pre != null) Remove(pre);
  }
  default Data PredecessorNRemove(Data data) {
    Data pre = Predecessor(data);
    if (pre == null) return null;
    Remove(pre);
    return pre;
  }

  default Data Successor(Data data) {
    return FoldForward((current, candidate) -> {
      return ((current.compareTo(data) > 0) && (candidate == null || current.compareTo(candidate) < 0)) ? current : candidate;
    }, null);
  }
  default void RemoveSuccessor(Data data) {
    Data succ = Successor(data);
    if (succ != null) Remove(succ);
  }
  default Data SuccessorNRemove(Data data) {
    Data succ = Successor(data);
    if (succ == null) return null;
    Remove(succ);
    return succ;
  }

}
