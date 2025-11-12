package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data> {

  Data Top();

  void Pop();

  default Data TopNPop() {
    if (Size().IsZero()) return null;
    final Data top = Top();
    Pop();
    return top;
  }

  default void SwapTop(Data data) {
    if (Size().IsZero()) {
      Push(data);
      return;
    }
    Pop();
    Push(data);
  }

  default Data TopNSwap(Data data) {
    if (Size().IsZero()) {
      Push(data);
      return null;
    }
    final Data oldTop = Top();
    Pop();
    Push(data);
    return oldTop;
  }

   void Push(Data data);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  
  @Override
  default void Clear() {
    while (!IsEmpty()) {
      Pop();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */
  
  @Override
  default boolean Insert(Data data) {
    Push(data);
    return true;
  }

}
