package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container { // Must extend Container

  boolean Insert(Data data);

  default boolean InsertAll(TraversableContainer<Data> container) {
    final Box<Boolean> all = new Box<>(true);
    if (container != null) container.TraverseForward(data -> {all.Set(all.Get() && Insert(data)); return true;});
    return all.Get();
  }

  default boolean InsertSome(TraversableContainer<Data> container) {
    final Box<Boolean> some = new Box<>(false);
    if (container != null) container.TraverseForward(data -> {some.Set(some.Get() || Insert(data)); return true;});
    return some.Get();
  }

}
