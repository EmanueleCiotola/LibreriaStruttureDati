package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;

import java.util.ArrayList;

public interface Set<Data> extends Collection<Data> {

  default void Union(Set<Data> other) {
    if (other == null) return;
    other.TraverseForward(elem -> {
      if (!this.Exists(elem)) Insert(elem);
      return false;
    });
  }

  default void Difference(Set<Data> other) {
    if (other == null) return;
    other.TraverseForward(elem -> {
      this.Remove(elem);
      return false;
    });
  }

  default void Intersection(Set<Data> other) {
    if (other == null) { Clear(); return; }
    ArrayList<Data> toRemove = new ArrayList<>();
    this.TraverseForward(elem -> {
      if (!other.Exists(elem)) toRemove.add(elem);
      return false;
    });
    for (Data data : toRemove) Remove(data);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null || !Size().equals(other.Size())) return false;
    if (this == other) return true;
    return !this.TraverseForward(elem -> !other.Exists(elem));
  }

}
