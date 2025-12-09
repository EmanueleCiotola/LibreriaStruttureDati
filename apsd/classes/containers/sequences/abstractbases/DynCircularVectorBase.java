package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data> extends CircularVectorBase<Data> implements DynVector<Data> {

  protected long size = 0L;

  public DynCircularVectorBase(){ super(); }
  public DynCircularVectorBase(Natural initialSize){
    super(initialSize);
    this.size = initialSize.ToLong();
  }
  public DynCircularVectorBase(Data[] arr) {
    super(arr);
    this.size = arr.length;
  }
  public DynCircularVectorBase(TraversableContainer<Data> container){
    super(container);
    this.size = container.Size().ToLong();
  }

  @Override
  public void ArrayAlloc(Natural newSize) {
    super.ArrayAlloc(newSize);
    this.size = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() { return Natural.Of(size); }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() {
    super.Clear();
    this.size = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural newCapacity) {
    long oldSize = size;
    super.Realloc(newCapacity);
    size = oldSize;
    if (size > newCapacity.ToLong()) size = newCapacity.ToLong();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public void Expand(Natural num) {
    if (num == null) throw new NullPointerException("Size cannot be null!");
    long LNum = num.ToLong();
    if (LNum < 0) throw new IllegalArgumentException("Expand amount cannot be negative!");

    Grow(num);
    size += LNum;
  }

  @Override
  public void Reduce(Natural num) {
    if (num == null) throw new NullPointerException("Size cannot be null!");
    long LNum = num.ToLong();
    if (LNum > size)  throw new IllegalArgumentException("Reduce cannot be bigger than size!");

    size -= LNum;
    Shrink();
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */
  
  @Override
  public void ShiftLeft(Natural position, Natural num) {
    super.ShiftLeft(position, num);
    Reduce(num);
  }

  @Override
  public void ShiftRight(Natural position, Natural num) {
    Expand(num);
    super.ShiftRight(position, num);
  }

}
