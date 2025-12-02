package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Object: Concrete sorted chain implementation on linked-list. */
public class LLSortedChain<Data extends Comparable<? super Data>> extends LLChainBase<Data> implements SortedChain<Data> {

  public LLSortedChain() { super(); }
  public LLSortedChain(TraversableContainer<Data> container) { super(container); }
  public LLSortedChain(LLSortedChain<Data> chain) { super(chain); }
  protected LLSortedChain(long size, LLNode<Data> head, LLNode<Data> tail) { super(size, head, tail); }

  @Override
  protected LLChainBase<Data> NewChain(long size, LLNode<Data> headref, LLNode<Data> tailref) { return new LLSortedChain<>(size, headref, tailref);}

  /* ************************************************************************ */
  /* Specific member functions of LLSortedChain                               */
  /* ************************************************************************ */
  
  protected LLNode<Data> PredFind(Data data) {
    if (data == null) return null;
    if (headref.IsNull()) return null;

    ForwardIterator<Box<LLNode<Data>>> iterator = FRefIterator();
    long len = size.ToLong();
    LLNode<Data> pred = null;

    while (len > 0 && iterator.IsValid()) {
      long newlen = len / 2;
      ListFRefIterator next = new ListFRefIterator((ListFRefIterator) iterator);
      if (newlen > 1) next.Next(newlen - 1);
      if (!next.IsValid()) break;

      Box<LLNode<Data>> tmpBox = next.GetCurrent();
      LLNode<Data> tmpNode = tmpBox.Get();
      next.Next();
      if (!next.IsValid()) {
        if (tmpNode.Get().compareTo(data) < 0) pred = tmpNode;
        break;
      }

      LLNode<Data> midNode = next.GetCurrent().Get();
      if (midNode.Get().compareTo(data) < 0) {
        pred = midNode;
        iterator = next;
        len = len - newlen;
      } else len = newlen;
    }

    return pred;
  }

  protected LLNode<Data> PredPredFind(Data data) {
    if (data == null) return null;

    ForwardIterator<Box<LLNode<Data>>> iterator = FRefIterator();
    long len = size.ToLong();
    LLNode<Data> predpred = null;

    while (len > 1 && iterator.IsValid()) {
      long newlen = len / 2;
      ListFRefIterator next = new ListFRefIterator((ListFRefIterator) iterator);
      if (newlen > 1) next.Next(newlen - 1);
      if (!next.IsValid()) break;

      Box<LLNode<Data>> tmpBox = next.GetCurrent();
      LLNode<Data> tmpNode = tmpBox.Get();
      next.Next();
      if (!next.IsValid()) break;

      LLNode<Data> midNode = next.GetCurrent().Get();
      if (midNode.Get().compareTo(data) < 0) {
        predpred = tmpNode;
        iterator = next;
        len = len - newlen;
      } else  len = newlen;
    }

    return predpred;
  }

  protected LLNode<Data> SuccFind(Data data) {
    if (data == null) return null;
    if (headref.IsNull()) return null;

    ForwardIterator<Box<LLNode<Data>>> iterator = FRefIterator();
    long len = size.ToLong();
    LLNode<Data> succ = null;

    while (len > 0 && iterator.IsValid()) {
      long newlen = len / 2;
      ListFRefIterator next = new ListFRefIterator((ListFRefIterator) iterator);
      if (newlen > 1) next.Next(newlen - 1);
      if (!next.IsValid()) break;

      Box<LLNode<Data>> tmpBox = next.GetCurrent();
      LLNode<Data> tmpNode = tmpBox.Get();
      next.Next();
      if (!next.IsValid()) {
        if (tmpNode.Get().compareTo(data) > 0) succ = tmpNode;
        break;
      }

      LLNode<Data> midNode = next.GetCurrent().Get();
      if (midNode.Get().compareTo(data) > 0) {
        succ = midNode;
        len = newlen;
      } else {
        iterator = next;
        len = len - newlen;
      }
    }

    return succ;
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  public boolean Insert(Data data) {
    if (data == null) return false;
    LLNode<Data> pred = PredFind(data);
    Box<LLNode<Data>> curr = (pred == null) ? headref : pred.GetNext();
    LLNode<Data> next = curr.IsNull() ? null : curr.Get();
    LLNode<Data> newNode = new LLNode<>(data, next);
    curr.Set(newNode);
    if (tailref.Get() == pred) tailref.Set(newNode);
    size.Increment();
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove(Data data) {
    if (data == null) return false;

    LLNode<Data> pred = PredFind(data);
    Box<LLNode<Data>> curr = (pred == null) ? headref : pred.GetNext();
    if (curr.IsNull()) return false;

    LLNode<Data> node = curr.Get();
    if (node == null) return false;
    if (node.Get().compareTo(data) != 0) return false;

    Box<LLNode<Data>> nextBox = node.GetNext();
    LLNode<Data> after = nextBox.IsNull() ? null : nextBox.Get();
    curr.Set(after);
    if (tailref.Get() == node) tailref.Set(pred);

    size.Decrement();
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Natural Search(Data data) {
    if (data == null) return null;
    Box<LLNode<Data>> curr = headref;
    long len = size.ToLong();
    long index = 0L;

    while (len > 0 && !curr.IsNull()) {
      long step = len / 2;
      Box<LLNode<Data>> next = curr;
      for (long i = 0; i < step; i++) {
        if (next.IsNull()) break;
        LLNode<Data> node = next.Get();
        if (node == null) { next = new Box<>(); break; }
        next = node.GetNext();
      }

      if (next.IsNull()) break;
      LLNode<Data> node = next.Get();
      if (node == null) break;
      Data elem = node.Get();

      int cmp = elem.compareTo(data);
      if (cmp == 0) return Natural.Of(index + step);
      else if (cmp < 0) {
        curr = next;
        index += step;
        len = len - step;
      } else len = step;
    }

    return null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from SortedSequence                   */
  /* ************************************************************************ */

  @Override
  public Natural SearchPredecessor(Data data) {
    if (data == null || headref.IsNull()) return null;
    Box<LLNode<Data>> curr = headref;
    long len = size.ToLong();
    long index = -1L;

    while (len > 0 && !curr.IsNull()) {
      long step = (len - 1) / 2;
      Box<LLNode<Data>> next = curr;
      for (long i = 0; i < step; i++) {
        if (next.IsNull()) break;
        LLNode<Data> node = next.Get();
        if (node == null) { next = new Box<>(); break; }
        next = node.GetNext();
      }

      if (next.IsNull()) break;
      LLNode<Data> node = next.Get();
      if (node == null) break;

      int cmp = node.Get().compareTo(data);
      if (cmp < 0) {
        Box<LLNode<Data>> afterBox = node.GetNext();
        curr = afterBox.IsNull() ? new Box<>() : afterBox;
        index += step + 1;
        len = len - (step + 1);
      } else len = step;
    }

    return (index >= 0) ? Natural.Of(index) : null;
  }

  @Override
  public Natural SearchSuccessor(Data data) {
    if (data == null || headref.IsNull()) return null;
    Box<LLNode<Data>> curr = headref;
    long len = size.ToLong();
    long baseIndex = 0L;
    long result = -1L;

    while (len > 0 && !curr.IsNull()) {
      long step = (len - 1) / 2;
      Box<LLNode<Data>> next = curr;
      for (long i = 0; i < step; i++) {
        if (next.IsNull()) break;
        LLNode<Data> node = next.Get();
        if (node == null) { next = new Box<>(); break; }
        next = node.GetNext();
      }

      if (next.IsNull()) break;
      LLNode<Data> node = next.Get();
      if (node == null) break;

      int cmp = node.Get().compareTo(data);
      if (cmp > 0) {
        result = baseIndex + step;
        len = step;
      } else {
        Box<LLNode<Data>> afterBox = node.GetNext();
        curr = afterBox.IsNull() ? new Box<>() : afterBox;
        baseIndex += step + 1;
        len = len - (step + 1);
      }
    }

    return (result >= 0) ? Natural.Of(result) : null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */
  @Override
  public Data Predecessor(Data data) {
    if (data == null || headref.IsNull()) return null;
    LLNode<Data> pred = PredFind(data);
    return pred == null ? null : pred.Get();
  }

  @Override
  public void RemovePredecessor(Data data) {
    if (data == null || headref.IsNull()) return;

    LLNode<Data> prev = null;
    LLNode<Data> prevprev = null;
    for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()) {
      LLNode<Data> node = ref.Get();
      if (node.Get().compareTo(data) < 0) {
        prevprev = prev;
        prev = node;
      } else break;
    }

    if (prev == null) return;
    if (prevprev == null) {
      headref.Set(prev.GetNext().Get());
      if (headref.IsNull()) tailref.Set(null);
    } else {
      prevprev.SetNext(prev.GetNext().Get());
      if (tailref.Get() == prev) tailref.Set(prevprev);
    }

    size.Decrement();
  }

  @Override
  public Data PredecessorNRemove(Data data) {
    if (data == null || headref.IsNull()) return null;

    LLNode<Data> prev = null;
    LLNode<Data> prevprev = null;
    for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()) {
      LLNode<Data> node = ref.Get();
      if (node.Get().compareTo(data) < 0) {
        prevprev = prev;
        prev = node;
      } else break;
    }

    if (prev == null) return null;
    Data ret = prev.Get();
    if (prevprev == null) {
      headref.Set(prev.GetNext().Get());
      if (headref.IsNull()) tailref.Set(null);
    } else {
      prevprev.SetNext(prev.GetNext().Get());
      if (tailref.Get() == prev) tailref.Set(prevprev);
    }

    size.Decrement();
    return ret;
  }

  @Override
  public Data Successor(Data data) {
    if (data == null || headref.IsNull()) return null;
    for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()) {
      LLNode<Data> node = ref.Get();
      if (node.Get().compareTo(data) > 0) return node.Get();
    }
    return null;
  }

  @Override
  public void RemoveSuccessor(Data data) {
    if (data == null || headref.IsNull()) return;

    LLNode<Data> prev = null;
    for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()) {
      LLNode<Data> node = ref.Get();
      if (node.Get().compareTo(data) > 0) {
        if (prev == null) {
          headref.Set(node.GetNext().Get());
          if (headref.IsNull()) tailref.Set(null);
        } else {
          prev.SetNext(node.GetNext().Get());
          if (tailref.Get() == node) tailref.Set(prev);
        }

        size.Decrement();
        return;
      }

      prev = node;
    }
  }

  @Override
  public Data SuccessorNRemove(Data data) {
    if (data == null || headref.IsNull()) return null;

    LLNode<Data> prev = null;
    for (Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref = ref.Get().GetNext()) {
      LLNode<Data> node = ref.Get();
      if (node.Get().compareTo(data) > 0) {
        Data ret = node.Get();
        if (prev == null) {
          headref.Set(node.GetNext().Get());
          if (headref.IsNull()) tailref.Set(null);
        } else {
          prev.SetNext(node.GetNext().Get());
          if (tailref.Get() == node) tailref.Set(prev);
        }

        size.Decrement();
        return ret;
      }

      prev = node;
    }
    
    return null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  @Override
  public boolean InsertIfAbsent(Data dat) {
    if (dat == null) return false;

    LLNode<Data> pred = PredFind(dat);
    Box<LLNode<Data>> curr = (pred == null) ? headref : pred.GetNext();
    if (!curr.IsNull()) {
      LLNode<Data> currNode = curr.Get();
      if (currNode != null && currNode.Get().compareTo(dat) == 0) return false;
    }

    LLNode<Data> next = curr.IsNull() ? null : curr.Get();
    LLNode<Data> newNode = new LLNode<>(dat, next);
    curr.Set(newNode);

    if (tailref.Get() == pred) tailref.Set(newNode);
    size.Increment();
    return true;
  }

  @Override
  public void RemoveOccurrences(Data dat) {
    if (dat == null || headref.IsNull()) return;
    LLNode<Data> pred = PredFind(dat);
    Box<LLNode<Data>> curr = (pred == null) ? headref : pred.GetNext();

    while (!curr.IsNull()) {
      LLNode<Data> node = curr.Get(); // nodo candidato
      if (node == null) break;
      if (node.Get().compareTo(dat) != 0) break;

      Box<LLNode<Data>> nextBox = node.GetNext();
      LLNode<Data> after = nextBox.IsNull() ? null : nextBox.Get();

      curr.Set(after);
      size.Decrement();
    }

    if (pred == null) headref.Set(curr.IsNull() ? null : curr.Get());
    else pred.SetNext(curr.IsNull() ? null : curr.Get());

    if (curr.IsNull()) tailref.Set(pred);
  }

}