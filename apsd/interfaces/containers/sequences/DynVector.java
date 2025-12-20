package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

/** Interface: Dynamic Vector (resizable) with default positional mutators. */
public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> {

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  default void InsertAt(Data data, Natural position) {
    Natural size = Size();
    if (position == null) throw new NullPointerException("Natural number cannot be null!");
    if (position.compareTo(size) > 0) throw new IndexOutOfBoundsException("Index out of bounds: " + position + "; Size: " + size + "!"); //? non uso ExcIfOutOfBound(position) perché per InsertAt è lecito usare position == Size()
    
    if (position.compareTo(size) == 0) Expand(Natural.ONE);
    else ShiftRight(position, Natural.ONE);
    SetAt(data, position);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  default Data AtNRemove(Natural position) {
    Data old = GetAt(position);
    ShiftLeft(position, Natural.ONE);
    return old;
  }

  /* ************************************************************************ */
  /* Specific member functions of dynamic Vector                              */
  /* ************************************************************************ */

  @Override
  default void ShiftLeft(Natural pos, Natural num) {
      Vector.super.ShiftLeft(pos, num);
      Reduce(num);
  }

  @Override
  default void ShiftRight(Natural pos, Natural num) {
    Expand(num);
    Vector.super.ShiftRight(pos, num);
  }

  default DynVector<Data> SubVector(Natural start, Natural end) { return (DynVector<Data>) Vector.super.SubVector(start, end); }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  abstract Natural Size();

}
