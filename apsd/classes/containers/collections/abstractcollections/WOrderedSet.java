package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable<? super Data>> extends WOrderedSetBase<Data, SortedChain<Data>> {

  public WOrderedSet() { super(); }
  public WOrderedSet(SortedChain<Data> chain) { super(chain); }
  public WOrderedSet(TraversableContainer<Data> container) { super(container); }
  public WOrderedSet(SortedChain<Data> chain, TraversableContainer<Data> container) { super(chain, container); }

  @Override
  protected void ChainAlloc() { chain = new VSortedChain<>(); }

}
