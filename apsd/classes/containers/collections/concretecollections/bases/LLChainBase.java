package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.sequences.Vector;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<Data> implements Chain<Data> {

  protected final MutableNatural size = new MutableNatural();
  protected final Box<LLNode<Data>> headref = new Box<>();
  protected final Box<LLNode<Data>> tailref = new Box<>();

  protected LLChainBase() {
    size.Assign(0L);
    headref.Set(null);
    tailref.Set(null);
  }
  public LLChainBase(TraversableContainer<Data> container) {
    size.Assign(container.Size());
    final Box<Boolean> isFirstNode = new Box<>(true);

    container.TraverseForward(data -> {
      LLNode<Data> node = new LLNode<>(data);
      if (isFirstNode.Get()) {
        headref.Set(node);
        isFirstNode.Set(false);
      } else tailref.Get().SetNext(node);
      
      tailref.Set(node);
      return false;
    });
  }
   protected LLChainBase(long size, LLNode<Data> head, LLNode<Data> tail) {
    this.size.Assign(size);
    this.headref.Set(head);
    this.tailref.Set(tail);
  }

  abstract protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail);

  /* ************************************************************************ */
  /* Specific member functions from LLChainBase                               */
  /* ************************************************************************ */

  protected class ListFRefIterator implements ForwardIterator<Box<LLNode<Data>>> {

    protected Box<LLNode<Data>> curr;

    public ListFRefIterator() { this.curr = headref; }

    public ListFRefIterator(ListFRefIterator itr) { curr = itr.curr; }

    @Override
    public boolean IsValid() { return !curr.IsNull(); }
    
    @Override
    public void Reset() { curr = headref; }

    @Override
    public Box<LLNode<Data>> GetCurrent() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      return curr;
    }

    @Override
    public void Next() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      curr = curr.Get().GetNext();
    }

    @Override
    public Box<LLNode<Data>> DataNNext() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      Box<LLNode<Data>> oldcur = curr;
      curr = curr.Get().GetNext();
      return oldcur;
    }

  }

  protected ForwardIterator<Box<LLNode<Data>>> FRefIterator() { return new ListFRefIterator(); }

  protected class ListBRefIterator implements BackwardIterator<Box<LLNode<Data>>> {

    protected long curr = -1L;
    protected Vector<Box<LLNode<Data>>> arr = null;

    public ListBRefIterator() { Reset(); }

    public ListBRefIterator(ListBRefIterator itr) {
      curr = itr.curr;
      arr = new Vector<>(itr.arr);
    }

    @Override
    public boolean IsValid() { return (curr >= 0 && curr < Size().ToLong()); }
    
    @Override
    public void Reset() {
      curr = -1L;
      if (Size().IsZero()) arr = null;
      else {
        arr = new Vector<>(Size());
        for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()){
          arr.SetAt(ref, Natural.Of(++curr));
        }
      }
    }

    @Override
    public Box<LLNode<Data>> GetCurrent() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      return arr.GetAt(Natural.Of(curr));
    }

    @Override
    public void Prev() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      curr--;
    }

    @Override
    public Box<LLNode<Data>> DataNPrev() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      return arr.GetAt(Natural.Of(curr--));
    }

  }

  protected BackwardIterator<Box<LLNode<Data>>> BRefIterator() { return new ListBRefIterator(); }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() { return size.ToNatural(); }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() {
    size.Zero();
    headref.Set(null);
    tailref.Set(null);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove(Data data) {
    if (data == null) return false;
    final Box<LLNode<Data>> pred = new Box<>();
    return FRefIterator().ForEachForward(curr -> {
      LLNode<Data> node = curr.Get();
      if (node.Get().equals(data)) {
        curr.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(pred.Get()); }
        size.Decrement();
        return true;
      }
      pred.Set(node);
      return false;
    });
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  protected class ListFIterator implements MutableForwardIterator<Data> {

    protected final ForwardIterator<Box<LLNode<Data>>> itr;

    public ListFIterator() { itr = FRefIterator(); }

    public ListFIterator(ListFIterator itr) { this.itr = itr.itr; }

    @Override
    public boolean IsValid() { return itr.IsValid(); }

    @Override
    public void Reset() { itr.Reset(); }

    @Override
    public Data GetCurrent() { return itr.GetCurrent().Get().Get(); }

    @Override
    public void SetCurrent(Data data) {
      if (data == null) return;
      itr.GetCurrent().Get().Set(data);
    }

    @Override
    public void Next() { itr.Next(); }

    @Override
    public Data DataNNext() { return itr.DataNNext().Get().Get(); }
  
  }

  @Override
  public ForwardIterator<Data> FIterator() { return new ListFIterator(); }

  protected class ListBIterator implements MutableBackwardIterator<Data> {

    protected final BackwardIterator<Box<LLNode<Data>>> itr;

    public ListBIterator() { itr = BRefIterator(); }

    public ListBIterator(ListBIterator itr) { this.itr = itr.itr; }

    @Override
    public boolean IsValid() { return itr.IsValid(); }

    @Override
    public void Reset() { itr.Reset(); }

    @Override
    public Data GetCurrent() { return itr.GetCurrent().Get().Get(); }

    @Override
    public void SetCurrent(Data data) {
      if (data == null) return;
      itr.GetCurrent().Get().Set(data);
    }

    @Override
    public void Prev() { itr.Prev(); }

    @Override
    public Data DataNPrev() { return itr.DataNPrev().Get().Get(); }
  
  }

  @Override
  public BackwardIterator<Data> BIterator() { return new ListBIterator(); }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetFirst() {
    if (headref.IsNull()) throw new IndexOutOfBoundsException("First element does not exist!");
    return headref.Get().Get();
  }

  @Override
  public Data GetLast() {
    if (tailref.IsNull()) throw new IndexOutOfBoundsException("Last element does not exist!");
    return tailref.Get().Get();
  }

  @Override
  public Sequence<Data> SubSequence(Natural from, Natural to) {
    long lFrom = ExcIfOutOfBound(from);
    long lTo = ExcIfOutOfBound(to);
    if (lFrom > lTo) return NewChain(0, null, null);

    final Box<Long> idx = new Box<>(0L);
    final Box<LLNode<Data>> headlst = new Box<>();
    final Box<LLNode<Data>> taillst = new Box<>();

    TraverseForward(data -> {
      long i = idx.Get();
      if (i > lTo) return true;
      
      if (i >= lFrom) {
        LLNode<Data> node = new LLNode<>(data);
        if (headlst.IsNull()) headlst.Set(node);
        else taillst.Get().SetNext(node);
        taillst.Set(node);
      }
      idx.Set(i + 1);
      return false;
    });
    
    return NewChain(lTo - lFrom + 1, headlst.Get(), taillst.Get());
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  public Data AtNRemove(Natural index) {
    long LIndex = ExcIfOutOfBound(index);
    final Box<Data> removed = new Box<>();
    final Box<Long> currIndex = new Box<>(0L);
    final Box<LLNode<Data>> pred = new Box<>();
    
    FRefIterator().ForEachForward(curr -> {
      LLNode<Data> node = curr.Get();
      if (currIndex.Get() == LIndex) {
        removed.Set(node.Get());
        curr.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(pred.Get()); }
        size.Decrement();
        return true;
      }
      pred.Set(node);
      currIndex.Set(currIndex.Get() + 1);
      return false;
    });
    
    return removed.Get();
  }

  @Override
  public void RemoveFirst() {
    if (headref.IsNull()) return;
    headref.Set(headref.Get().GetNext().Get());
    size.Decrement();
    if (headref.IsNull()) tailref.Set(null);
  }

  @Override
  public void RemoveLast() {
    if (tailref.IsNull()) return;
    if (size.ToLong() == 1) RemoveFirst();
    else {
      final Box<LLNode<Data>> pred = new Box<>();
      FRefIterator().ForEachForward(curr -> {
        LLNode<Data> node = curr.Get();
        if (node == tailref.Get()) {
          pred.Get().SetNext(null);
          tailref.Set(pred.Get());
          return true;
        }
        pred.Set(node);
        return false;
      });
      size.Decrement();
    }
  }

  public Data FirstNRemove() {
    if (Size().IsZero()) return null;
    Data data = GetFirst();
    RemoveFirst();
    return data;
  }

  public Data LastNRemove() {
    if (Size().IsZero()) return null;
    Data data = GetLast();
    RemoveLast();
    return data;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  @Override
  public boolean Filter(Predicate<Data> pred) {
    if (pred == null) throw new IllegalArgumentException("Predicate cannot be null");
    long oldSize = size.ToLong();

    ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
    Box<LLNode<Data>> prev = new Box<>();

    while (itr.IsValid()) {
      Box<LLNode<Data>> curBox = itr.GetCurrent();
      LLNode<Data> node = curBox.Get();
      if (pred.Apply(node.Get())) {
        prev.Set(node);
        itr.Next();
      } else {
        Box<LLNode<Data>> nextBox = node.GetNext();
        curBox.Set(nextBox.IsNull() ? null : nextBox.Get());
        if (tailref.Get() == node) {
          tailref.Set(prev.Get());
        }
        size.Decrement();
      }
    }
    return oldSize != size.ToLong();
  }

}
