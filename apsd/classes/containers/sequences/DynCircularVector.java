package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data> extends DynCircularVectorBase<Data> {

  public DynCircularVector() { super(); }
  public DynCircularVector(Natural initialSize) { super(initialSize); }
  public DynCircularVector(Data[] arr) { super(arr); }
  public DynCircularVector(TraversableContainer<Data> container) { super(container); }

  @Override
  protected DynCircularVector<Data> NewVector(Data[] arr) { return new DynCircularVector<>(arr); }

}