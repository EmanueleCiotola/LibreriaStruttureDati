package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.base.RemovableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

public interface Collection<Data> extends ClearableContainer, InsertableContainer<Data>, RemovableContainer<Data>, IterableContainer<Data> {

  default boolean Filter(Predicate<Data> fun) {
    Natural oldsize = Size();
    if (fun != null) {
      ForwardIterator<Data> it = FIterator();
      while (it.IsValid()) {
        Data data = it.GetCurrent();
        if (fun.Apply(data)) {
          it.Next();
        } else {
          Remove(data);
          it.Reset();
        }
      }
    }
    return !Size().equals(oldsize);
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  
  @Override
  default void Clear() {
    ForwardIterator<Data> it = FIterator();
    while (it.IsValid()) {
      Data data = it.GetCurrent();
      Remove(data);
      it.Reset();
    }
  }

}
