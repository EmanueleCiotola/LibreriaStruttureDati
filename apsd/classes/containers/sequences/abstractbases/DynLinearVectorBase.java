package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data> {

  protected long size = 0L;

  public DynLinearVectorBase() { super(); }
  public DynLinearVectorBase(Natural initialSize) {
    super(initialSize);
    this.size = initialSize.ToLong();
  }
  public DynLinearVectorBase(Data[] arr) {
    super(arr);
    this.size = arr.length;
  }
  public DynLinearVectorBase(TraversableContainer<Data> container) {
    super(container);
    this.size = container.Size().ToLong();
  }

  @Override
  protected void ArrayAlloc(Natural newSize) { //TODO che senso ha questo override?
    if (newSize == null) throw new NullPointerException("Size cannot be null!");
    super.ArrayAlloc(newSize);
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
    if (newCapacity == null) { throw new NullPointerException("Natural cannot be null!"); }
    super.Realloc(newCapacity);
    if(size > newCapacity.ToLong()){
        size = newCapacity.ToLong();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public void Expand(Natural num) { //TODO Expand e Reduce
    if (num == null) throw new NullPointerException("Size cannot be null!");
    long LNum = num.ToLong();
    if (LNum < 0) throw new IllegalArgumentException("Expand amount cannot be negative!");

    long req = size + LNum;
    if (req > arr.length) Realloc(Natural.Of(req));
    size = req;
  }

  @Override
  public void Reduce(Natural num) {
    if (num == null) throw new NullPointerException("Size cannot be null!");
    long LNum = num.ToLong();
    if (LNum > size)  throw new IllegalArgumentException("Reduce cannot be bigger than size!");
    size -= LNum;
  }

}
