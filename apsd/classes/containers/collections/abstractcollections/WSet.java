package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;

/** Object: Wrapper set implementation via chain. */
public class WSet<Data> extends WSetBase<Data, Chain<Data>> {

  public WSet() { super(); }
  public WSet(Chain<Data> chain) {
    super();
    chain.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }
  public WSet(TraversableContainer<Data> container) {
    super();
    container.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }
  public WSet(Chain<Data> chain, TraversableContainer<Data> container) {
    super();
    chain.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
    container.TraverseForward(data -> {
      this.chain.InsertIfAbsent(data);
      return false;
    });
  }

  @Override
  protected void ChainAlloc() { chain = new VList<>(); }

}
