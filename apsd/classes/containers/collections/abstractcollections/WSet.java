package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;

/** Object: Wrapper set implementation via chain. */
public class WSet<Data> extends WSetBase<Data, Chain<Data>> {

  public WSet() { super(); }
  public WSet(Chain<Data> chain) { super(chain); }
  public WSet(TraversableContainer<Data> container) { super(container); }
  public WSet(Chain<Data> chain, TraversableContainer<Data> container) { super(chain, container); }

  @Override
  protected void ChainAlloc() { chain = new VList<>(); }

}
