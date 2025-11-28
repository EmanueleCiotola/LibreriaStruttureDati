package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static) circular vector implementation. */
public class CircularVector<Data> extends CircularVectorBase<Data> {

  public CircularVector() { super(); }
  public CircularVector(Natural initialSize) { super(initialSize); }
  public CircularVector(Data[] arr) { super(arr); }
  public CircularVector(TraversableContainer<Data> container) { super(container); }

  @Override
  public CircularVector<Data> NewVector(Data[] arr) { return new CircularVector<>(arr); }

}
