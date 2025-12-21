package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data> {

  protected long start = 0L;

  protected CircularVectorBase(Natural size) { super(size); }

  @Override
  protected void ArrayAlloc(Natural newSize) {
    super.ArrayAlloc(newSize);
    this.start = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural newCapacity) {
    if (newCapacity == null) return;
    Data[] oldArr = arr;
    long oldStart = start;
    long minSize = Math.min(Size().ToLong(), newCapacity.ToLong());
    ArrayAlloc(newCapacity);
    for (long index = 0; index < minSize; index++) {
      arr[(int) index]=oldArr[(int) ((oldStart+index) % oldArr.length)];
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
    long len = Math.min(num.ToLong(), size - index);

    if (len <= 0) return;

    if (index < len) {
      for (long i = 0; i < len; i++) {
        Natural currentPos = Natural.Of(i);
        if (i < index) SetAt(GetAt(currentPos), Natural.Of(i + len));
        SetAt(null, currentPos);
      }

      start = (start + len) % arr.length;
    } else super.ShiftLeft(position, num);
  }

  @Override
  public void ShiftRight(Natural position, Natural num) {
    long index = ExcIfOutOfBound(position);
    long size = Size().ToLong();
    long len = Math.min(num.ToLong(), size - index);

    if (len <= 0) return;

    if (index < len) {
      start = (start - len + arr.length) % arr.length;
      
      for (long i = 0; i < index; i++) { SetAt(GetAt(Natural.Of(i + len)), Natural.Of(i)); }
      for (long offset = 0; offset < len; offset++) { SetAt(null, Natural.Of(index + offset)); }
    } else super.ShiftRight(position, num);
  }

}