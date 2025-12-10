package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Vector;

/** Object: Abstract vector base implementation. */
abstract public class VectorBase<Data> implements Vector<Data> {

  protected Data[] arr;

  protected VectorBase() { ArrayAlloc(new Natural(0)); }
  protected VectorBase(Natural initialSize) {
    if (initialSize == null) throw new IllegalArgumentException("Initial size cannot be null!");
    ArrayAlloc(initialSize);
  }
  protected VectorBase(Data[] arr) { this.arr = arr; }
  protected VectorBase(TraversableContainer<Data> container) {
    if (container == null) throw new NullPointerException("Traversable container cannot be null!");
    ArrayAlloc(container.Size());
    final MutableNatural index = new MutableNatural();
    container.TraverseForward(data -> {SetAt(data, index.GetNIncrement()); return false; });
  }

  protected abstract VectorBase<Data> NewVector(Data[] arr);

  @SuppressWarnings("unchecked")
  protected void ArrayAlloc(Natural newSize) {
    if (newSize == null) throw new NullPointerException("Size cannot be null!");
    long size = newSize.ToLong();
    if (size >= Integer.MAX_VALUE) throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!");
    arr = (Data[]) new Object[(int) size];
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() { ArrayAlloc(Natural.ZERO); }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public Natural Capacity() { return Natural.Of(arr.length); }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  private class VectorFIterator implements MutableForwardIterator<Data> {
    
    private long index = 0;

    @Override
    public boolean IsValid() { return index < Size().ToLong(); }

    @Override
    public void Next() {
      if (!IsValid()) throw new IndexOutOfBoundsException("Iterator not valid!");
      index++;
    }

    @Override
    public Data GetCurrent() {
      if (!IsValid()) throw new IndexOutOfBoundsException("Iterator not valid!");
      return GetAt(Natural.Of(index));
    }

    @Override
    public void SetCurrent(Data data) {
      if (!IsValid()) throw new IndexOutOfBoundsException("Iterator not valid!");
      SetAt(data, Natural.Of(index));
    }

    @Override
    public Data DataNNext() {
      Data curr = GetCurrent();
      Next();
      return curr;
    }

    @Override
    public void Reset() { index = 0; }

  }

  @Override
  public MutableForwardIterator<Data> FIterator() { return new VectorFIterator(); }

  private class VectorBIterator implements MutableBackwardIterator<Data> {
    
    private long index = Size().ToLong() - 1;

    @Override
    public boolean IsValid() { return index >= 0; }

    @Override
    public void Prev() {
      if(!IsValid()) throw new IndexOutOfBoundsException("Iterator not valid!");
      index--;
    }

    @Override
    public Data GetCurrent() {
      if(!IsValid()) throw new IndexOutOfBoundsException("Iterator not valid!");
      return GetAt(Natural.Of(index));
    }

    @Override
    public void SetCurrent(Data data) {
      if(!IsValid()) throw new IndexOutOfBoundsException("Iterator not valid!");
      SetAt(data, Natural.Of(index));
    }

    @Override
    public Data DataNPrev() {
      Data curr = GetCurrent();
      Prev();
      return curr;
    }
    
    @Override
    public void Reset() { index = Size().ToLong() - 1; }

  }

  @Override
  public MutableBackwardIterator<Data> BIterator() { return new VectorBIterator(); }


  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public abstract Data GetAt(Natural index);

  @Override
  public abstract void SetAt(Data data, Natural position);

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override //TODO rivedere perchè fa schifo (fatta da chatgpt)
  @SuppressWarnings("unchecked")
  public MutableSequence<Data> SubSequence(Natural start, Natural end) {
    if (start == null || end == null) throw new NullPointerException("Indices cannot be null!");
    
    long LStart = start.ToLong();
    long LEnd = end.ToLong();
    long size = Size().ToLong();

    if (LStart > LEnd) throw new IllegalArgumentException("Start index cannot be greater than end index!");
    if (LEnd >= size) throw new IndexOutOfBoundsException("End index out of bounds!");

    int newSize = (int)(LEnd - LStart + 1);
    Data[] newArr = (Data[]) new Object[newSize];
    for (int i = 0; i < newSize; i++) {
        newArr[i] = GetAt(Natural.Of(LStart + i));
    }

    return (MutableSequence<Data>) NewVector(newArr);
  }

}
