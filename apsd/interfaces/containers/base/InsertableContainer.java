package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container {

  boolean Insert(Data data);

  default boolean InsertAll(TraversableContainer<Data> items) {
    final Box<Boolean> all = new Box<>(true);
    if (items != null) items.TraverseForward(dat -> { if (!Insert(dat)) all.Set(false); return false; });
    return all.Get();
  }

  default boolean InsertSome(TraversableContainer<Data> items) {
    final Box<Boolean> some = new Box<>(false);
    if (items != null) items.TraverseForward(dat -> { if (Insert(dat)) { some.Set(true); return true; } return false; });
    return some.Get();
  }

}
