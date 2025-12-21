package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static linear) vector implementation. */
public class Vector<Data> extends LinearVectorBase<Data> {

  public Vector() { super(Natural.ZERO); }
  public Vector(Natural initialSize) { super(initialSize); }
  public Vector(Data[] arr) {
    super(Natural.Of(arr.length));
    for (int elem = 0; elem < arr.length; elem++) { this.arr[elem] = arr[elem]; }
  }
  public Vector(TraversableContainer<Data> container){
    super(Natural.Of(container.Size().ToLong()));
    Box<Natural> index = new Box<>(Natural.ZERO);
    container.TraverseForward(data -> {
      SetAt(data, index.Get());
      index.Set(index.Get().Increment());
      return false;
    });
  }

  @Override
  protected Vector<Data> NewVector (Data[] arr) { return new Vector<>(arr); }
  
}
