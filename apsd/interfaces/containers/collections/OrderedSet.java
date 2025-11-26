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

  default Data Max() { return FoldForward((data, max) -> (max == null || data.compareTo(max) > 0) ? data : max, null); }
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
    Data predecessor = Predecessor(data);
    if (predecessor != null) Remove(predecessor);
  }
  default Data PredecessorNRemove(Data data) {
    Data predecessor = Predecessor(data);
    if (predecessor == null) return null;
    Remove(predecessor);
    return predecessor;
  }

  default Data Successor(Data data) {
    return FoldForward((current, candidate) -> {
      return ((current.compareTo(data) > 0) && (candidate == null || current.compareTo(candidate) < 0)) ? current : candidate;
    }, null);
  }
  default void RemoveSuccessor(Data data) {
    Data successor = Successor(data);
    if (successor != null) Remove(successor);
  }
  default Data SuccessorNRemove(Data data) {
    Data successor = Successor(data);
    if (successor == null) return null;
    Remove(successor);
    return successor;
  }

}
