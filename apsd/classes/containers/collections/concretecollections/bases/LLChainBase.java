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

  public LLChainBase() {}

  public LLChainBase(TraversableContainer<Data> container) { //TODO se il dato è null non va bene, modificare, questa deve essere una collezione, ma gli passo un cointainer
    size.Assign(container.Size());
    final Box<Boolean> first = new Box<>(true);
    container.TraverseForward(data -> {
      LLNode<Data> node = new LLNode<>(data);
      if (first.Get()) {
        headref.Set(node);
        first.Set(false);
      } else {
        tailref.Get().SetNext(node);
      }
      tailref.Set(node);
      return false;
    });
  }
  protected LLChainBase(long size, LLNode<Data> head, LLNode<Data> tail) {
    this.size.Assign(size);
    headref.Set(head);
    tailref.Set(tail);
  }

  abstract protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail);

  /* ************************************************************************ */
  /* Specific member functions from LLChainBase                               */
  /* ************************************************************************ */

  protected class ListFRefIterator implements ForwardIterator<Box<LLNode<Data>>> {

    protected Box<LLNode<Data>> cur;

    public ListFRefIterator() { this.cur = headref; }

    public ListFRefIterator(ListFRefIterator itr) { cur = itr.cur; }

    @Override
    public boolean IsValid() { return !cur.IsNull(); }
    
    @Override
    public void Reset() { cur = headref; }

    @Override
    public Box<LLNode<Data>> GetCurrent() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      return cur;
    }

    @Override
    public void Next() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      cur = cur.Get().GetNext();
    }

    @Override
    public Box<LLNode<Data>> DataNNext() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      Box<LLNode<Data>> oldcur = cur;
      cur = cur.Get().GetNext();
      return oldcur;
    }

  }

  protected ForwardIterator<Box<LLNode<Data>>> FRefIterator() { return new ListFRefIterator(); }

  protected class ListBRefIterator implements BackwardIterator<Box<LLNode<Data>>> {

    protected long cur = -1L;
    protected Vector<Box<LLNode<Data>>> arr = null;

    public ListBRefIterator() { Reset(); }

    public ListBRefIterator(ListBRefIterator itr) {
      cur = itr.cur;
      arr = new Vector<>(itr.arr);
    }

    @Override
    public boolean IsValid() { return (cur >= 0 && cur < Size().ToLong()); }
    
    @Override
    public void Reset() {
      cur = -1L;
      if (Size().IsZero()) arr = null;
      else {
        arr = new Vector<>(Size());
        for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()){
          arr.SetAt(ref, Natural.Of(++cur));
        }
      }
    }

    @Override
    public Box<LLNode<Data>> GetCurrent() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      return arr.GetAt(Natural.Of(cur));
    }

    @Override
    public void Prev() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      cur--;
    }

    @Override
    public Box<LLNode<Data>> DataNPrev() {
      if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
      return arr.GetAt(Natural.Of(cur--));
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
    final Box<LLNode<Data>> prd = new Box<>();
    return FRefIterator().ForEachForward(cur -> {
      LLNode<Data> node = cur.Get();
      if (node.Get().equals(data)) {
        cur.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(prd.Get()); }
        size.Decrement();
        return true;
      }
      prd.Set(node);
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
    public void SetCurrent(Data dat) {
      if (dat == null) return;
      itr.GetCurrent().Get().Set(dat);
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
    public void SetCurrent(Data dat) {
      if (dat == null) return;
      itr.GetCurrent().Get().Set(dat);
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
  public Sequence<Data> SubSequence(Natural from, Natural to) { //TODO fatta lui. Molto simile
    long LFrom = from.ToLong();
    long LTo = to.ToLong(); 
    if (LFrom > LTo || LTo >= size.ToLong()) return null;
    
    final Box<Long> idx = new Box<>(0L);
    final Box<LLNode<Data>> headlst = new Box<>();
    final Box<LLNode<Data>> taillst = new Box<>();
    
    TraverseForward(dat -> {
      if (idx.Get() > LTo) return true;
      LLNode<Data> node = new LLNode<>(dat);
      if (idx.Get() == LFrom) headlst.Set(node);
      else if (idx.Get() > LFrom) taillst.Get().SetNext(node);
      
      taillst.Set (node);
      idx.Set(idx.Get() + 1);
      return false;
    });

    return NewChain(LTo - LFrom + 1, headlst.Get(), taillst.Get());
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  public Data AtNRemove(Natural index) { //TODO controllare
    long idx = index.ToLong();
    if (idx >= size.ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx);
    
    final Box<Data> removed = new Box<>();
    final Box<Long> curidx = new Box<>(0L);
    final Box<LLNode<Data>> prd = new Box<>();
    
    FRefIterator().ForEachForward(cur -> {
      LLNode<Data> node = cur.Get();
      if (curidx.Get() == idx) {
        removed.Set(node.Get());
        cur.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(prd.Get()); }
        size.Decrement();
        return true;
      }
      prd.Set(node);
      curidx.Set(curidx.Get() + 1);
      return false;
    });
    
    return removed.Get();
  }

  @Override
  public void RemoveFirst() { //TODO controllare
    if (headref.IsNull()) throw new IndexOutOfBoundsException("First element does not exist!");
    headref.Set(headref.Get().GetNext().Get());
    size.Decrement();
    if (headref.IsNull()) tailref.Set(null);
  }

  @Override
  public void RemoveLast() { //TODO controllare
    if (tailref.IsNull()) throw new IndexOutOfBoundsException("Last element does not exist!");
    if (size.ToLong() == 1) {
      headref.Set(null);
      tailref.Set(null);
    } else {
      final Box<LLNode<Data>> prd = new Box<>();
      FRefIterator().ForEachForward(cur -> {
        LLNode<Data> node = cur.Get();
        if (node == tailref.Get()) {
          prd.Get().SetNext(null);
          tailref.Set(prd.Get());
          return true;
        }
        prd.Set(node);
        return false;
      });
    }
    size.Decrement();
  }

  public Data FirstNRemove() {
    Data data = GetFirst();
    RemoveFirst();
    return data;
  }

  public Data LastNRemove() {
    Data data = GetLast();
    RemoveLast();
    return data;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  @Override
  public boolean Filter(Predicate<Data> pred) { //TODO da rivedere
    if (pred == null) return false;
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
        curBox.Set(node.GetNext() == null ? null : node.GetNext().Get());
        if (tailref.Get() == node) {
          tailref.Set(prev.Get());
        }
        size.Decrement();
      }
    }
    return oldSize != size.ToLong();
  }

}
