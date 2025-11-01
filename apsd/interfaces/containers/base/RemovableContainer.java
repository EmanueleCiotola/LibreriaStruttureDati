package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container {

  boolean Remove(Data data);

  default boolean RemoveAll(TraversableContainer<Data> container) {
    final Box<Boolean> all = new Box<>(true);
    if (container != null) container.TraverseForward(dat -> { all.Set(all.Get() && Remove(dat)); return true; });
    return all.Get();
  }

  default boolean RemoveSome(TraversableContainer<Data> container) {
    final Box<Boolean> some = new Box<>(false);
    if (container != null) container.TraverseForward(dat -> { some.Set(some.Get() || Remove(dat)); return true; });
    return some.Get();
  }

}
