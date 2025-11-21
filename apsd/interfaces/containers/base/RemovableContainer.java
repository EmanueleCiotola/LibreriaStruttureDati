package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container {

  boolean Remove(Data data);

  default boolean RemoveAll(TraversableContainer<Data> container) {
    final Box<Boolean> all = new Box<>(true);
    if (container != null) container.TraverseForward(data -> { all.Set(all.Get() && Remove(data)); return true; });
    return all.Get();
  }

  default boolean RemoveSome(TraversableContainer<Data> container) {
    final Box<Boolean> some = new Box<>(false);
    if (container != null) container.TraverseForward(data -> { some.Set(some.Get() || Remove(data)); return true; });
    return some.Get();
  }

}
