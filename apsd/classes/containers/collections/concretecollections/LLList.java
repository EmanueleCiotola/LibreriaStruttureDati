package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> {

  public LLList() { super(); }
  public LLList(TraversableContainer<Data> container) { super(container); }
  protected LLList(long size, LLNode<Data> head, LLNode<Data> tail) { super(size, head, tail); }

  // NewChain
  @Override
  protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) { return new LLList<Data>(size, head, tail); }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  @Override
  public MutableForwardIterator<Data> FIterator() { return new ListFIterator(); }

  @Override
  public MutableBackwardIterator<Data> BIterator() { return new ListBIterator(); }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */
  @Override
  public void SetAt(Data data, Natural index) {
    if (data == null) throw new NullPointerException("Data cannot be null!");
    if (index == null) throw new NullPointerException("Index cannot be null!");
    List.super.SetAt(data, index);
  }

  @Override
  public void SetFirst(Data data) {
    if (data == null) throw new NullPointerException("Data cannot be null!");
    if (headref.IsNull()) throw new IndexOutOfBoundsException("First element does not exist!");
    headref.Get().Set(data);
  }

  @Override
  public void SetLast(Data data) {
    if (data == null) throw new NullPointerException("Data cannot be null!");
    if (tailref.IsNull()) throw new IndexOutOfBoundsException("Last element does not exist!");
    tailref.Get().Set(data);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  public MutableSequence<Data> SubSequence(Natural from, Natural to) {
    if (from == null || to == null) throw new NullPointerException("Indices cannot be null!");
    return (MutableSequence<Data>) super.SubSequence(from, to);
  }

  @Override
  public void InsertAt(Data data, Natural position) {
    if (data == null) throw new NullPointerException("Data cannot be null!");
    if (position == null) throw new NullPointerException("Position cannot be null!");
    long LPosition = position.ToLong();
    long LSize = size.ToLong();
    if (LPosition > LSize) throw new IndexOutOfBoundsException("Index out of bounds: " + LPosition + "; Size: " + LSize + "!");
    if (LPosition == 0) { InsertFirst(data); return; }
    if (LPosition == LSize) { InsertLast(data); return; }

    ForwardIterator<Box<LLNode<Data>>> iterator = FRefIterator();
    iterator.Next(LPosition - 1);
    LLNode<Data> prev = iterator.GetCurrent().Get();
    LLNode<Data> node = new LLNode<>(data, prev.GetNext().Get());
    prev.SetNext(node);
    size.Increment();
  }

  @Override
  public void InsertFirst(Data data) {
    if (data == null) throw new NullPointerException("Data cannot be null!");
    LLNode<Data> node = new LLNode<>(data);
    if (headref.IsNull()) {
      headref.Set(node);
      tailref.Set(node);
    } else {
      node.SetNext(headref.Get());
      headref.Set(node);
    }
    size.Increment();
  }

  @Override
  public void InsertLast(Data data) {
    if (data == null) throw new NullPointerException("Data cannot be null!");
    LLNode<Data> node = new LLNode<>(data);
    if (tailref.IsNull()) {
      headref.Set(node);
      tailref.Set(node);
    } else {
      tailref.Get().SetNext(node);
      tailref.Set(node);
    }
    size.Increment();
  }

}
