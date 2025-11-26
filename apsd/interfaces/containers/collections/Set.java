package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;

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

  default void Intersection(Set<Data> other) { Filter(elem -> other.Exists(elem)); }

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
