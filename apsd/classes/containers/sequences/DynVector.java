package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic (linear) vector implementation. */
public class DynVector<Data> extends DynLinearVectorBase<Data> {

  public DynVector() { super(); }
  public DynVector(Natural initialSize) { super(initialSize); }
  public DynVector(Data[] arr) { super(arr); }
  public DynVector(TraversableContainer<Data> container) { super(container); }

  @Override
  protected DynVector<Data> NewVector(Data[] arr) { return new DynVector<>(arr); }

}
