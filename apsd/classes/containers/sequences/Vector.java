package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static linear) vector implementation. */
public class Vector<Data> extends LinearVectorBase<Data> {

  public Vector() { super(); }
  public Vector(Natural initialSize) { super(initialSize); }
  public Vector(Data[] arr) { super(arr); }
  public Vector(TraversableContainer<Data> container){ super(container); }

  @Override
  protected Vector<Data> NewVector (Data[] arr) { return new Vector<>(arr); }
  
}
