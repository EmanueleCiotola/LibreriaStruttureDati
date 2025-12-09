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
    this.start = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override //TODO forse è meglio usare la versione in basso
  public void Realloc(Natural newCapacity) {
    if (newCapacity == null) throw new NullPointerException("Natural cannot be null!");
    Data[] oldArr = arr;
    long oldStart = start;
    long minSize = Math.min(Size().ToLong(), newCapacity.ToLong());
    ArrayAlloc(newCapacity);
    for (long index = 0; index < minSize; index++) {
      arr[(int) index]=oldArr[(int) ((oldStart+index) % oldArr.length)];
    }
  }
  // @Override
  // public void Realloc(Natural newCapacity) {
  //   if (newCapacity == null) throw new NullPointerException("Natural cannot be null!");
  //   Data[] oldArr = arr;
  //   long oldStart = start;
  //   int oldLen = oldArr.length;
  //   int minSize = (int) Math.min(Size().ToLong(), newCapacity.ToLong());
    
  //   ArrayAlloc(newCapacity);
  //   if (minSize == 0) return;

  //   int firstChunk = Math.min(minSize, oldLen - (int) oldStart);
  //   System.arraycopy(oldArr, (int) oldStart, arr, 0, firstChunk);
  //   int remaining = minSize - firstChunk;
  //   if (remaining > 0) System.arraycopy(oldArr, 0, arr, firstChunk, remaining);
  // }

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
    long len = Math.min(num.ToLong(), size - index);

    if (len <= 0) return;
    
    if (index < len) {
      for (long rdr = index - 1; rdr >= 0; rdr--) {
        Natural natRdr = Natural.Of(rdr);
        Natural natWrt = Natural.Of(rdr + len);
        SetAt(GetAt(natRdr), natWrt);
        SetAt(null, natRdr);
      }

      start = (start + len) % arr.length; //? aggiusta start mantenendolo nel range consentito
    } else super.ShiftLeft(position, num);
  }

  @Override
  public void ShiftRight(Natural position, Natural num) {
    long index = ExcIfOutOfBound(position);
    long size = Size().ToLong();
    long len = Math.min(num.ToLong(), size - index);

    if (len <= 0) return;

    if (index < len) {
      for (long rdr = index - 1; rdr >= 0; rdr--) {
        Natural natRdr = Natural.Of(rdr);
        Natural natWrt = Natural.Of(rdr + size - len);
        SetAt(GetAt(natRdr), natWrt);
      }

      start = (start - len + arr.length) % arr.length; //? aggiusta start mantenendolo nel range consentito

      for (long offset = 0; offset < len; offset++) {
        SetAt(null, Natural.Of(index + offset));
      }
    } else super.ShiftRight(position, num);
  }

}