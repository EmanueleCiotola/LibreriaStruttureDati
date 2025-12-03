package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data> {

  protected long start = 0L;

  protected CircularVectorBase() { super(); }
  protected CircularVectorBase(Natural size) { super(size); }
  protected CircularVectorBase(Data[] arr) { super(arr); }
  protected CircularVectorBase(TraversableContainer<Data> container) { super(container); }

  @Override
  public void ArrayAlloc(Natural newSize) {
    super.ArrayAlloc(newSize);
    start = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural newCapacity) {
    if (newCapacity == null) throw new NullPointerException("Natural cannot be null!");
    Data[] oldArr = arr;
    long oldStart = start;
    long minSize = Math.min(Size().ToLong(), newCapacity.ToLong());
    ArrayAlloc(newCapacity);
    for (long index = 0; index < minSize; index++) {
      arr[(int) index]=oldArr[(int) ((oldStart+index)% oldArr.length)];
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural positionition) {
    long index = ExcIfOutOfBound(positionition);
    return arr[(int) ((start + index) % arr.length)];
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */
  
  @Override
  public void SetAt(Data data, Natural positionition) {
    long index = ExcIfOutOfBound(positionition);
    arr[(int) ((start + index) % arr.length)] = data;
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  @Override
  public void ShiftLeft(Natural position, Natural num) {
    long index = ExcIfOutOfBound(position);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - index) ? len : size - index;
    if (index < size - (index + len)) {
      long iniwrt = index - 1 + len;
      long wrt = iniwrt;
      for (long rdr = wrt - len; rdr >= 0; rdr-- , wrt-- ) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
      }
      for (; iniwrt - wrt < len; wrt-- ) {
        SetAt(null, Natural.Of(wrt));
      }
      start = (start + len) % arr.length;
    } else {
      super.ShiftLeft(position, num);
    }
  }

  @Override
  public void ShiftRight(Natural position, Natural num) {
    long index = ExcIfOutOfBound(position);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - index) ? len : size - index;
    if (index < size - (index + len)) {
      long iniwrt = index;
      long wrt = iniwrt;
      for (long rdr = wrt + len; rdr < size; rdr++ , wrt++ ) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
      }
      for (; wrt - iniwrt < len; wrt++ ) {
        SetAt(null, Natural.Of(wrt));
      }
      start = (start - len + arr.length) % arr.length;
    } else {
      super.ShiftRight(position, num);
    }
  }
  
}