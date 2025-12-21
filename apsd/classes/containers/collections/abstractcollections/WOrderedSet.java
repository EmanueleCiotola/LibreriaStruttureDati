package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable<? super Data>> extends WOrderedSetBase<Data, SortedChain<Data>> {

  public WOrderedSet() { super(); }

  public WOrderedSet(Chain<Data> chain) {
    super();
    this.chain = new VSortedChain<Data>(chain);
  }
  public WOrderedSet(SortedChain<Data> chain) {
    super();
    this.chain = chain;
  }
  public WOrderedSet(TraversableContainer<Data> container) {
    super();
    container.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }
  public WOrderedSet(SortedChain<Data> chain, TraversableContainer<Data> container) {
    super();
    this.chain = chain;
    container.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }
  public WOrderedSet(Chain<Data> chain, TraversableContainer<Data> container) {
    super();
    this.chain = new VSortedChain<Data>(chain);
    container.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }

  @Override
  protected void ChainAlloc() { chain = new VSortedChain<>(); }

}
