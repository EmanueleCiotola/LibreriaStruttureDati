package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data> {

  protected LinearVectorBase(Natural size) { super(size); }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural newCapacity) {
    if (newCapacity == null) throw new NullPointerException("Natural cannot be null!");
    Data[] oldArr = arr;
    int minSize = (int) Math.min(Capacity().ToLong(), newCapacity.ToLong());
    ArrayAlloc(newCapacity);
    System.arraycopy(oldArr, 0, arr, 0, minSize);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural position) { return arr[(int) ExcIfOutOfBound(position)]; }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  public void SetAt(Data data, Natural position) { arr[(int) ExcIfOutOfBound(position)] = data; }

}
